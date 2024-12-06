package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity

@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Catalogo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String titolo;

    @Column(name = "anno_pubblicazione")
    private Integer annoPubblicazione;

    @Column(name = "numero_pagine")
    private Integer numeroPagine;
}