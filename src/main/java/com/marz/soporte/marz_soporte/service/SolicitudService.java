package com.marz.soporte.marz_soporte.service;

import com.marz.soporte.marz_soporte.entity.Solicitud;
import com.marz.soporte.marz_soporte.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        solicitud.setUltimaActualizacion(LocalDateTime.now());
        solicitud.setEstado("Nuevo"); // Estado predeterminado
        solicitud.setSolicitante(solicitante);

        return solicitudRepository.save(solicitud);
    }
}
