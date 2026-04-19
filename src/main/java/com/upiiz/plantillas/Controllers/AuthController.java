package com.upiiz.plantillas.Controllers;

import com.upiiz.plantillas.Services.CuentaService;
import com.upiiz.plantillas.entities.Cuenta;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private JavaMailSender mailSender;
    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (cuentaService.validarCredenciales(email, password)) {
            Cuenta usuario = cuentaService.buscarPorEmail(email);

            session.setAttribute("usuarioLogueado", usuario.getTitular());
            return "redirect:/cuentas";
        } else {

            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "auth/login";
        }
    }


    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute Cuenta cuenta, Model model) {
        Cuenta cuentaExistente = cuentaService.buscarPorEmail(cuenta.getEmail());
        if (cuentaExistente != null) {
            model.addAttribute("error", "Este correo ya está registrado.");
            return "auth/register";
        }
        if (cuenta.getTipo_cuenta() == null) cuenta.setTipo_cuenta("Ahorro");
        if (cuenta.getSaldo() == null) cuenta.setSaldo(java.math.BigDecimal.ZERO);

        cuentaService.guardar(cuenta);
        return "redirect:/auth/login?success";
    }


    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }

    @PostMapping("/recuperar")
    public String recuperarContrasena(@RequestParam String email) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("chavocholuisdaniel7@gmail.com");
            mensaje.setTo(email);
            mensaje.setSubject("Recuperación de Contraseña - SAES");
            mensaje.setText("Restablece tu contraseña aquí: http://localhost:8081/auth/recover-password?email=" + email);
            mailSender.send(mensaje);
            return "redirect:/auth/forgot-password?success";
        } catch (Exception e) {
            return "redirect:/auth/forgot-password?error";
        }
    }

    @GetMapping("/recover-password")
    public String recoverPassword() {
        return "auth/recover-password";
    }

    @PostMapping("/actualizar-password")
    public String actualizarPassword(@RequestParam String email, @RequestParam String password) {
        Cuenta cuenta = cuentaService.buscarPorEmail(email);
        if (cuenta != null) {
            cuenta.setPassword(password);
            cuentaService.guardar(cuenta);
            return "redirect:/auth/login?resetSuccess";
        }
        return "redirect:/auth/recover-password?error";
    }
}