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
import java.util.List;
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

    @Operation(summary = "상품 의미 검색", description = "상품명 임베딩 기준 검색어와 가장 유사한 상품 조회")
    @GetMapping("/semantic-search")
    public ResponseEntity<List<ProductInfo>> semanticSearch(@RequestParam String query,
                                                            @RequestParam(defaultValue = "5") int size) {
        List<ProductInfo> productInfos = productUseCase.semanticSearch(query, size);
        return ResponseEntity.ok(productInfos);
    }

    @Operation(summary = "상품 임베딩 재생성", description = "전체 상품에 대한 임베딩 재실행 후 업데이트 상품 수 반환")
    @PostMapping("/embeddings/refresh")
    public ResponseEntity<Integer> refreshEmbeddings() {
        int updatedCount = productUseCase.refreshEmbeddings();
        return ResponseEntity.ok(updatedCount);
    }

}
