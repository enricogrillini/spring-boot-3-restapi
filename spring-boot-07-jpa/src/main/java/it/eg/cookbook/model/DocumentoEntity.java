package it.eg.cookbook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "documento")
public class DocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_documento")
    @SequenceGenerator(name = "seq_id_documento", allocationSize = 1)
    private Long id;

    private String nome;
    private String descrizione;
    private LocalDate data;
    private String updateBy;
}