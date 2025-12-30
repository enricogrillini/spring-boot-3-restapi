package it.eg.cookbook.model.entity;

import it.eg.cookbook.model.JobStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_job")
    @SequenceGenerator(name = "seq_id_job", allocationSize = 1)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private JobStatus status;
    private String message;

    private String taskName;
    private String taskDetail;


}