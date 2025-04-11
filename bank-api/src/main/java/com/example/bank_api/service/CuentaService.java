package com.example.bank_api.service;

import com.example.bank_api.model.Cliente;
import com.example.bank_api.model.Cuenta;
import com.example.bank_api.repository.ClienteRepository;
import com.example.bank_api.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Cuenta createCuenta(Cuenta cuenta) {
        // Validar que el cliente esté presente y tenga un ID
        if (cuenta.getCliente() == null || cuenta.getCliente().getId() == null) {
            throw new IllegalArgumentException("El cliente es requerido");
        }
        Long clienteId = cuenta.getCliente().getId();

        // Cargar el cliente desde la base de datos
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if (!clienteOptional.isPresent()) {
            throw new IllegalArgumentException("Cliente con ID " + clienteId + " no encontrado");
        }
        Cliente cliente = clienteOptional.get();

        // Asociar el cliente cargado a la cuenta
        cuenta.setCliente(cliente);

        // Guardar la cuenta
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> getCuentaById(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta updateCuenta(Long id, Cuenta cuenta) {
        if (cuentaRepository.existsById(id)) {
            cuenta.setId(id);
            return cuentaRepository.save(cuenta);
        }
        return null;
    }

    public boolean deleteCuenta(Long id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}