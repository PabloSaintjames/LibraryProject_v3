package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.model.Articulo;
import com.pol.springboot.app.librarydemo.services.ArticuloService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    // 🔹 Obtener todos los artículos
    @GetMapping
    public List<Articulo> findAll() {
        return articuloService.findAll();
    }

    // 🔹 Crear un artículo
    @PostMapping
    public Articulo create(@RequestBody Articulo articulo) {
        return articuloService.createArticulo(articulo);

    }

    @GetMapping("/disponibles")
    public List<Articulo> disponibles() {
        return articuloService.findDisponibles();
    }

    @PutMapping("/{id}")
    public Articulo update(@PathVariable Long id, @RequestBody Articulo articulo) {
        return articuloService.updateArticulo(id, articulo);
    }


    // 🔹 Eliminar artículo por id
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        articuloService.borrarArticulo(id);
    }
}
