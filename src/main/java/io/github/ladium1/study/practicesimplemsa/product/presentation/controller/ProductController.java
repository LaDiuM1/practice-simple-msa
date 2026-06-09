package io.github.ladium1.study.practicesimplemsa.product.presentation.controller;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.usecase.ProductUseCase;
import io.github.ladium1.study.practicesimplemsa.product.presentation.dto.ProductCreateRequest;
import io.github.ladium1.study.practicesimplemsa.product.presentation.dto.ProductUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @Operation(summary = "상품 정보 조회")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfo> get(@PathVariable UUID productId) {
        ProductInfo productInfo = productUseCase.get(productId);
        return ResponseEntity.ok(productInfo);
    }

    @Operation(summary = "상품 정보 생성")
    @PostMapping
    public ResponseEntity<ProductInfo> create(@Valid @RequestBody ProductCreateRequest request) {
        ProductInfo productInfo = productUseCase.create(request.toCommand());
        URI uri = URI.create("/products/" + productInfo.id());
        return ResponseEntity.created(uri).body(productInfo);
    }

    @Operation(summary = "상품 정보 수정")
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductInfo> update(@PathVariable UUID productId,
                                              @Valid @RequestBody ProductUpdateRequest request) {
        ProductInfo productInfo = productUseCase.update(request.toCommand(productId));
        return ResponseEntity.ok(productInfo);
    }

    @Operation(summary = "상품 정보 삭제")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable UUID productId) {
        productUseCase.delete(productId);
        return ResponseEntity.noContent().build();
    }

}
