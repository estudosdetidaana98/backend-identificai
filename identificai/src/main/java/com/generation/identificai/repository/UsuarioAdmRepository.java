package com.generation.identificai.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.identificai.model.UsuarioAdm;

public interface UsuarioAdmRepository extends JpaRepository<UsuarioAdm, Long> {
    Optional<UsuarioAdm> findByEmail(String email); 
}


