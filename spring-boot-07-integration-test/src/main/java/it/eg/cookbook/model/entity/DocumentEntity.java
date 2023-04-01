package it.eg.cookbook.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_iddocument")
    @SequenceGenerator(name = "seq_iddocument", allocationSize = 1)
    private Integer id;

    private String name;
    private String description;
    private LocalDate data;
    private String updateBy;
}
