package io.github.ladium1.study.practicesimplemsa.order.presentation.controller;

import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderInfo;
import io.github.ladium1.study.practicesimplemsa.order.application.usecase.OrderUseCase;
import io.github.ladium1.study.practicesimplemsa.order.presentation.dto.OrderCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    @Operation(summary = "주문 정보 조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfo> get(@PathVariable UUID orderId) {
        OrderInfo orderInfo = orderUseCase.get(orderId);
        return ResponseEntity.ok(orderInfo);
    }

    @Operation(summary = "주문 생성")
    @PostMapping
    public ResponseEntity<OrderInfo> create(@Valid @RequestBody OrderCreateRequest request) {
        OrderInfo orderInfo = orderUseCase.create(request.toCommand());
        URI uri = URI.create("/orders/" + orderInfo.id());
        return ResponseEntity.created(uri).body(orderInfo);
    }

    @Operation(summary = "주문 취소")
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderInfo> cancel(@PathVariable UUID orderId) {
        OrderInfo orderInfo = orderUseCase.cancel(orderId);
        return ResponseEntity.ok(orderInfo);
    }

}
