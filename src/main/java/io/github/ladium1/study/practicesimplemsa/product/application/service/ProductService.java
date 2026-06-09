package io.github.ladium1.study.practicesimplemsa.product.application.service;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductCreateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductUpdateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.usecase.ProductUseCase;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public ProductInfo get(UUID productId) {
        Product product = productRepository.getById(productId);
        return ProductInfo.from(product);
    }

    @Override
    @Transactional
    public ProductInfo create(ProductCreateCommand command) {
        Product newProduct = new Product(
                command.name(),
                command.price(),
                command.sellerId()
        );
        Product createdProduct = productRepository.save(newProduct);
        return ProductInfo.from(createdProduct);
    }

    @Override
    @Transactional
    public ProductInfo update(ProductUpdateCommand command) {
        Product product = productRepository.getById(command.productId());
        product.update(command.name(),
                command.price(),
                command.stockQuantity(),
                command.status()
        );
        return ProductInfo.from(product);
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        Product deleteProduct = productRepository.getById(productId);
        productRepository.delete(deleteProduct);
    }
}
