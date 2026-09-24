package com.marz.soporte.marz_soporte.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role rol; // Ahora tomará automáticamente el enum Role de este mismo paquete

    // Constructor personalizado sin ID para facilitar la creación de nuevos usuarios
    public Usuario(String email, String password, Role rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
}