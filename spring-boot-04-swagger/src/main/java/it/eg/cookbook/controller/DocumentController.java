package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.ResponseMessage;
import it.eg.cookbook.service.DocumentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DocumentController implements DocumentApi {

    @Autowired
    private DocumentServices documentServices;

    @Override
    public ResponseEntity<ResponseMessage> deleteDocument(Integer documentId) {
        if (documentServices.getDocument(documentId) != null) {
            documentServices.delete(documentId);

            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento eliminato correttamente"));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    @Override
    public ResponseEntity<Document> getDocument(Integer documentId) {
        if (documentServices.getDocument(documentId) != null) {
            return ResponseEntity.ok(documentServices.getDocument(documentId));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    @Override
    public ResponseEntity<List<Document>> getDocuments() {
        return ResponseEntity.ok(documentServices.getDocuments());
    }

    @Override
    public ResponseEntity<ResponseMessage> postDocument(Document document) {
        if (documentServices.getDocument(document.getId()) == null) {
            documentServices.save(document);
            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento inserito correttamente"));

        } else {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "Documento già presente");
        }
    }

    @Override
    public ResponseEntity<ResponseMessage> putDocument(Document document) {
        if (documentServices.getDocument(document.getId()) != null) {
            documentServices.save(document);
            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento aggiornato correttamente"));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

}

