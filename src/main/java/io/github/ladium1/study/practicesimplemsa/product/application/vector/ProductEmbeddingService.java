package io.github.ladium1.study.practicesimplemsa.product.application.vector;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductEmbeddingService {

    private final ProductRepository productRepository;
    private final ProductEmbeddingGenerator productEmbeddingGenerator;

    public Optional<float[]> embed(String text) {
        return productEmbeddingGenerator.generate(text);
    }

    public void applyEmbedding(Product product) {
        String source = buildSourceText(product);
        if (source.isBlank()) {
            return;
        }

        productEmbeddingGenerator.generate(source)
                .ifPresent(product::updateEmbedding);
    }

    @Transactional
    public int refreshEmbeddings() {
        int updatedCount = 0;
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            float[] before = product.getEmbedding();
            applyEmbedding(product);
            if (!sameEmbedding(before, product.getEmbedding())) {
                updatedCount++;
            }
        }

        return updatedCount;
    }

    private String buildSourceText(Product product) {
        return product.getName() == null ? "" : product.getName().strip();
    }

    private boolean sameEmbedding(float[] before, float[] after) {
        if (before == null) {
            return after == null;
        }
        return after != null && Arrays.equals(before, after);
    }
}
