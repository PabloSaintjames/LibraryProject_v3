package com.pol.springboot.app.librarydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration  // Indica que esta clase es una clase de configuración de Spring
public class CorsConfig {

    @Bean  // Define un Bean que Spring manejará automáticamente
    CorsConfigurationSource corsConfigurationSource() {
        // Crear la configuración CORS
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir el envío de credenciales (como cookies y cabeceras de autenticación)
        configuration.setAllowCredentials(true);

        // Especificar los orígenes permitidos (en este caso, localhost:4200, típico para desarrollo con Angular)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        // Permitir los métodos HTTP: GET, POST, PUT, DELETE
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));


        // Permitir todas las cabeceras en las solicitudes
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Crear un objeto UrlBasedCorsConfigurationSource para registrar la configuración
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Registrar la configuración de CORS para todas las rutas ("/**")
        source.registerCorsConfiguration("/**", configuration);

        // Devolver la configuración CORS registrada
        return source;
    }
}
