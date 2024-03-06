package com.generation.identificai.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.identificai.model.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByNome(String nome);
    Optional<Usuarios> findByEmail(String email); 
}

