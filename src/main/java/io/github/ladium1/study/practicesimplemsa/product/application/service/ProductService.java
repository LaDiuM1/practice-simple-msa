package io.github.ladium1.study.practicesimplemsa.product.application.service;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductCreateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductUpdateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.search.ProductLexicalSearcher;
import io.github.ladium1.study.practicesimplemsa.product.application.usecase.ProductUseCase;
import io.github.ladium1.study.practicesimplemsa.product.application.vector.ProductEmbeddingService;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements ProductUseCase {

    private static final int DEFAULT_SEARCH_SIZE = 5;

    private final ProductRepository productRepository;
    private final ProductEmbeddingService productEmbeddingService;
    private final ProductLexicalSearcher productLexicalSearcher;

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
        productEmbeddingService.applyEmbedding(newProduct);
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
        productEmbeddingService.applyEmbedding(product);
        return ProductInfo.from(product);
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        Product deleteProduct = productRepository.getById(productId);
        productRepository.delete(deleteProduct);
    }

    @Override
    @Transactional
    public void decreaseStock(UUID productId, int quantity) {
        Product product = productRepository.getById(productId);
        product.decreaseStock(quantity);
    }

    @Override
    @Transactional
    public void restoreStock(UUID productId, int quantity) {
        Product product = productRepository.getById(productId);
        product.restoreStock(quantity);
    }

    @Override
    public List<ProductInfo> semanticSearch(String query, int size) {
        int limit = size <= 0 ? DEFAULT_SEARCH_SIZE : size;
        Optional<float[]> queryEmbedding = productEmbeddingService.embed(query);

        if (queryEmbedding.isPresent()) {
            List<Product> nearestProducts = productRepository.findNearestProducts(queryEmbedding.get(), limit);
            if (!nearestProducts.isEmpty()) {
                return toInfos(nearestProducts);
            }
        }

        return toInfos(productLexicalSearcher.search(query, limit));
    }

    @Override
    @Transactional
    public int refreshEmbeddings() {
        return productEmbeddingService.refreshEmbeddings();
    }

    private List<ProductInfo> toInfos(List<Product> products) {
        return products.stream()
                .map(ProductInfo::from)
                .toList();
    }
}
