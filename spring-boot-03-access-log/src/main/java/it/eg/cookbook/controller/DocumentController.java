package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.service.DocumentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocumentServices documentServices;

    @DeleteMapping(value = "/document/{documentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Message> deleteDocument(@PathVariable("documentId") Integer documentId) {
        if (documentServices.getDocument(documentId) != null) {
            documentServices.delete(documentId);

            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento eliminato correttamente"));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    @GetMapping(value = "/document/{documentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Document> getDocument(@PathVariable("documentId") Integer documentId) {
        if (documentServices.getDocument(documentId) != null) {
            return ResponseEntity.ok(documentServices.getDocument(documentId));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    @GetMapping(value = "/document", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Document>> getDocuments() {
        return ResponseEntity.ok(documentServices.getDocuments());
    }

    @PostMapping(value = "/document", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Message> postDocument(@RequestBody Document document) {
        if (documentServices.getDocument(document.getId()) == null) {
            documentServices.save(document);
            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento inserito correttamente"));

        } else {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "Documento già presente");
        }
    }

    @PutMapping(value = "/document", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Message> putDocument(@RequestBody Document document) {
        if (documentServices.getDocument(document.getId()) != null) {
            documentServices.save(document);
            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento aggiornato correttamente"));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

}

