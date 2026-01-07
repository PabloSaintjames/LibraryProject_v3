package com.pol.springboot.app.librarydemo.config;

import com.pol.springboot.app.librarydemo.model.Cd;
import com.pol.springboot.app.librarydemo.model.Libro;
import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.repository.ArticuloRepository;
import com.pol.springboot.app.librarydemo.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepository,
            ArticuloRepository articuloRepository
    ) {
        return args -> {
            initRoles(rolRepository);
            initArticulos(articuloRepository);
        };
    }

    /* =========================
       ROLES
       ========================= */
    private void initRoles(RolRepository rolRepository) {

        if (rolRepository.count() > 0) return;

        rolRepository.save(new Rol(Rol.TipoRol.ADMINISTRADOR));
        rolRepository.save(new Rol(Rol.TipoRol.USUARIO));
        rolRepository.save(new Rol(Rol.TipoRol.INVITADO));
        rolRepository.save(new Rol(Rol.TipoRol.OPERARIO));

        System.out.println("✅ Roles inicializados");
    }

    /* =========================
       ARTÍCULOS
       ========================= */
    private void initArticulos(ArticuloRepository articuloRepository) {

        if (articuloRepository.count() > 0) return;

        Libro libro = new Libro();
        libro.setTitulo("Clean Code");
        libro.setIsbn("9780132350884");
        libro.setAlquilado(false);

        Cd cd = new Cd();
        cd.setTitulo("The Dark Side of the Moon");
        cd.setEstiloMusical("Rock");
        cd.setAlquilado(false);

        articuloRepository.save(libro);
        articuloRepository.save(cd);

        System.out.println("📚 Artículos creados correctamente");
    }
}
