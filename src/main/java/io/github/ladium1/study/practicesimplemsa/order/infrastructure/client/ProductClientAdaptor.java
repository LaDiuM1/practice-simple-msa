package io.github.ladium1.study.practicesimplemsa.order.infrastructure.client;

import io.github.ladium1.study.practicesimplemsa.order.application.client.ProductClient;
import io.github.ladium1.study.practicesimplemsa.order.application.client.ProductSnapshot;
import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderProductOutOfStockException;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.usecase.ProductUseCase;
import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductOutOfStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductClientAdaptor implements ProductClient {

    private final ProductUseCase productUseCase;

    @Override
    public ProductSnapshot getProduct(UUID productId) {
        try {
            ProductInfo productInfo = productUseCase.get(productId);
            return new ProductSnapshot(
                    productInfo.id(),
                    productInfo.name(),
                    productInfo.price()
            );
        } catch (ProductNotFoundException exception) {
            throw new OrderProductNotFoundException(productId);
        }
    }

    @Override
    public void decreaseStock(UUID productId, int quantity) {
        try {
            productUseCase.decreaseStock(productId, quantity);
        } catch (ProductNotFoundException exception) {
            throw new OrderProductNotFoundException(productId);
        } catch (ProductOutOfStockException exception) {
            throw new OrderProductOutOfStockException(productId);
        }
    }

    @Override
    public void restoreStock(UUID productId, int quantity) {
        try {
            productUseCase.restoreStock(productId, quantity);
        } catch (ProductNotFoundException exception) {
            throw new OrderProductNotFoundException(productId);
        }
    }
}
