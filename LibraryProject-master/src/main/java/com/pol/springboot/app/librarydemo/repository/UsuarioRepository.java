package com.pol.springboot.app.librarydemo.repository;

import com.pol.springboot.app.librarydemo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}