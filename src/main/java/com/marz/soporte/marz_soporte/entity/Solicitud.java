package com.marz.soporte.marz_soporte.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    private String prioridad = "Baja"; // Valor inicial por defecto

    private String estado = "Pendiente"; // Valor inicial por defecto

    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario solicitante;

    public Solicitud(String titulo, String descripcion, String categoria, Usuario solicitante) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.solicitante = solicitante;
        this.fechaCreacion = LocalDateTime.now();
    }}
