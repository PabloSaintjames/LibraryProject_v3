package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 🔹 Obtener todos los usuarios
    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    // 🔹 Crear usuario
    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioService.createUser(usuario);
    }

    // 🔹 Borrar usuario
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }
}
