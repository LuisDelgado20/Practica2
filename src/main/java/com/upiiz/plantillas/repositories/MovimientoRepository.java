package com.upiiz.plantillas.repositories;

import com.upiiz.plantillas.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    // Busca todos los movimientos asociados a una cuenta específica
    List<Movimiento> findByCuentaId(Long cuentaId);
}