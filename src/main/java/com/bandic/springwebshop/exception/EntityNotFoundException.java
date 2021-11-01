package com.bandic.springwebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long entityId, String entityName) {
        super(createMessage(entityId, entityName));
    }

    private static String createMessage(Long entityId, String entityName) {
        return entityName + " with id " + entityId + " doesn't exist.";
    }
}
