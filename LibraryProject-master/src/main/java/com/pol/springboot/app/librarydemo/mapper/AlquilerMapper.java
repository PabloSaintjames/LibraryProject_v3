package com.pol.springboot.app.librarydemo.mapper;

import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerCreateDTO;
import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerResponseDTO;
import com.pol.springboot.app.librarydemo.model.Alquiler;
import com.pol.springboot.app.librarydemo.model.Articulo;
import com.pol.springboot.app.librarydemo.model.Libro;
import com.pol.springboot.app.librarydemo.model.Usuario;

import java.time.LocalDate;

public class AlquilerMapper {

    private AlquilerMapper() {}

    /* ============================
       DTO → ENTITY
       ============================ */
    public static Alquiler toEntity(
            AlquilerCreateDTO dto,
            Usuario usuario,
            Articulo articulo
    ) {
        Alquiler alquiler = new Alquiler();
        alquiler.setUsuario(usuario);
        alquiler.setArticulo(articulo);
        alquiler.setFechaAlquiler(LocalDate.now());
        alquiler.setVigente(true);
        return alquiler;
    }

    /* ============================
       ENTITY → DTO
       ============================ */
    public static AlquilerResponseDTO toResponseDTO(Alquiler alquiler) {

        String isbn = null;

        // 🔑 SOLO los libros tienen ISBN
        if (alquiler.getArticulo() instanceof Libro libro) {
            isbn = libro.getIsbn();
        }

        return new AlquilerResponseDTO(
                alquiler.getId(),
                alquiler.getUsuario().getNombre(),
                alquiler.getArticulo().getTitulo(),
                alquiler.isVigente(),
                alquiler.getFechaAlquiler(),
                alquiler.getFechaDevolucion(),
                isbn
        );
    }
}
