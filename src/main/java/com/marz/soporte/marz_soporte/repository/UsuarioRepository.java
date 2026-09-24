package com.marz.soporte.marz_soporte.repository;

import com.marz.soporte.marz_soporte.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioRepository, Long> {
    Optional<Usuario> findByEmail(String email);
}
