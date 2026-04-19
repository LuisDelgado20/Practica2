package com.upiiz.plantillas.repositories;

import com.upiiz.plantillas.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> { }