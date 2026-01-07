//package com.pol.springboot.app.librarydemo.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import javax.sql.DataSource; // Importa la interfaz de DataSource
//import org.springframework.boot.jdbc.DataSourceBuilder; // Importa el constructor de DataSource
//
//@Configuration
//public class SecurityConfig {
//
//        @Bean
//    public DataSource dataSource() {
//        // Usa DataSourceBuilder para construir manualmente el DataSource de SQLite
//        return DataSourceBuilder.create()
//                .driverClassName("org.sqlite.JDBC")
//                .url("jdbc:sqlite:libraryDemo.sqlite") // 💡 Asegúrate que la URL sea correcta
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Configuración de seguridad.
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        return http.build();
//    }
//}