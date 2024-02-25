package it.eg.cookbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * User
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-25T18:31:12.613479900+01:00[Europe/Rome]")
public class User {

  private String issuer;

  private String subject;

  private String audience;

  private String customClaim;

  private Long ttlMillis;

  public User() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public User(String issuer, String subject, String audience, String customClaim, Long ttlMillis) {
    this.issuer = issuer;
    this.subject = subject;
    this.audience = audience;
    this.customClaim = customClaim;
    this.ttlMillis = ttlMillis;
  }

  public User issuer(String issuer) {
    this.issuer = issuer;
    return this;
  }

  /**
   * issuer
   * @return issuer
  */
  @NotNull 
  @Schema(name = "issuer", example = "www.idm.com\"", description = "issuer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("issuer")
  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public User subject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * subject
   * @return subject
  */
  @NotNull 
  @Schema(name = "subject", example = "reader-1\"", description = "subject", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("subject")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public User audience(String audience) {
    this.audience = audience;
    return this;
  }

  /**
   * audience
   * @return audience
  */
  @NotNull 
  @Schema(name = "audience", example = "progetto-cookbook", description = "audience", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("audience")
  public String getAudience() {
    return audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }

  public User customClaim(String customClaim) {
    this.customClaim = customClaim;
    return this;
  }

  /**
   * customClaim
   * @return customClaim
  */
  @NotNull 
  @Schema(name = "customClaim", example = "customClaim", description = "customClaim", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("customClaim")
  public String getCustomClaim() {
    return customClaim;
  }

  public void setCustomClaim(String customClaim) {
    this.customClaim = customClaim;
  }

  public User ttlMillis(Long ttlMillis) {
    this.ttlMillis = ttlMillis;
    return this;
  }

  /**
   * ttlMillis
   * @return ttlMillis
  */
  @NotNull 
  @Schema(name = "ttlMillis", example = "3600000", description = "ttlMillis", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ttlMillis")
  public Long getTtlMillis() {
    return ttlMillis;
  }

  public void setTtlMillis(Long ttlMillis) {
    this.ttlMillis = ttlMillis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.issuer, user.issuer) &&
        Objects.equals(this.subject, user.subject) &&
        Objects.equals(this.audience, user.audience) &&
        Objects.equals(this.customClaim, user.customClaim) &&
        Objects.equals(this.ttlMillis, user.ttlMillis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issuer, subject, audience, customClaim, ttlMillis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    issuer: ").append(toIndentedString(issuer)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    audience: ").append(toIndentedString(audience)).append("\n");
    sb.append("    customClaim: ").append(toIndentedString(customClaim)).append("\n");
    sb.append("    ttlMillis: ").append(toIndentedString(ttlMillis)).append("\n");
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

