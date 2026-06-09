package io.github.ladium1.study.practicesimplemsa.product.infrastructure.persistence;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
}
