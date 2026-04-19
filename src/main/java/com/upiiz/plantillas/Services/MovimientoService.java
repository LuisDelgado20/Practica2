package com.upiiz.plantillas.Services;

import com.upiiz.plantillas.entities.Movimiento;
import com.upiiz.plantillas.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovimientoService{ // En MovimientoService.java
    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<Movimiento> buscarPorCuentaId(Long id) {
        // 2. Usamos la variable en minúsculas (la instancia), NO la clase con Mayúscula
        return movimientoRepository.findByCuentaId(id);
    }
}
