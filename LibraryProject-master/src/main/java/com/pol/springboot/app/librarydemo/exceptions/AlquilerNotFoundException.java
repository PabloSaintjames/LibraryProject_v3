package com.pol.springboot.app.librarydemo.exceptions;

public class AlquilerNotFoundException extends RuntimeException {
    public AlquilerNotFoundException(String message) {
        super(message);
    }
}
