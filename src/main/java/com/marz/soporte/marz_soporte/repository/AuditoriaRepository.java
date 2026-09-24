package com.marz.soporte.marz_soporte.repository;

import com.marz.soporte.marz_soporte.entity.HistorialAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<HistorialAuditoria, Long> {
}
