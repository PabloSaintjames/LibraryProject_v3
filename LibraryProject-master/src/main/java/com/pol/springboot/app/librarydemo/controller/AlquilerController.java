package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerCreateDTO;
import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerResponseDTO;
import com.pol.springboot.app.librarydemo.mapper.AlquilerMapper;
import com.pol.springboot.app.librarydemo.model.Alquiler;
import com.pol.springboot.app.librarydemo.services.AlquilerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
@CrossOrigin(origins = "http://localhost:4200") // Angular
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // 🔹 Obtener todos los alquileres
    @GetMapping
    public List<Alquiler> getAll() {
        return alquilerService.findAll();
    }

    // 🔹 Obtener alquiler por id
    @GetMapping("/{id}")
    public Alquiler getById(@PathVariable Long id) {
        return alquilerService.findById(id);
    }

    // 🔹 Crear un alquiler
    @PostMapping
    public AlquilerResponseDTO crear(
            @Valid @RequestBody AlquilerCreateDTO dto
    ) {
        Alquiler alquiler = alquilerService.crearAlquiler(dto);
        return AlquilerMapper.toResponseDTO(alquiler);
    }

    // 🔹 Devolver un artículo
    @PutMapping("/{id}/devolver")
    public Alquiler devolver(@PathVariable Long id) {
        return alquilerService.devolverArticulo(id);
    }

    // 🔹 Eliminar alquiler (opcional)
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        alquilerService.deleteById(id);
    }
}
