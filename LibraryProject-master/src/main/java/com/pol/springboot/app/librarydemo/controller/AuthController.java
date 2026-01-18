package com.pol.springboot.app.librarydemo.controller;

import com.pol.springboot.app.librarydemo.dto.auth.LoginRequestDTO;
import com.pol.springboot.app.librarydemo.dto.auth.LoginResponseDTO;
import com.pol.springboot.app.librarydemo.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }
}
