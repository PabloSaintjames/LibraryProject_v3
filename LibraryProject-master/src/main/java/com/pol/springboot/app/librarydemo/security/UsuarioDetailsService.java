package com.pol.springboot.app.librarydemo.security;

import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asegúrate de que tu Rol se convierte en "ROLE_<TIPO>", p.ej. ROLE_USUARIO
        String roleName = "ROLE_" + usuario.getRol().getTipo().name();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword()) // ya está encriptada
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
