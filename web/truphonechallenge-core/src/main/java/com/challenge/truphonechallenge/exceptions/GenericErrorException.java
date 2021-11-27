package com.challenge.truphonechallenge.exceptions;

public class GenericErrorException extends RuntimeException {
    public GenericErrorException(String message) {
        super("Generic error: " + message);
    }
}