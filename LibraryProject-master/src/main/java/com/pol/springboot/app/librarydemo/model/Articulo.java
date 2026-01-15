package com.pol.springboot.app.librarydemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "articulos")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "tipo")
public abstract class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;


    @Column(nullable = false)
    private boolean alquilado = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isAlquilado() {
        return alquilado;
    }

    public void setAlquilado(boolean alquilado) {
        this.alquilado = alquilado;
    }

    // getters / setters
}
