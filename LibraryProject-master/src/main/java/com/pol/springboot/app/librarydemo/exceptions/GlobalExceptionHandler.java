package com.pol.springboot.app.librarydemo.exceptions;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /* ==========================
       ERRORES DE NEGOCIO
       ========================== */

    @ExceptionHandler(ArticuloAlquiladoException.class)
    public ResponseEntity<ApiError> handleArticuloAlquilado(
            ArticuloAlquiladoException ex
    ) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AlquilerYaCerradoException.class)
    public ResponseEntity<ApiError> handleAlquilerCerrado(
            AlquilerYaCerradoException ex
    ) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AlquilerNotFoundException.class)
    public ResponseEntity<ApiError> handleAlquilerNotFound(
            AlquilerNotFoundException ex
    ) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity<ApiError> handleAccesoDenegado(
            AccesoDenegadoException ex
    ) {
        return build(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    /* ==========================
       SEGURIDAD / AUTORIZACIÓN
       ========================== */

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleSpringAccessDenied(
            AccessDeniedException ex
    ) {
        return build(
                HttpStatus.FORBIDDEN,
                "No tienes permisos para realizar esta acción"
        );
    }

    /* ==========================
       AUTENTICACIÓN / JWT
       ========================== */

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ApiError> handleCredencialesInvalidas(
            CredencialesInvalidasException ex
    ) {
        return build(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(
            AuthenticationException ex
    ) {
        return build(
                HttpStatus.UNAUTHORIZED,
                "No autenticado o token inválido"
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwt(JwtException ex) {
        return build(
                HttpStatus.UNAUTHORIZED,
                "JWT inválido o expirado"
        );
    }

    /* ==========================
       VALIDACIONES
       ========================== */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Datos inválidos");

        return build(HttpStatus.BAD_REQUEST, mensaje);
    }

    /* ==========================
       FALLBACK GLOBAL
       ========================== */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {

        // Log real (producción)
        log.error("Error no controlado", ex);

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor"
        );
    }

    /* ==========================
       UTIL
       ========================== */

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String message
    ) {
        ApiError error = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(error);
    }
}
