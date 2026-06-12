package io.github.ladium1.study.practicesimplemsa.order.domain.model;

import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderAlreadyCanceledException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "\"order\"", schema = "study_msa", comment = "주문 정보")
@Entity
@Getter
@Schema(description = "주문 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @Column(comment = "주문 id")
    private UUID id;
    @Column(comment = "상품 id")
    private UUID productId;
    @Column(comment = "구매자 id")
    private UUID buyerId;
    @Column(comment = "주문 수량")
    private Integer quantity;
    @Column(comment = "주문 금액")
    private Long orderPrice;
    @Column(comment = "주문 상태")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(comment = "생성일시")
    private LocalDateTime createdAt;
    @Column(comment = "수정일시")
    private LocalDateTime updatedAt;

    public Order(UUID productId, UUID buyerId, Integer quantity, Long orderPrice) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
    }

    public void cancel() {
        if (this.status == OrderStatus.CANCELED) {
            throw new OrderAlreadyCanceledException(this.id);
        }
        this.status = OrderStatus.CANCELED;
    }

    @PrePersist
    private void onCreate() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if(this.status == null) {
            this.status = OrderStatus.CREATED;
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
