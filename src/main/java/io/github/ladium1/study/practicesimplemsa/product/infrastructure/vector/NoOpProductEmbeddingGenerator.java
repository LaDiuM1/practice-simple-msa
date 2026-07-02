package io.github.ladium1.study.practicesimplemsa.product.infrastructure.vector;

import io.github.ladium1.study.practicesimplemsa.product.application.vector.ProductEmbeddingGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "openai.embedding.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpProductEmbeddingGenerator implements ProductEmbeddingGenerator {

    @Override
    public Optional<float[]> generate(String text) {
        return Optional.empty();
    }
}
