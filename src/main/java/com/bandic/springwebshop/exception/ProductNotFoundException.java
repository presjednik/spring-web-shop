package com.bandic.springwebshop.exception;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(Long entityId) {
        super(entityId, "Product");
    }
}
