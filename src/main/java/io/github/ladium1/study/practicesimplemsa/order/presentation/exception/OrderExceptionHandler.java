package io.github.ladium1.study.practicesimplemsa.order.presentation.exception;

import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderAlreadyCanceledException;
import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderNotFoundException;
import io.github.ladium1.study.practicesimplemsa.order.presentation.controller.OrderController;
import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductOutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {OrderController.class})
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFound(OrderNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(OrderAlreadyCanceledException.class)
    public ProblemDetail handleOrderAlreadyCanceled(OrderAlreadyCanceledException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFound(ProductNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ProblemDetail handleProductOutOfStock(ProductOutOfStockException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

}
