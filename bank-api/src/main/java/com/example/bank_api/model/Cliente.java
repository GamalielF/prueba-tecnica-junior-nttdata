package com.example.bank_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Persona {
    @Column(unique = true, nullable = false)
    private String clienteId;

    private String contrasena;

    private Boolean estado;
}
