package com.pol.springboot.app.librarydemo.config;

import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol(Rol.TipoRol.ADMINISTRADOR));
                rolRepository.save(new Rol(Rol.TipoRol.USUARIO));
                rolRepository.save(new Rol(Rol.TipoRol.INVITADO));
                rolRepository.save(new Rol(Rol.TipoRol.OPERARIO));
            }
        };
    }
}
