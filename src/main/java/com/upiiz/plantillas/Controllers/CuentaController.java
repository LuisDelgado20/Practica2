package com.upiiz.plantillas.Controllers;

import com.upiiz.plantillas.entities.Cuenta;
import com.upiiz.plantillas.Services.CuentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        String nombreLogueado = (String) session.getAttribute("usuarioLogueado");


        if (nombreLogueado == null) {
            return "redirect:/auth/login";
        }


        Cuenta cuentaUsuario = cuentaService.buscarPorTitular(nombreLogueado);


        if (cuentaUsuario != null) {
            model.addAttribute("cuentas", List.of(cuentaUsuario));
        } else {

            model.addAttribute("cuentas", List.of());
        }

        model.addAttribute("usuarioLogueado", nombreLogueado);

        return "cuentas/listado";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        return "cuentas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cuenta cuenta) {
        cuentaService.guardar(cuenta);
        return "redirect:/cuentas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return "redirect:/cuentas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cuenta cuenta = cuentaService.buscarPorId(id);
        model.addAttribute("cuenta", cuenta);
        return "cuentas/formulario";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

}
