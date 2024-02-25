package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Documento
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-25T18:31:12.613479900+01:00[Europe/Rome]")
public class Documento {

  private Long id;

  private String nome;

  private String descrizione;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate data;

  public Documento() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Documento(String nome, String descrizione) {
    this.nome = nome;
    this.descrizione = descrizione;
  }

  public Documento id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Documento nome(String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * Nome
   * @return nome
  */
  @NotNull 
  @Schema(name = "nome", example = "Titolo", description = "Nome", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Documento descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

  /**
   * Descriptione
   * @return descrizione
  */
  @NotNull 
  @Schema(name = "descrizione", example = "Descrizione", description = "Descriptione", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Documento data(LocalDate data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
  */
  @Valid 
  @Schema(name = "data", example = "Fri Apr 22 02:00:00 CEST 2022", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Documento documento = (Documento) o;
    return Objects.equals(this.id, documento.id) &&
        Objects.equals(this.nome, documento.nome) &&
        Objects.equals(this.descrizione, documento.descrizione) &&
        Objects.equals(this.data, documento.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome, descrizione, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Documento {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

