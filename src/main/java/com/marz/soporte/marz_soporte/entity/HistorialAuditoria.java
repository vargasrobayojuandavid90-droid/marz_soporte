package com.marz.soporte.marz_soporte.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "historial_auditoria")
public class HistorialAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long solicitudId;
    private String usuarioEmail;
    private String accion;
    private String detalle;
    private LocalDateTime fecha;
}
