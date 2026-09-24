package com.marz.soporte.marz_soporte.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Data

public class Solicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaActualizacion;
    private String estado; // "Nuevo", "En Proceso", etc.
    private String prioridad;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;
}
