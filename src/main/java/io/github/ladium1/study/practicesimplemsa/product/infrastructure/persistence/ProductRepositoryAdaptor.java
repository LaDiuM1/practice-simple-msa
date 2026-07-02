package io.github.ladium1.study.practicesimplemsa.product.infrastructure.persistence;

import com.pgvector.PGvector;
import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdaptor implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Product getById(UUID productId) {
        return productJpaRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public List<Product> findNearestProducts(float[] embedding, int limit) {
        if (embedding == null || embedding.length == 0 || limit <= 0) {
            return List.of();
        }

        String sql = """
                SELECT id
                FROM study_msa."product"
                WHERE embedding IS NOT NULL
                ORDER BY embedding <=> ?
                LIMIT ?
                """;

        List<UUID> orderedIds = jdbcTemplate.query(
                connection -> {
                    var statement = connection.prepareStatement(sql);
                    statement.setObject(1, new PGvector(embedding));
                    statement.setInt(2, limit);
                    return statement;
                },
                (resultSet, rowNum) -> UUID.fromString(resultSet.getString("id"))
        );

        if (orderedIds.isEmpty()) {
            return List.of();
        }

        Map<UUID, Product> productMap = new LinkedHashMap<>();
        for (Product product : productJpaRepository.findAllById(orderedIds)) {
            productMap.put(product.getId(), product);
        }

        return orderedIds.stream()
                .map(productMap::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void delete(Product deleteProduct) {
        productJpaRepository.delete(deleteProduct);
    }
}
