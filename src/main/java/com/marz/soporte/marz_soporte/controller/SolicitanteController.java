package com.marz.soporte.marz_soporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitante")
public class SolicitanteController {
    @Autowired
    private SolicitudRepository solicitudRepository;

    @GetMapping("/mis-solicitudes")
    public List<Solicitud> obtenerMisSolicitudes(Authentication authentication) {
        String email = authentication.getName();
        return solicitudRepository.findBySolicitanteEmail(email); // Nunca retorna solicitudes ajenas
    }

    @GetMapping("/mis-solicitudes/{id}")
    public ResponseEntity<Solicitud> obtenerDetalle(@PathVariable Long id, Authentication authentication) {
        return solicitudRepository.findByIdAndSolicitanteEmail(id, authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
