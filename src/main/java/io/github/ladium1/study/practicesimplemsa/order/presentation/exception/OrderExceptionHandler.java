package io.github.ladium1.study.practicesimplemsa.order.presentation.exception;

import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderAlreadyCanceledException;
import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderNotFoundException;
import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderProductOutOfStockException;
import io.github.ladium1.study.practicesimplemsa.order.presentation.controller.OrderController;
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

    @ExceptionHandler(OrderProductNotFoundException.class)
    public ProblemDetail handleOrderProductNotFound(OrderProductNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    @ExceptionHandler(OrderProductOutOfStockException.class)
    public ProblemDetail handleOrderProductOutOfStock(OrderProductOutOfStockException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

}
