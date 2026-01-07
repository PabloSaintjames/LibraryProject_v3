package com.pol.springboot.app.librarydemo.exceptions;

public class ArticuloNotFoundException extends RuntimeException {
    public ArticuloNotFoundException(String message) {
        super(message);
    }
}
