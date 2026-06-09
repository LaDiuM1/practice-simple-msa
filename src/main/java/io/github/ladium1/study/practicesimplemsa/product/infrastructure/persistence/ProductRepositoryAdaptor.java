package io.github.ladium1.study.practicesimplemsa.product.infrastructure.persistence;

import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdaptor implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

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
    public void delete(Product deleteProduct) {
        productJpaRepository.delete(deleteProduct);
    }
}
