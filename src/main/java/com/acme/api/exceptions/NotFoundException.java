package com.acme.api.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException (String message) {
        super(message);
    }
}
