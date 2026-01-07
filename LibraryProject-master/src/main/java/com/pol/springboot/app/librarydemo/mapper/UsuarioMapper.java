package com.pol.springboot.app.librarydemo.mapper;

import com.pol.springboot.app.librarydemo.dto.usuario.UsuarioCreateDTO;
import com.pol.springboot.app.librarydemo.dto.usuario.UsuarioResponseDTO;
import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.model.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {
        // Evita instanciación
    }

    // 🔹 DTO → Entity (CREATE)
    public static Usuario toEntity(UsuarioCreateDTO dto, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // aún sin encriptar
        usuario.setRol(rol);
        return usuario;
    }

    // 🔹 Entity → DTO (RESPONSE)
    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getTipo().name()
        );
    }
}
