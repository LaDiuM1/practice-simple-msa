package io.github.ladium1.study.practicesimplemsa.product.infrastructure.llm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.ladium1.study.practicesimplemsa.product.application.llm.ProductLlmAnswerGenerator;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "openai.chat.enabled", havingValue = "true")
public class OpenAiProductLlmAnswerGenerator implements ProductLlmAnswerGenerator {

    private static final String OPENAI_RESPONSES_URL = "https://api.openai.com/v1/responses";
    private static final String SYSTEM_PROMPT =
            "너는 상품 추천 도우미다. 제공된 상품 정보만 사용해서 한국어로 짧고 명확하게 답변해라.";

    private final RestClient restClient;

    @Value("${openai.api-key:}")
    private String apiKey;

    @Value("${openai.chat.model:gpt-5.4-mini}")
    private String model;

    @Override
    public String generateAnswer(String question, List<Product> products) {
        ResponsePayload response = restClient.post()
                .uri(OPENAI_RESPONSES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(requireApiKey()))
                .body(Map.of(
                        "model", model,
                        "input", List.of(
                                Map.of(
                                        "role", "system",
                                        "content", List.of(inputText(SYSTEM_PROMPT))
                                ),
                                Map.of(
                                        "role", "user",
                                        "content", List.of(
                                                inputText("질문: " + question),
                                                inputText("상품 목록:\n" + buildProductContext(products))
                                        )
                                )
                        )
                ))
                .retrieve()
                .body(ResponsePayload.class);

        return extractAnswer(response);
    }

    private Map<String, String> inputText(String text) {
        return Map.of("type", "input_text", "text", text);
    }

    private String buildProductContext(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return "검색된 상품이 없습니다.";
        }

        StringJoiner joiner = new StringJoiner("\n");
        for (Product product : products) {
            joiner.add("- name: " + safe(product.getName()));
            joiner.add("  price: " + safe(product.getPrice()));
            joiner.add("  status: " + safe(product.getStatus()));
            joiner.add("  stock: " + safe(product.getStockQuantity()));
        }
        return joiner.toString();
    }

    private String extractAnswer(ResponsePayload response) {
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

    private String safe(Object value) {
        return value == null ? "" : value.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record ResponsePayload(List<OutputItem> output) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OutputItem(List<OutputContent> content) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OutputContent(String type, String text) {
    }
}
