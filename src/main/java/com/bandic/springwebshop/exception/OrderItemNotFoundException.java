package com.bandic.springwebshop.exception;

public class OrderItemNotFoundException extends EntityNotFoundException {

    public OrderItemNotFoundException(Long entityId) {
        super(entityId, "Order item");
    }
}
