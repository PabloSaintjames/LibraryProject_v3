package com.pol.springboot.app.librarydemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoRol tipo;

    public enum TipoRol {
        ADMINISTRADOR,
        USUARIO,
        INVITADO,
        OPERARIO
    }

    public Rol() {}

    public Rol(TipoRol tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public TipoRol getTipo() {
        return tipo;
    }

    public void setTipo(TipoRol tipo) {
        this.tipo = tipo;
    }
}
