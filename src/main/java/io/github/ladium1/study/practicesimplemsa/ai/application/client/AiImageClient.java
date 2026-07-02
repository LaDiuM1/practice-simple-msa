package io.github.ladium1.study.practicesimplemsa.ai.application.client;

public interface AiImageClient {

    String analyzeImage(byte[] image, String contentType, String prompt);

    GeneratedImage generateImage(String prompt, String size);

    record GeneratedImage(String base64, String format) {
    }
}
