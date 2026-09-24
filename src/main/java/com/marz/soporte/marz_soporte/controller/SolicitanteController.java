package com.marz.soporte.marz_soporte.controller;

import com.marz.soporte.marz_soporte.entity.Solicitud;
import com.marz.soporte.marz_soporte.entity.Usuario;
import com.marz.soporte.marz_soporte.repository.SolicitudRepository;
import com.marz.soporte.marz_soporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitante")
public class SolicitanteController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // HU02: Crear una nueva solicitud de soporte
    @PostMapping("/solicitudes")
    public ResponseEntity<?> crearSolicitud(@RequestBody Map<String, String> datos, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Usuario solicitante = usuarioRepository.findByEmail(emailUsuario).orElse(null);

        if (solicitante == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", "Usuario no autenticado."));
        }

        String titulo = datos.get("titulo");
        String descripcion = datos.get("descripcion");
        String categoria = datos.get("categoria");

        if (titulo == null || descripcion == null || categoria == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Título, descripción y categoría son requeridos."));
        }

        Solicitud nuevaSolicitud = new Solicitud(titulo, descripcion, categoria, solicitante);
        solicitudRepository.save(nuevaSolicitud);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Solicitud creada exitosamente.",
                "id", nuevaSolicitud.getId()
        ));
    }

    // HU03: Consultar las solicitudes propias del usuario
    @GetMapping("/solicitudes")
    public ResponseEntity<?> obtenerMisSolicitudes(Authentication authentication) {
        String emailUsuario = authentication.getName();
        Usuario solicitante = usuarioRepository.findByEmail(emailUsuario).orElse(null);

        if (solicitante == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", "Usuario no autenticado."));
        }

        List<Solicitud> misSolicitudes = solicitudRepository.findBySolicitante(solicitante);
        return ResponseEntity.ok(misSolicitudes);
    }
}