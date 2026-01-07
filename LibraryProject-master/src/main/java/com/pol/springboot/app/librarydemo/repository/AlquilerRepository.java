package com.pol.springboot.app.librarydemo.repository;

import com.pol.springboot.app.librarydemo.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    List<Alquiler> findByUsuarioId(Long usuarioId);

    List<Alquiler> findByArticuloId(Long articuloId);

    List<Alquiler> findByVigenteTrue();
}
