package io.github.ladium1.study.practicesimplemsa.product.presentation.exception;

import io.github.ladium1.study.practicesimplemsa.product.domain.exception.ProductNotFoundException;
import io.github.ladium1.study.practicesimplemsa.product.presentation.controller.ProductController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ProductController.class})
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFound(ProductNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

}
