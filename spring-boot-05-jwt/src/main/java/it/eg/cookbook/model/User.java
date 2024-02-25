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
public class User {

    @NotNull
    @Schema(name = "issuer", example = "www.idm.com\"", description = "issuer", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("issuer")
    private String issuer;

    @NotNull
    @Schema(name = "subject", example = "reader-1\"", description = "subject", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("subject")
    private String subject;

    @NotNull
    @Schema(name = "audience", example = "progetto-cookbook", description = "audience", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("audience")
    private String audience;

    @NotNull
    @Schema(name = "customClaim", example = "customClaim", description = "customClaim", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("customClaim")
    private String customClaim;

    @NotNull
    @Schema(name = "ttlMillis", example = "3600000", description = "ttlMillis", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("ttlMillis")
    private Long ttlMillis;

}

