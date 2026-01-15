package com.pol.springboot.app.librarydemo.config;

import com.pol.springboot.app.librarydemo.model.Libro;
import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.repository.ArticuloRepository;
import com.pol.springboot.app.librarydemo.repository.RolRepository;
import com.pol.springboot.app.librarydemo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            ArticuloRepository articuloRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        return args -> {

            /* ========= ROLES ========= */
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol(Rol.TipoRol.ADMINISTRADOR));
                rolRepository.save(new Rol(Rol.TipoRol.USUARIO));
                rolRepository.save(new Rol(Rol.TipoRol.OPERARIO));
                rolRepository.save(new Rol(Rol.TipoRol.INVITADO));
            }

            Rol adminRol = rolRepository.findByTipo(Rol.TipoRol.ADMINISTRADOR).orElseThrow();
            Rol userRol  = rolRepository.findByTipo(Rol.TipoRol.USUARIO).orElseThrow();
            Rol operRol  = rolRepository.findByTipo(Rol.TipoRol.OPERARIO).orElseThrow();

            /* ========= USUARIOS ========= */
            if (usuarioRepository.count() == 0) {

                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setEmail("admin@demo.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRol(adminRol);

                Usuario usuario = new Usuario();
                usuario.setNombre("Pablo");
                usuario.setEmail("usuario@demo.com");
                usuario.setPassword(passwordEncoder.encode("1234"));
                usuario.setRol(userRol);

                Usuario operario = new Usuario();
                operario.setNombre("Operario");
                operario.setEmail("operario@demo.com");
                operario.setPassword(passwordEncoder.encode("1234"));
                operario.setRol(operRol);

                usuarioRepository.save(admin);
                usuarioRepository.save(usuario);
                usuarioRepository.save(operario);
            }

            /* ========= LIBROS ========= */
            if (articuloRepository.count() == 0) {

                Libro l1 = new Libro();
                l1.setTitulo("Clean Code");
                l1.setAutor("Robert C. Martin");
                l1.setIsbn("9780132350884");
                l1.setAlquilado(false);

                Libro l2 = new Libro();
                l2.setTitulo("Clean Architecture");
                l2.setAutor("Robert C. Martin");
                l2.setIsbn("9780134494166");
                l2.setAlquilado(false);

                Libro l3 = new Libro();
                l3.setTitulo("Effective Java");
                l3.setAutor("Joshua Bloch");
                l3.setIsbn("9780134685991");
                l3.setAlquilado(false);

                Libro l4 = new Libro();
                l4.setTitulo("Design Patterns");
                l4.setAutor("Erich Gamma");
                l4.setIsbn("9780201633610");
                l4.setAlquilado(false);

                Libro l5 = new Libro();
                l5.setTitulo("Refactoring");
                l5.setAutor("Martin Fowler");
                l5.setIsbn("9780201485677");
                l5.setAlquilado(false);

                Libro l6 = new Libro();
                l6.setTitulo("Domain-Driven Design");
                l6.setAutor("Eric Evans");
                l6.setIsbn("9780321125217");
                l6.setAlquilado(false);

                Libro l7 = new Libro();
                l7.setTitulo("Spring in Action");
                l7.setAutor("Craig Walls");
                l7.setIsbn("9781617294945");
                l7.setAlquilado(false);

                Libro l8 = new Libro();
                l8.setTitulo("Head First Design Patterns");
                l8.setAutor("Eric Freeman");
                l8.setIsbn("9780596007126");
                l8.setAlquilado(false);

                Libro l9 = new Libro();
                l9.setTitulo("The Pragmatic Programmer");
                l9.setAutor("Andrew Hunt");
                l9.setIsbn("9780201616224");
                l9.setAlquilado(false);

                Libro l10 = new Libro();
                l10.setTitulo("Java Concurrency in Practice");
                l10.setAutor("Brian Goetz");
                l10.setIsbn("9780321349606");
                l10.setAlquilado(false);

                articuloRepository.saveAll(
                        List.of(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10)
                );
            }



            System.out.println("✅ Datos iniciales cargados correctamente");
        };
    }
}
