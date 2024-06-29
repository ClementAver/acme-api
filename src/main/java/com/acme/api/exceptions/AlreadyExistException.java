package com.acme.api.exceptions;

public class AlreadyExistException extends Exception{
    public AlreadyExistException (String message) {
        super(message);
    }
}
