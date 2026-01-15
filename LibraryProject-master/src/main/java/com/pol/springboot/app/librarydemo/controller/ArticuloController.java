package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.dto.articulo.ArticuloResponseDTO;
import com.pol.springboot.app.librarydemo.mapper.ArticuloMapper;
import com.pol.springboot.app.librarydemo.model.Libro;
import com.pol.springboot.app.librarydemo.services.ArticuloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    // 🔹 GET todos
    @GetMapping
    public List<ArticuloResponseDTO> findAll() {
        return articuloService.findAll()
                .stream()
                .map(ArticuloMapper::toResponse)
                .toList();
    }

    // 🔹 GET disponibles
    @GetMapping("/disponibles")
    public List<ArticuloResponseDTO> disponibles() {
        return articuloService.findDisponibles()
                .stream()
                .map(ArticuloMapper::toResponse)
                .toList();
    }

    // 🔹 POST crear libro
    @PostMapping
    public ArticuloResponseDTO crear(@RequestBody Map<String, Object> body) {

        if (!"LIBRO".equals(body.get("tipo"))) {
            throw new IllegalArgumentException("Solo se permite crear artículos tipo LIBRO");
        }

        Libro libro = new Libro();
        libro.setTitulo((String) body.get("titulo"));
        libro.setAutor((String) body.get("autor"));
        libro.setIsbn((String) body.get("isbn"));
        libro.setAlquilado(
                body.get("alquilado") != null && (Boolean) body.get("alquilado")
        );

        return ArticuloMapper.toResponse(
                articuloService.save(libro)
        );
    }

    // 🔹 PUT editar libro
    @PutMapping("/{id}")
    public ArticuloResponseDTO actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        return ArticuloMapper.toResponse(
                articuloService.actualizarLibro(id, body)
        );
    }
    @PatchMapping("/{id}/alquiler")
    public ArticuloResponseDTO cambiarEstadoAlquiler(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        if (!body.containsKey("alquilado")) {
            throw new IllegalArgumentException("El campo 'alquilado' es obligatorio");
        }

        boolean alquilado = Boolean.parseBoolean(body.get("alquilado").toString());

        return ArticuloMapper.toResponse(
                articuloService.cambiarEstadoAlquiler(id, alquilado)
        );
    }


    // 🔹 DELETE borrar artículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        articuloService.borrarArticulo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/test")
    public String testPatch() {
        return "PATCH OK";
    }
}
