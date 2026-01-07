package com.pol.springboot.app.librarydemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro extends Articulo {

    @Column(nullable = false, unique = true)
    private String isbn;

    public Libro() {}

    public Libro(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
