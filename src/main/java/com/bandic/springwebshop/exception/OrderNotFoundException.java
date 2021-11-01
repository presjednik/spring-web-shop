package com.bandic.springwebshop.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(Long entityId) {
        super(entityId, "Order");
    }
}
