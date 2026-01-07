package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.exceptions.UsuarioNotFoundException;
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

        public Usuario createUser(Usuario usuario) {

            Rol rol = rolRepository.findById(usuario.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no existe en la base de datos"));

            usuario.setRol(rol);

            // 🔐 ENCRIPTAR AQUÍ
            usuario.setPassword(
                    passwordEncoder.encode(usuario.getPassword())
            );

            return usuarioRepository.save(usuario);
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
