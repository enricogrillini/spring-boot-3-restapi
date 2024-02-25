package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Schema(name = "nome", example = "Titolo", description = "Nome", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("nome")
    private String nome;

    @NotNull
    @Schema(name = "descrizione", example = "Descrizione", description = "Descriptione", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("descrizione")
    private String descrizione;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Valid
    @Schema(name = "data", example = "Fri Apr 22 02:00:00 CEST 2022", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("data")
    private LocalDate data;
}

