package com.vinsguru.webflux_playground.sec04.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
