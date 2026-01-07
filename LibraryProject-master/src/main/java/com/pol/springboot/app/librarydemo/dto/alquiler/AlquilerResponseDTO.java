package com.pol.springboot.app.librarydemo.dto.alquiler;

import java.time.LocalDate;

public class AlquilerResponseDTO {

    private Long id;
    private String usuario;
    private String articulo;
    private boolean vigente;
    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucion;

    public AlquilerResponseDTO(
            Long id,
            String usuario,
            String articulo,
            boolean vigente,
            LocalDate fechaAlquiler,
            LocalDate fechaDevolucion
    ) {
        this.id = id;
        this.usuario = usuario;
        this.articulo = articulo;
        this.vigente = vigente;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucion = fechaDevolucion;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getArticulo() {
        return articulo;
    }

    public boolean isVigente() {
        return vigente;
    }

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
}
