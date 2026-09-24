package com.marz.soporte.marz_soporte.controller;

import com.marz.soporte.marz_soporte.dto.SolicitudDTO;
import com.marz.soporte.marz_soporte.entity.Solicitud;
import com.marz.soporte.marz_soporte.entity.Usuario;
import com.marz.soporte.marz_soporte.repository.SolicitudRepository;
import com.marz.soporte.marz_soporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitante")
public class SolicitanteController {
    @Autowired private SolicitudRepository solicitudRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @PostMapping("/solicitudes")
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudDTO dto, Authentication authentication) {
        if (dto.getTitulo() == null || dto.getDescripcion() == null || dto.getCategoria() == null) {
            return ResponseEntity.badRequest().body("Título, descripción y categoría son obligatorios.");
        }

        Usuario solicitante = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();

        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo(dto.getTitulo());
        solicitud.setDescripcion(dto.getDescripcion());
        solicitud.setCategoria(dto.getCategoria());
        solicitud.setFechaCreacion(LocalDateTime.now());
        solicitud.setUltimaActualizacion(LocalDateTime.now());
        solicitud.setEstado("Nuevo");
        solicitud.setPrioridad("Media");
        solicitud.setSolicitante(solicitante);

        Solicitud guardada = solicitudRepository.save(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @GetMapping("/solicitudes")
    public List<Solicitud> listarMisSolicitudes(Authentication authentication) {
        return solicitudRepository.findBySolicitanteEmail(authentication.getName());
    }

    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<Solicitud> obtenerDetalle(@PathVariable Long id, Authentication authentication) {
        return solicitudRepository.findByIdAndSolicitanteEmail(id, authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
