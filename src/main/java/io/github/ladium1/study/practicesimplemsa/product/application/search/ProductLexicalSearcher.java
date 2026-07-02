package io.github.ladium1.study.practicesimplemsa.product.application.search;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ProductLexicalSearcher {

    private final ProductRepository productRepository;

    public List<Product> search(String query, int limit) {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return List.of();
        }

        return products.stream()
                .sorted(Comparator
                        .comparingDouble((Product product) -> score(product, query))
                        .reversed()
                        .thenComparing(Product::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .limit(limit)
                .toList();
    }

    private double score(Product product, String query) {
        if (query == null || query.isBlank() || product.getName() == null) {
            return 0.0;
        }

        String haystack = product.getName().toLowerCase(Locale.ROOT);
        String needle = query.toLowerCase(Locale.ROOT).trim();

        if (haystack.contains(needle)) {
            return 1.0;
        }

        String[] tokens = needle.split("\\s+");
        long matches = 0;
        for (String token : tokens) {
            if (!token.isBlank() && haystack.contains(token)) {
                matches++;
            }
        }
        return tokens.length == 0 ? 0.0 : (double) matches / tokens.length;
    }
}
