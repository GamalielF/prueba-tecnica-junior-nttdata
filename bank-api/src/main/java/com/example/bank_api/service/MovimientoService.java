package com.example.bank_api.service;

import com.example.bank_api.exception.SaldoNoDisponibleException;
import com.example.bank_api.model.Cuenta;
import com.example.bank_api.model.Movimiento;
import com.example.bank_api.repository.CuentaRepository;
import com.example.bank_api.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    public Movimiento createMovimiento(Movimiento movimiento) {
        // Obtener el ID de la cuenta desde el JSON
        if (movimiento.getCuenta() == null || movimiento.getCuenta().getId() == null) {
            throw new IllegalArgumentException("La cuenta es requerida");
        }
        Long cuentaId = movimiento.getCuenta().getId();

        // Buscar la cuenta en la base de datos
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);
        if (!cuentaOptional.isPresent()) {
            throw new IllegalArgumentException("Cuenta con ID " + cuentaId + " no encontrada");
        }
        Cuenta cuenta = cuentaOptional.get();

        // Validar saldo (F3)
        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
        if (nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        // Actualizar saldo de la cuenta (F2)
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // Registrar movimiento (F2)
        movimiento.setCuenta(cuenta); // Asegurar que el movimiento esté asociado a la cuenta cargada
        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);
        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> getMovimientoById(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento updateMovimiento(Long id, Movimiento movimiento) {
        if (movimientoRepository.existsById(id)) {
            movimiento.setId(id);
            return movimientoRepository.save(movimiento);
        }
        return null;
    }

    public boolean deleteMovimiento(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
