package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class User {

    @JsonProperty("issuer")
    private String issuer;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("audience")
    private String audience;

    @JsonProperty("customClaim")
    private String customClaim;

    @JsonProperty("ttlMillis")
    private Long ttlMillis;

}

