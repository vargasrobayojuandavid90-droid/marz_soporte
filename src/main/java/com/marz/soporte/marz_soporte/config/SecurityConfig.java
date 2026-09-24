package com.marz.soporte.marz_soporte.config;

import com.marz.soporte.marz_soporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return email -> usuarioRepository.findByEmail(email)
                .map(u -> User.builder()
                        .username(u.getEmail())
                        .password(u.getPassword())
                        .roles(u.getRol().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar las pruebas REST
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Para permitir la consola H2
                .authorizeHttpRequests(auth -> auth
                        // 1. RECURSOS PÚBLICOS (Página principal, HTML, CSS, JS)
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/favicon.ico").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/registro").permitAll()
                        .requestMatchers("/api/auth/me").permitAll()

                        // 2. RUTAS PROTEGIDAS POR ROL
                        .requestMatchers("/api/solicitante/**").hasRole("SOLICITANTE")
                        .requestMatchers("/api/coordinador/**").hasRole("COORDINADOR")
                        .requestMatchers("/api/agente/**").hasRole("AGENTE")
                        .requestMatchers("/api/auditor/**").hasRole("AUDITOR")

                        // Cualquier otra petición requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Mantiene HTTP Basic para peticiones de la API

        return http.build();
    }
}
