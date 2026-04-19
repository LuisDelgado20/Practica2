package com.upiiz.plantillas.Controllers;

import com.upiiz.plantillas.entities.Cuenta;
import com.upiiz.plantillas.entities.Transaccion;
import com.upiiz.plantillas.Services.CuentaService;
import com.upiiz.plantillas.Services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private CuentaService cuentaService;

    // 1. Listado de transacciones
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("transacciones", transaccionService.listarTodas());
        model.addAttribute("usuarioLogueado", "Luis Daniel");
        return "transacciones/listado";
    }

    // 2. Formulario para registro INDIVIDUAL de transacción
    // Acceso: /transacciones/nuevo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cuentas", cuentaService.listarTodas());
        model.addAttribute("transaccion", new Transaccion());
        model.addAttribute("usuarioLogueado", "Luis Daniel");
        return "transacciones/formulario";
    }

    // 3. Guardar transacción individual
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Transaccion transaccion) {
        transaccionService.guardar(transaccion);
        return "redirect:/transacciones";
    }

    // 4. Procesa la transferencia entre dos cuentas
    // Acceso: /transacciones/transferir
    @PostMapping("/transferir")
    public String realizarTransferencia(@RequestParam Long idOrigen,
                                        @RequestParam Long idDestino,
                                        @RequestParam Double monto) {
        cuentaService.transferir(idOrigen, idDestino, monto);
        return "redirect:/cuentas";
    }

    // 5. Estadísticas
    @GetMapping("/estadisticas")
    public String verEstadisticas(Model model) {
        model.addAttribute("cuentas", cuentaService.listarTodas());
        return "transacciones/estadisticas";
    }
}