package io.github.ladium1.study.practicesimplemsa.product.infrastructure.llm;

import io.github.ladium1.study.practicesimplemsa.product.application.llm.ProductLlmAnswerGenerator;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "openai.chat.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpProductLlmAnswerGenerator implements ProductLlmAnswerGenerator {

    @Override
    public String generateAnswer(String question, List<Product> products) {
        return "";
    }
}
