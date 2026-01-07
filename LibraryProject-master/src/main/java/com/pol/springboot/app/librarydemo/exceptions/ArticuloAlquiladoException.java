package com.pol.springboot.app.librarydemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Mapea automáticamente esta excepción a una respuesta HTTP 409
@ResponseStatus(HttpStatus.CONFLICT)
public class ArticuloAlquiladoException extends RuntimeException {

    public ArticuloAlquiladoException(String message) {
        super(message);
    }
}