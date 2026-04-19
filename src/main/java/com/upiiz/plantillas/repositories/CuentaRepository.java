package com.upiiz.plantillas.repositories;

import com.upiiz.plantillas.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    // Ahora sí funcionará porque existe 'email' y 'password' en la clase Cuenta
    Optional<Cuenta> findByEmailAndPassword(String email, String password);
    Optional<Cuenta> findByEmail(String email);
    Cuenta findByTitular(String titular);
}