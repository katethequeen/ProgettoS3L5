package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String cognome;

    @Column(name = "data_nascita")
    private String dataDiNacita;
    @Column(name = "numero_tessera")
    private String numeroDiTessera;
}