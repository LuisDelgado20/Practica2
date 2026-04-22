package com.upiiz.plantillas.Controllers;

import com.upiiz.plantillas.Services.MovimientoService;
import com.upiiz.plantillas.entities.Cuenta;
import com.upiiz.plantillas.entities.Movimiento;
import com.upiiz.plantillas.entities.Transaccion;
import com.upiiz.plantillas.Services.CuentaService;
import com.upiiz.plantillas.Services.TransaccionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transacciones") // Todas las rutas aquí empiezan con /transacciones
public class TransaccionController {

    @Autowired
    private MovimientoService movimientoService; // Asegúrate de usar el servicio que guarda 'Movimiento'

    @Autowired
    private CuentaService cuentaService;

    // Ruta final: /transacciones/nuevo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model, HttpSession session) {
        String nombreLogueado = (String) session.getAttribute("usuarioLogueado");

        // Inicialización crucial para evitar el error 500
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(new Cuenta());
        movimiento.setFecha(java.time.LocalDate.now());

        model.addAttribute("movimiento", movimiento);
        model.addAttribute("cuentas", cuentaService.obtenerTodas());
        model.addAttribute("usuarioLogueado", nombreLogueado != null ? nombreLogueado : "Usuario");

        return "transacciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("movimiento") Movimiento movimiento) {
        // Guardamos el movimiento usando el servicio correspondiente
        movimientoService.guardar(movimiento);
        return "redirect:/estadisticas"; // Redirigimos a estadísticas para ver el cambio en la gráfica
    }
}