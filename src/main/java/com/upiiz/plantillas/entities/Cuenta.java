package com.upiiz.plantillas.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long id;

    private String titular;
    private String email;
    private String password;
    private String tipo_cuenta;
    private BigDecimal saldo;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaccion> transacciones;

    public Cuenta() {}

    public Cuenta(Long id, String titular, String email, String password, String tipo_cuenta, BigDecimal saldo, List<Transaccion> transacciones) {
        this.id = id;
        this.titular = titular;
        this.email = email;
        this.password = password;
        this.tipo_cuenta = tipo_cuenta;
        this.saldo = saldo;
        this.transacciones = transacciones;
    }

    // Getters y Setters... (los que ya tenías están perfectos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTipo_cuenta() { return tipo_cuenta; }
    public void setTipo_cuenta(String tipo_cuenta) { this.tipo_cuenta = tipo_cuenta; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public List<Transaccion> getTransacciones() { return transacciones; }
    public void setTransacciones(List<Transaccion> transacciones) { this.transacciones = transacciones; }
}