package com.example.bank_api.controller;

import com.example.bank_api.model.Cuenta;
import com.example.bank_api.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.createCuenta(cuenta));
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        return ResponseEntity.ok(cuentaService.getAllCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.getCuentaById(id);
        return cuenta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta updated = cuentaService.updateCuenta(id, cuenta);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        return cuentaService.deleteCuenta(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}