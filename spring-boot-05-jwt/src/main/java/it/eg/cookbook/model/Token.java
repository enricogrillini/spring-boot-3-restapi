package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

  @Schema(name = "jwtToken", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("jwtToken")
  private String jwtToken;


}

