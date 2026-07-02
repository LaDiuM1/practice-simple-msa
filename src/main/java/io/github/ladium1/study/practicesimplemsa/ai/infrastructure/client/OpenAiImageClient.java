package io.github.ladium1.study.practicesimplemsa.ai.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ladium1.study.practicesimplemsa.ai.application.client.AiImageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class OpenAiImageClient implements AiImageClient {

    private static final String OPENAI_RESPONSES_URL = "https://api.openai.com/v1/responses";
    private static final String OPENAI_IMAGES_URL = "https://api.openai.com/v1/images/generations";
    private static final String DEFAULT_CONTENT_TYPE = "image/png";
    private static final String OUTPUT_FORMAT = "png";

    private final RestClient restClient;

    @Value("${openai.api-key:}")
    private String apiKey;

    @Value("${openai.image-analysis.model:gpt-5.4-nano}")
    private String imageAnalysisModel;

    @Value("${openai.image.model:gpt-image-1-mini}")
    private String imageModel;

    @Override
    public String analyzeImage(byte[] image, String contentType, String prompt) {
        AnalysisResponse response = restClient.post()
                .uri(OPENAI_RESPONSES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(requireApiKey()))
                .body(Map.of(
                        "model", imageAnalysisModel,
                        "input", List.of(
                                Map.of(
                                        "role", "user",
                                        "content", List.of(
                                                Map.of("type", "input_text", "text", prompt),
                                                Map.of("type", "input_image", "image_url", toDataUrl(image, contentType))
                                        )
                                )
                        )
                ))
                .retrieve()
                .body(AnalysisResponse.class);

        String answer = extractText(response);
        if (answer.isBlank()) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "OpenAI image analysis returned no text");
        }
        return answer;
    }

    @Override
    public GeneratedImage generateImage(String prompt, String size) {
        GenerationResponse response = restClient.post()
                .uri(OPENAI_IMAGES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(requireApiKey()))
                .body(Map.of(
                        "model", imageModel,
                        "prompt", prompt,
                        "size", size,
                        "output_format", OUTPUT_FORMAT
                ))
                .retrieve()
                .body(GenerationResponse.class);

        if (response == null || response.data() == null || response.data().isEmpty()) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "OpenAI image generation returned no data");
        }

        ImageData imageData = response.data().get(0);
        String format = response.outputFormat() == null ? OUTPUT_FORMAT : response.outputFormat();
        return new GeneratedImage(imageData.b64Json(), format);
    }

    private String toDataUrl(byte[] image, String contentType) {
        String type = (contentType == null || contentType.isBlank()) ? DEFAULT_CONTENT_TYPE : contentType;
        String base64 = Base64.getEncoder().encodeToString(image);
        return "data:%s;base64,%s".formatted(type, base64);
    }

    private String extractText(AnalysisResponse response) {
        if (response == null || response.output() == null) {
            return "";
        }

        for (OutputItem item : response.output()) {
            if (item.content() == null) {
                continue;
            }
            for (OutputContent content : item.content()) {
                if ("output_text".equals(content.type()) && content.text() != null) {
                    return content.text();
                }
            }
        }
        return "";
    }

    private String requireApiKey() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "OPENAI_API_KEY is required");
        }
        return apiKey;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record AnalysisResponse(List<OutputItem> output) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OutputItem(List<OutputContent> content) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OutputContent(String type, String text) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record GenerationResponse(
            List<ImageData> data,
            @JsonProperty("output_format") String outputFormat
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record ImageData(@JsonProperty("b64_json") String b64Json) {
    }
}
