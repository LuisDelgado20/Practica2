package com.upiiz.plantillas.Controllers;

import com.upiiz.plantillas.Services.CuentaService;
import com.upiiz.plantillas.Services.MovimientoService;
import com.upiiz.plantillas.entities.Cuenta;
import com.upiiz.plantillas.entities.Movimiento;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EstadisticasController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoService movimientoService;
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model, HttpSession session) {
        String nombreLogueado = (String) session.getAttribute("usuarioLogueado");

        if (nombreLogueado == null) {
            return "redirect:/auth/login";
        }

        Cuenta cuentaUsuario = cuentaService.buscarPorTitular(nombreLogueado);

        if (cuentaUsuario != null) {
            List<Movimiento> misMovimientos = movimientoService.buscarPorCuentaId(cuentaUsuario.getId());

            double entradas = misMovimientos.stream()
                    .filter(m -> m.getTipo().equalsIgnoreCase("Entrada") || m.getTipo().equalsIgnoreCase("Depósito"))
                    .mapToDouble(m -> m.getMonto().doubleValue()).sum();

            double salidas = misMovimientos.stream()
                    .filter(m -> m.getTipo().equalsIgnoreCase("Salida") || m.getTipo().equalsIgnoreCase("Retiro"))
                    .mapToDouble(m -> m.getMonto().doubleValue()).sum();

            model.addAttribute("movimientos", misMovimientos);
            model.addAttribute("totalEntradas", entradas);
            model.addAttribute("totalSalidas", salidas);
            model.addAttribute("saldoActual", cuentaUsuario.getSaldo());
        } else {
            model.addAttribute("totalEntradas", 0);
            model.addAttribute("totalSalidas", 0);
            model.addAttribute("movimientos", List.of());
        }

        model.addAttribute("usuarioLogueado", nombreLogueado);
        // Asegúrate de que esta ruta coincida con la ubicación de tu archivo .html
        return "Transacciones/estadisticas";
    }
}