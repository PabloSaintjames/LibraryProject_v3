package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.dto.usuario.UsuarioCreateDTO;
import com.pol.springboot.app.librarydemo.exceptions.UsuarioNotFoundException;
import com.pol.springboot.app.librarydemo.mapper.UsuarioMapper;
import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.repository.RolRepository;
import com.pol.springboot.app.librarydemo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

        private final UsuarioRepository usuarioRepository;
        private final RolRepository rolRepository;
        private final BCryptPasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario createUser(UsuarioCreateDTO dto) {

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no existe"));

        Usuario usuario = UsuarioMapper.toEntity(dto, rol);

        // 🔐 Encriptar password
        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        return usuarioRepository.save(usuario);
    }

    public Usuario usuarioActualMock() {
        return usuarioRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuario mock no existe"));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuario no encontrado con email: " + email
                        )
                );
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException("Usuario no encontrado con ID: " + id));
    }
}
