package com.mentee.microservice.exception;

public class CustomeMessageException extends RuntimeException {

    public CustomeMessageException(String message) {
        super(message);
    }
}
