package com.awards.exceptions;

public class IllegalFileFormatException extends RuntimeException {

    public IllegalFileFormatException(String message, Exception e) {
        super(message, e);
    }
}
