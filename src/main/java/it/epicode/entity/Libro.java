package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity

public class Libro extends Catalogo {
    private String autore;
    private String genere;
}