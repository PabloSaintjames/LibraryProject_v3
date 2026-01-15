package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.exceptions.ArticuloAlquiladoException;
import com.pol.springboot.app.librarydemo.exceptions.ArticuloNotFoundException;
import com.pol.springboot.app.librarydemo.model.Articulo;
import com.pol.springboot.app.librarydemo.model.Libro;
import com.pol.springboot.app.librarydemo.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticuloService {

    private final ArticuloRepository articuloRepository;

    public ArticuloService(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    // 🔹 Lectura
    public List<Articulo> findAll() {
        return articuloRepository.findAll();
    }

    public Articulo findById(Long id) {
        return articuloRepository.findById(id)
                .orElseThrow(() ->
                        new ArticuloNotFoundException("Artículo no encontrado con ID: " + id));
    }

    public List<Articulo> findDisponibles() {
        return articuloRepository.findByAlquiladoFalse();
    }

    public Articulo cambiarEstadoAlquiler(Long id, boolean alquilado) {
        Articulo articulo = findById(id);
        articulo.setAlquilado(alquilado);
        return articuloRepository.save(articulo);
    }

    // 🔹 Creación
    public Articulo save(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // 🔹 Actualización (SOLO libros, parcial)
    public Articulo actualizarLibro(Long id, Map<String, Object> body) {

        Articulo articulo = findById(id);

        if (!(articulo instanceof Libro libro)) {
            throw new IllegalStateException("Solo se pueden editar libros");
        }

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("No se puede actualizar un artículo alquilado");
        }

        if (body.containsKey("titulo")) {
            libro.setTitulo((String) body.get("titulo"));
        }

        if (body.containsKey("autor")) {
            libro.setAutor((String) body.get("autor"));
        }

        if (body.containsKey("alquilado")) {
            libro.setAlquilado((Boolean) body.get("alquilado"));
        }

        // ❌ isbn NO se toca
        return articuloRepository.save(libro);
    }

    // 🔹 Borrado
    public void borrarArticulo(Long id) {
        Articulo articulo = findById(id);

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("No se puede borrar un artículo alquilado");
        }

        articuloRepository.delete(articulo);
    }
}
