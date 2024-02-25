package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Message
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-25T18:31:12.613479900+01:00[Europe/Rome]")
public class Message {

  private String code;

  private String description;

  private String detail;

  public Message() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Message(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public Message code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Codice risposta
   * @return code
  */
  @NotNull 
  @Schema(name = "code", example = "OK", description = "Codice risposta", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Message description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Descrizione
   * @return description
  */
  @NotNull 
  @Schema(name = "description", example = "Operazione eseguita correttamente", description = "Descrizione", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Message detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Descrizione dettagliata
   * @return detail
  */
  
  @Schema(name = "detail", example = "Operazione eseguita correttamente senza avvertimenti", description = "Descrizione dettagliata", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("detail")
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return Objects.equals(this.code, message.code) &&
        Objects.equals(this.description, message.description) &&
        Objects.equals(this.detail, message.detail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description, detail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Message {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

