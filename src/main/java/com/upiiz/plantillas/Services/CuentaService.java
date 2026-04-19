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

    public boolean validarCredenciales(String titular, String password) {
        return cuentaRepository.findByEmailAndPassword(titular, password).isPresent();
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

    public Cuenta buscarPorTitular(String titular) {
        // Asumiendo que usas JPA, el repositorio debería tener este método
        return cuentaRepository.findByTitular(titular);
    }

}