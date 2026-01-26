package it.eg.cookbook.model.entity;

import it.eg.cookbook.model.Documento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "documento_autore")
public class DocumentoAutoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_documento_autore")
    @SequenceGenerator(name = "seq_id_documento_autore", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_documento")
    private DocumentoEntity documento;

    @ManyToOne
    @JoinColumn(name = "id_autore")
    private AutoreEntity autore;
}