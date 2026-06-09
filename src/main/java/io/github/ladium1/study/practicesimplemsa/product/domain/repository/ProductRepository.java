package io.github.ladium1.study.practicesimplemsa.product.domain.repository;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;

import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Product getById(UUID productId);

    void delete(Product deleteProduct);
}
