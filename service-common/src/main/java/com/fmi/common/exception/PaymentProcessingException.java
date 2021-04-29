package com.fmi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PaymentProcessingException extends Exception {
    public PaymentProcessingException(String message) {
        super(message);
    }
}
