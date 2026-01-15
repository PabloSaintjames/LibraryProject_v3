package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerCreateDTO;
import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerResponseDTO;
import com.pol.springboot.app.librarydemo.mapper.AlquilerMapper;
import com.pol.springboot.app.librarydemo.services.AlquilerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
@CrossOrigin(origins = "http://localhost:4200")
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // 🔹 TODOS los alquileres (DTO)
    @GetMapping
    public List<AlquilerResponseDTO> getAll() {
        return alquilerService.findAll()
                .stream()
                .map(AlquilerMapper::toResponseDTO)
                .toList();
    }

    // 🔹 Alquiler por ID (DTO)
    @GetMapping("/{id}")
    public AlquilerResponseDTO getById(@PathVariable Long id) {
        return AlquilerMapper.toResponseDTO(
                alquilerService.findById(id)
        );
    }

    @PostMapping
    public AlquilerResponseDTO crear(
            @Valid @RequestBody AlquilerCreateDTO dto
    ) {
        return AlquilerMapper.toResponseDTO(
                alquilerService.crearAlquiler(dto)
        );
    }

    // 🔹 Devolver artículo (cerrar alquiler)
    @PutMapping("/{id}/devolver")
    public AlquilerResponseDTO devolver(@PathVariable Long id) {
        return AlquilerMapper.toResponseDTO(
                alquilerService.devolverArticulo(id)
        );
    }
}
