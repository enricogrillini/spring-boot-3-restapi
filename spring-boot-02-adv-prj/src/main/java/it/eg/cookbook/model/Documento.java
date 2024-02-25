package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("descrizione")
    private String descrizione;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("data")
    private LocalDate data;
}

