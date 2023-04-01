/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package it.eg.cookbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.eg.cookbook.model.Token;
import it.eg.cookbook.model.User;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@Tag(name = "Security", description = "Rest API - Security")
public interface SecurityApi {

    /**
     * POST /api/v1/security/generate-token : Genera un JWT
     *
     * @param user (required)
     * @return Ok (status code 200)
     */
    @Operation(
            operationId = "postGenerateToken",
            summary = "Genera un JWT",
            tags = {"Security"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Token.class))
                    })
            }
    )
    @PostMapping(value = "/api/v1/security/generate-token", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Token> postGenerateToken(
            @Parameter(name = "User", description = "", required = true) @Valid @RequestBody User user
    );

}