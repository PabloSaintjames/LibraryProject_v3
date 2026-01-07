package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.exceptions.ArticuloAlquiladoException;
import com.pol.springboot.app.librarydemo.exceptions.ArticuloNotFoundException;
import com.pol.springboot.app.librarydemo.model.Articulo;
import com.pol.springboot.app.librarydemo.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloService {

    private final ArticuloRepository articuloRepository;

    public ArticuloService(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    public List<Articulo> findAll() {
        return articuloRepository.findAll();
    }

    public Articulo findById(Long id) {
        return articuloRepository.findById(id)
                .orElseThrow(() ->
                        new ArticuloNotFoundException("Artículo no encontrado con ID: " + id));
    }

    public Articulo createArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public Articulo save(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public Articulo updateArticulo(Long id, Articulo articuloActualizado) {
        Articulo articulo = findById(id);

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("No se puede actualizar un artículo alquilado");
        }

        articuloActualizado.setId(id);
        return articuloRepository.save(articuloActualizado);
    }

    public void borrarArticulo(Long id) {
        Articulo articulo = findById(id);

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("No se puede borrar un artículo alquilado");
        }

        articuloRepository.deleteById(id);
    }

    public List<Articulo> findDisponibles() {
        return articuloRepository.findByAlquiladoFalse();
    }
}
