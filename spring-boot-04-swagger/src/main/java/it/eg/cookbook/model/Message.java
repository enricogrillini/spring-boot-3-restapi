package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @NotNull
    @Schema(name = "code", example = "OK", description = "Codice risposta", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("code")
    private String code;

    @NotNull
    @Schema(name = "description", example = "Operazione eseguita correttamente", description = "Descrizione", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("description")
    private String description;

    @Schema(name = "detail", example = "Operazione eseguita correttamente senza avvertimenti", description = "Descrizione dettagliata", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("detail")
    private String detail;

}

