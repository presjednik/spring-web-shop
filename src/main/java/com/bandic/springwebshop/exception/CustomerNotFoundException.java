package com.bandic.springwebshop.exception;

public class CustomerNotFoundException extends EntityNotFoundException {

    public CustomerNotFoundException(Long entityId) {
        super(entityId, "Customer");
    }
}
