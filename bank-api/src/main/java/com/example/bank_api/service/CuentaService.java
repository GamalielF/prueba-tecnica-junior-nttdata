package com.example.bank_api.service;

import com.example.bank_api.model.Cuenta;
import com.example.bank_api.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> findById(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }
}