package com.marz.soporte.marz_soporte.controller;

import com.marz.soporte.marz_soporte.entity.Role; // CORRECCIÓN: Importar tu Enum propio
import com.marz.soporte.marz_soporte.entity.Usuario;
import com.marz.soporte.marz_soporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Verificar quién inició sesión y obtener su rol (HU01)
    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioAutenticado(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión activa.");
        }

        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("email", usuario.getEmail());
        respuesta.put("rol", usuario.getRol());

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");
        String password = datos.get("password");
        String rolString = datos.get("rol");

        if (email == null || password == null || rolString == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Todos los campos son obligatorios."));
        }

        if (usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "El correo ya se encuentra registrado."));
        }

        Role rol;
        try {
            rol = Role.valueOf(rolString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Rol no válido."));
        }

        Usuario nuevoUsuario = new Usuario(email, passwordEncoder.encode(password), rol);
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Usuario registrado con éxito."));
    }
}