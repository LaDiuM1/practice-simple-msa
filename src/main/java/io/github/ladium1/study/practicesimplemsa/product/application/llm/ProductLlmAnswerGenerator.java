package io.github.ladium1.study.practicesimplemsa.product.application.llm;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;

import java.util.List;

public interface ProductLlmAnswerGenerator {

    String generateAnswer(String question, List<Product> products);
}
