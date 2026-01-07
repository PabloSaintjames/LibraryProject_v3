package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.repository.RolRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RolController {

    private final RolRepository rolRepository;

    public RolController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // 🔹 Obtener todos los roles
    @GetMapping
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    // 🔹 Crear un rol
    @PostMapping
    public Rol create(@RequestBody Rol rol) {
        return rolRepository.save(rol);
    }
}
