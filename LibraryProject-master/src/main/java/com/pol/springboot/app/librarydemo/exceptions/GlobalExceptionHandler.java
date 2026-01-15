package com.pol.springboot.app.librarydemo.exceptions;

import com.pol.springboot.app.librarydemo.dto.error.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =============================
       400 — VALIDACIONES DTO
       ============================= */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        ApiErrorDTO error = new ApiErrorDTO(
                400,
                "BAD_REQUEST",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    /* =============================
       404 — NOT FOUND
       ============================= */
    @ExceptionHandler({
            UsuarioNotFoundException.class,
            ArticuloNotFoundException.class,
            AlquilerNotFoundException.class
    })
    public ResponseEntity<ApiErrorDTO> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        ApiErrorDTO error = new ApiErrorDTO(
                404,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /* =============================
       409 — CONFLICT (NEGOCIO / BD)
       ============================= */
    @ExceptionHandler({
            ArticuloAlquiladoException.class,
            AlquilerYaCerradoException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ApiErrorDTO> handleConflict(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        String message = ex instanceof DataIntegrityViolationException
                ? "Conflicto de datos (posible duplicado)"
                : ex.getMessage();

        ApiErrorDTO error = new ApiErrorDTO(
                409,
                "CONFLICT",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /* =============================
       403 — FORBIDDEN (ROLES)
       ============================= */
    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity<ApiErrorDTO> handleForbidden(
            AccesoDenegadoException ex,
            HttpServletRequest request
    ) {
        ApiErrorDTO error = new ApiErrorDTO(
                403,
                "FORBIDDEN",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    /* =============================
   401 — AUTHENTICATION
   ============================= */
    @ExceptionHandler({
            BadCredentialsException.class,
            AuthenticationException.class
    })
    public ResponseEntity<ApiErrorDTO> handleUnauthorized(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        ApiErrorDTO error = new ApiErrorDTO(
                401,
                "UNAUTHORIZED",
                "Credenciales inválidas",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /* =============================
   400 — BAD REQUEST (LÓGICA SIMPLE)
   ============================= */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ApiErrorDTO error = new ApiErrorDTO(
                400,
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    /* =============================
       500 — ERROR INTERNO REAL
       ============================= */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleInternalError(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiErrorDTO error = new ApiErrorDTO(
                500,
                "INTERNAL_SERVER_ERROR",
                "Error interno del servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
