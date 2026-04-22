package com.upiiz.plantillas.Services;

import com.upiiz.plantillas.entities.Cuenta;
import com.upiiz.plantillas.repositories.CuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }

    public Cuenta guardar(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminar(Long id) {
        cuentaRepository.deleteById(id);
    }

    public boolean validarCredenciales(String email, String password) {
        return cuentaRepository.findByEmailAndPassword(email, password).isPresent();
    }

    public Cuenta buscarPorEmail(String email) {
        return cuentaRepository.findByEmail(email).orElse(null);
    }

    public Cuenta buscarPorId(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void transferir(Long idOrigen, Long idDestino, Double monto) {
        Cuenta origen = buscarPorId(idOrigen);
        Cuenta destino = buscarPorId(idDestino);

        if (origen != null && destino != null && origen.getSaldo().doubleValue() >= monto) {
            origen.setSaldo(origen.getSaldo().subtract(java.math.BigDecimal.valueOf(monto)));
            destino.setSaldo(destino.getSaldo().add(java.math.BigDecimal.valueOf(monto)));
            cuentaRepository.save(origen);
            cuentaRepository.save(destino);
        }
    }

    // CORRECCIÓN: Ahora maneja la lista que viene del repositorio
    public Cuenta buscarPorTitular(String titular) {
        List<Cuenta> cuentas = cuentaRepository.findByTitular(titular);
        // Si hay cuentas, devolvemos la primera para evitar que la vista falle
        return cuentas.isEmpty() ? null : cuentas.get(0);
    }
    // Agrega este método
    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }


    public Cuenta obtenerPorId(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }
}