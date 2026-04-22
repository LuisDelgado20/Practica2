package com.upiiz.plantillas.repositories;

import com.upiiz.plantillas.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.id = :cuentaId")
    List<Movimiento> findByCuentaId(@Param("cuentaId") Long cuentaId);
}