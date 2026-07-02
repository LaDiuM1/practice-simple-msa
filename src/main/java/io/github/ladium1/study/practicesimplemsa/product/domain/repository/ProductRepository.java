package io.github.ladium1.study.practicesimplemsa.product.domain.repository;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Product getById(UUID productId);

    List<Product> findAll();

    List<Product> findNearestProducts(float[] embedding, int limit);

    void delete(Product deleteProduct);
}
