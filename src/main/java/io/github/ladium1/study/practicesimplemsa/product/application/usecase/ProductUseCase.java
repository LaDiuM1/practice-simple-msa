package io.github.ladium1.study.practicesimplemsa.product.application.usecase;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductCreateCommand;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductUpdateCommand;

import java.util.UUID;

public interface ProductUseCase {

    ProductInfo create(ProductCreateCommand command);

    ProductInfo get(UUID productId);

    ProductInfo update(ProductUpdateCommand command);

    void delete(UUID productId);
}
