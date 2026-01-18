package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.dto.auth.LoginRequestDTO;
import com.pol.springboot.app.librarydemo.dto.auth.LoginResponseDTO;
import com.pol.springboot.app.librarydemo.exceptions.CredencialesInvalidasException;
import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.repository.UsuarioRepository;
import com.pol.springboot.app.librarydemo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            UsuarioRepository usuarioRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new CredencialesInvalidasException("Email o contraseña incorrectos")
                );

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Email o contraseña incorrectos");
        }

        String token = jwtUtil.generarToken(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRol().getTipo().name()
        );

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getRol().getTipo().name(),
                token
        );
    }
}
