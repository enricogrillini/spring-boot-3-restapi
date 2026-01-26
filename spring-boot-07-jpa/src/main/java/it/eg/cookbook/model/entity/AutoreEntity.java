package it.eg.cookbook.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "autore")
public class AutoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_autore")
    @SequenceGenerator(name = "seq_id_autore", allocationSize = 1)
    private Long id;

    private String nome;
    private String cognome;
}