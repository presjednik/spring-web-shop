package com.bandic.springwebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException(Long productId) {
        super(createMessage(productId));
    }

    private static String createMessage(Long productId) {
        return "Product with id " + productId + " is not available";
    }
}
