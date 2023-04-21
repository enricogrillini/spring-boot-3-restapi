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
import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(name = "Document", description = "Rest API - Document CRUD")
public interface DocumentApi {

    /**
     * DELETE /api/v1/document/{documentId} : Elimina un documento
     *
     * @param documentId (required)
     * @return Ok (status code 200)
     * or Impossibile cancellare il documento (status code 400)
     */
    @Operation(
            operationId = "deleteDocument",
            summary = "Elimina un documento",
            tags = {"Document"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Impossibile cancellare il documento", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    })
            }
    )
    @DeleteMapping(value = "/api/v1/document/{documentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseMessage> deleteDocument(
            @Parameter(name = "documentId", description = "", required = true) @PathVariable("documentId") Integer documentId
    );


    /**
     * GET /api/v1/document/{documentId} : Ritorna un documento
     *
     * @param documentId (required)
     * @return Errore nel reperimento del documento (status code 400)
     * or Documento trovato (status code 200)
     */
    @Operation(
            operationId = "getDocument",
            summary = "Ritorna un documento",
            tags = {"Document"},
            responses = {
                    @ApiResponse(responseCode = "400", description = "Errore nel reperimento del documento", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    }),
                    @ApiResponse(responseCode = "200", description = "Documento trovato", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Document.class))
                    })
            }
    )
    @GetMapping(value = "/api/v1/document/{documentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Document> getDocument(
            @Parameter(name = "documentId", description = "", required = true) @PathVariable("documentId") Integer documentId
    );


    /**
     * GET /api/v1/document : Ritorna la lista di documenti
     *
     * @return Lista di documenti (status code 200)
     */
    @Operation(
            operationId = "getDocuments",
            summary = "Ritorna la lista di documenti",
            tags = {"Document"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista di documenti", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Document.class))
                    })
            }
    )
    @GetMapping(value = "/api/v1/document", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Document>> getDocuments();


    /**
     * POST /api/v1/document : Aggiunge un documento
     *
     * @param document (required)
     * @return Ok (status code 200)
     * or Impossibile inserire il documento (status code 400)
     */
    @Operation(
            operationId = "postDocument",
            summary = "Aggiunge un documento",
            tags = {"Document"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Impossibile inserire il documento", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    })
            }
    )
    @PostMapping(value = "/api/v1/document", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseMessage> postDocument(
            @Parameter(name = "Document", description = "", required = true) @Valid @RequestBody Document document
    );

    /**
     * PUT /api/v1/document : Aggiorna un documento
     *
     * @param document (required)
     * @return Ok (status code 200)
     * or Impossibile aggiornare il documento (status code 400)
     */
    @Operation(
            operationId = "putDocument",
            summary = "Aggiorna un documento",
            tags = {"Document"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Impossibile aggiornare il documento", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseMessage.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/v1/document",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<ResponseMessage> putDocument(
            @Parameter(name = "Document", description = "", required = true) @Valid @RequestBody Document document
    );

}
