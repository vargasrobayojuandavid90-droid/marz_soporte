package com.marz.soporte.marz_soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<SolicitudRepository,Long > {
    List<Solicitud> findBySolicitanteEmail(String email);
    Optional<Solicitud> findByIdAndSolicitanteEmail(Long id, String email);
}
