package com.example.bank_api.controller;

import com.example.bank_api.model.Cuenta;
import com.example.bank_api.model.Cliente;
import com.example.bank_api.repository.CuentaRepository;
import com.example.bank_api.repository.ClienteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    @PostMapping
    public Cuenta create(@RequestBody CuentaRequest cuentaRequest) {
        // Validamos que los campos no sean nulos
        if (cuentaRequest.getIdentificacion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La identificación es obligatoria");
        }
        if (cuentaRequest.getNumeroCuenta() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de cuenta es obligatorio");
        }

        // Buscamos el cliente
        Cliente cliente = clienteRepository.findById(cuentaRequest.getIdentificacion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        // Creamos la cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial() != null ? cuentaRequest.getSaldoInicial() : 0.0);
        cuenta.setEstado(cuentaRequest.getEstado() != null ? cuentaRequest.getEstado() : true);
        cuenta.setCliente(cliente);

        try {
            return cuentaRepository.save(cuenta);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El número de cuenta ya existe", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la cuenta: " + e.getMessage(), e);
        }
    }

    @Data
    private static class CuentaRequest {
        private String numeroCuenta;
        private String tipoCuenta;
        private Double saldoInicial;
        private Boolean estado;
        private String identificacion;
    }
}