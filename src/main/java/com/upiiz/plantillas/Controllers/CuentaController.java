package com.upiiz.plantillas.Controllers;

import com.upiiz.plantillas.entities.Cuenta;
import com.upiiz.plantillas.Services.CuentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        // Obtenemos el email o nombre guardado en la sesión
        String usuarioLogueado = (String) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/auth/login";
        }

        // El Service ahora maneja internamente la lógica de la lista
        // y nos devuelve una sola Cuenta (o null)
        Cuenta cuentaUsuario = cuentaService.buscarPorTitular(usuarioLogueado);

        if (cuentaUsuario != null) {
            model.addAttribute("cuentas", List.of(cuentaUsuario));
        } else {
            model.addAttribute("cuentas", List.of());
        }

        model.addAttribute("usuarioLogueado", usuarioLogueado);
        return "cuentas/listado";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        // Asegúrate de que el archivo sea 'formulario.html' dentro de 'templates/cuentas/'
        return "cuentas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cuenta") Cuenta cuenta) {
        // 1. Usamos getId() porque así se llama en tu entidad Cuenta
        if (cuenta.getId() != null && (cuenta.getPassword() == null || cuenta.getPassword().isEmpty())) {
            // Recuperamos la cuenta existente para no perder la contraseña anterior
            Cuenta cuentaExistente = cuentaService.buscarPorId(cuenta.getId());
            if (cuentaExistente != null) {
                cuenta.setPassword(cuentaExistente.getPassword());
            }
        }

        // 2. Validación de seguridad para evitar que MySQL lance el error de 'password cannot be null'
        if (cuenta.getPassword() == null || cuenta.getPassword().isEmpty()) {
            cuenta.setPassword("12345"); // Contraseña temporal
        }

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
        if (cuenta != null) {
            model.addAttribute("cuenta", cuenta);
            return "cuentas/formulario";
        }
        return "redirect:/cuentas";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}