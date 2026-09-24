package com.marz.soporte.marz_soporte.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role rol;

}
