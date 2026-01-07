package com.pol.springboot.app.librarydemo.model;

import com.pol.springboot.app.librarydemo.model.Articulo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cds")
public class Cd extends Articulo {

    @Column(nullable = false)
    private String estiloMusical;

    public Cd() {}

    public Cd(String estiloMusical) {
        this.estiloMusical = estiloMusical;
    }

    public String getEstiloMusical() {
        return estiloMusical;
    }

    public void setEstiloMusical(String estiloMusical) {
        this.estiloMusical = estiloMusical;
    }
}
