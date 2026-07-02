package io.github.ladium1.study.practicesimplemsa.product.application.service;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductCreateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductLlmSearchInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductUpdateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.llm.ProductLlmAnswerGenerator;
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
    private final ProductLlmAnswerGenerator productLlmAnswerGenerator;

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
        return toInfos(searchProducts(query, size));
    }

    @Override
    public ProductLlmSearchInfo llmSearch(String question, int size) {
        List<Product> products = searchProducts(question, size);
        String answer = productLlmAnswerGenerator.generateAnswer(question, products);
        if (answer == null || answer.isBlank()) {
            answer = fallbackAnswer(products);
        }
        return ProductLlmSearchInfo.of(question, answer, toInfos(products));
    }

    @Override
    @Transactional
    public int refreshEmbeddings() {
        return productEmbeddingService.refreshEmbeddings();
    }

    private List<Product> searchProducts(String query, int size) {
        int limit = size <= 0 ? DEFAULT_SEARCH_SIZE : size;
        Optional<float[]> queryEmbedding = productEmbeddingService.embed(query);

        if (queryEmbedding.isPresent()) {
            List<Product> nearestProducts = productRepository.findNearestProducts(queryEmbedding.get(), limit);
            if (!nearestProducts.isEmpty()) {
                return nearestProducts;
            }
        }

        return productLexicalSearcher.search(query, limit);
    }

    private String fallbackAnswer(List<Product> products) {
        if (products.isEmpty()) {
            return "관련 상품을 찾지 못했습니다.";
        }
        return "검색된 상품을 기준으로 추천했습니다. 아래 목록을 확인해 주세요.";
    }

    private List<ProductInfo> toInfos(List<Product> products) {
        return products.stream()
                .map(ProductInfo::from)
                .toList();
    }
}
