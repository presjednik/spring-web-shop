package com.bandic.springwebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class HnbExchangeException extends RuntimeException {

    public HnbExchangeException() {
        super("HNB service is unavailable.");
    }

}
