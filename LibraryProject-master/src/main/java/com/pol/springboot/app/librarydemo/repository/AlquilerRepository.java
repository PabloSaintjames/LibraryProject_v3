package com.pol.springboot.app.librarydemo.repository;

import com.pol.springboot.app.librarydemo.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    @Query("""
        SELECT a
        FROM Alquiler a
        JOIN FETCH a.usuario
        JOIN FETCH a.articulo
    """)
    List<Alquiler> findAllWithUsuarioAndArticulo();
}
