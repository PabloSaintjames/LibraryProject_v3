package com.pol.springboot.app.librarydemo.repository;

import com.pol.springboot.app.librarydemo.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA ya proporciona findById, save, findAll, etc.
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {


    List<Articulo> findByAlquiladoFalse();
    // Si necesitas alguna consulta personalizada, puedes añadirla aquí.
    //Metodos que podría necesitar...
    //List<Articulo> findByAlquiladoFalse();

    //List<Articulo> findByCategoria(String categoria);
}