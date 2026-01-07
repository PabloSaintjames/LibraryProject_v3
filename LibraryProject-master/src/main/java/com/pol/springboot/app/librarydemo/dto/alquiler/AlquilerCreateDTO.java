package com.pol.springboot.app.librarydemo.dto.alquiler;

import jakarta.validation.constraints.NotNull;

public class AlquilerCreateDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long articuloId;

    // getters y setters

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Long articuloId) {
        this.articuloId = articuloId;
    }
}
