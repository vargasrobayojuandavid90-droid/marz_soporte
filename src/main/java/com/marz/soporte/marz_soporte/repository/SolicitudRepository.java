package com.marz.soporte.marz_soporte.repository;

import com.marz.soporte.marz_soporte.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository extends JpaRepository<SolicitudRepository,Long > {
    List<Solicitud> findBySolicitanteEmail(String email);
    Optional<Solicitud> findByIdAndSolicitanteEmail(Long id, String email);
}
