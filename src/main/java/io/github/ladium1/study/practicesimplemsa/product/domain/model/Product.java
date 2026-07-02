package io.github.ladium1.study.practicesimplemsa.product.domain.model;

import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductOutOfStockException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "\"product\"", schema = "study_msa", comment = "상품 정보")
@Entity
@Getter
@Schema(description = "상품 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(comment = "상품 id")
    private UUID id;
    @Column(comment = "상품명")
    private String name;
    @Column(comment = "가격")
    private Long price;
    @Column(comment = "재고량")
    private Integer stockQuantity;
    @Column(comment = "상품 상태")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column(comment = "판매자 id")
    private UUID sellerId;
    @Schema(hidden = true)
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 1536)
    @Column(comment = "상품 임베딩")
    private float[] embedding;
    @Column(comment = "생성일시")
    private LocalDateTime createdAt;
    @Column(comment = "수정일시")
    private LocalDateTime updatedAt;

    public Product(String name, Long price, UUID sellerId) {
        this.name = name;
        this.price = price;
        this.sellerId = sellerId;
    }

    public void updateEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

    public void update(String name,
                       Long price,
                       Integer stockQuantity,
                       ProductStatus status) {
        if (name != null) { this.name = name; }
        if (price != null) { this.price = price; }
        if (stockQuantity != null) { this.stockQuantity = stockQuantity; }
        if (status != null) { this.status = status; }
    }

    public void decreaseStock(int quantity) {
        if (this.stockQuantity == null || this.stockQuantity < quantity) {
            throw new ProductOutOfStockException(this.id);
        }
        this.stockQuantity -= quantity;
        if (this.stockQuantity == 0) {
            this.status = ProductStatus.SOLD_OUT;
        }
    }

    public void restoreStock(int quantity) {
        this.stockQuantity = (this.stockQuantity == null ? quantity : this.stockQuantity + quantity);
        if (this.status == ProductStatus.SOLD_OUT && this.stockQuantity > 0) {
            this.status = ProductStatus.FOR_SALE;
        }
    }

    @PrePersist
    private void onCreate() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if(this.stockQuantity == null) {
            this.stockQuantity = 0;
        }
        if(this.status == null) {
            this.status = ProductStatus.PREPARING;
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
