package com.proiect.licenta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthenticationRuntimeException extends RuntimeException{

    public AuthenticationRuntimeException(String message) {
        super(message);
    }
}
