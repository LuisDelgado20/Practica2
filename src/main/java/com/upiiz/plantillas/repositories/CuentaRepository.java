package com.upiiz.plantillas.repositories;

import com.upiiz.plantillas.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByEmailAndPassword(String email, String password);
    Optional<Cuenta> findByEmail(String email);

    // Cambiado a List para evitar NonUniqueResultException
    List<Cuenta> findByTitular(String titular);
}