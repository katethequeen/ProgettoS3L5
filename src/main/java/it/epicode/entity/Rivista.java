package it.epicode.entity;

import it.epicode.entity.entity_enum.Periodicita;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity

public class Rivista extends Catalogo {

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

}