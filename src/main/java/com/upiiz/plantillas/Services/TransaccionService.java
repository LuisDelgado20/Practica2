package com.upiiz.plantillas.Services;

import com.upiiz.plantillas.entities.Transaccion;
import com.upiiz.plantillas.repositories.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    // Listar todas las transacciones para la tabla secundaria
    public List<Transaccion> listarTodas() {
        return transaccionRepository.findAll();
    }

    // Obtener una transacción por ID (para editar o eliminar)
    public Transaccion obtenerPorId(Long id) {
        return transaccionRepository.findById(id).orElse(null);
    }

    // Guardar o Actualizar
    public Transaccion guardar(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    // Eliminar registro
    public void eliminar(Long id) {
        transaccionRepository.deleteById(id);
    }
}