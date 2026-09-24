package com.marz.soporte.marz_soporte.service;

import com.marz.soporte.marz_soporte.dto.SolicitudDTO;
import com.marz.soporte.marz_soporte.entity.Solicitud;
import com.marz.soporte.marz_soporte.entity.Usuario;
import com.marz.soporte.marz_soporte.repository.SolicitudRepository;
import com.marz.soporte.marz_soporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SolicitudService {
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    public Solicitud crearSolicitud(SolicitudDTO dto, String emailUsuario) {

        if (dto.getTitulo() == null || dto.getDescripcion() == null || dto.getCategoria() == null) {
            throw new IllegalArgumentException("Campos obligatorios incompletos");
        }

        Usuario solicitante = usuarioRepository.findByEmail(emailUsuario).orElseThrow();

        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo(dto.getTitulo());
        solicitud.setDescripcion(dto.getDescripcion());
        solicitud.setCategoria(dto.getCategoria());
        solicitud.setFechaCreacion(LocalDateTime.now());
        solicitud.setEstado("Nuevo");
        solicitud.setSolicitante(solicitante);

        return solicitudRepository.save(solicitud);
    }
}
