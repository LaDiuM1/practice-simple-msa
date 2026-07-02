package io.github.ladium1.study.practicesimplemsa.product.application.vector;

import java.util.Optional;

public interface ProductEmbeddingGenerator {

    Optional<float[]> generate(String text);
}
