package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.ResponseMessage;
import it.eg.cookbook.model.entity.DocumentEntity;
import it.eg.cookbook.model.mapper.DocumentMapper;
import it.eg.cookbook.service.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DocumentController implements DocumentApi {

    @Autowired
    private DocumentRepository documentServices;

    @Autowired
    DocumentMapper documentMapper;

    @Override
    public ResponseEntity<ResponseMessage> deleteDocument(Integer documentId) {
        Optional<DocumentEntity> documentOptional = documentServices.findById(documentId);
        if (documentOptional.isPresent()) {
            documentServices.delete(documentOptional.get());

            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento eliminato correttamente"));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    @Override
    public ResponseEntity<Document> getDocument(Integer documentId) {
        Optional<DocumentEntity> documentOptional = documentServices.findById(documentId);
        if (documentOptional.isPresent()) {
            return ResponseEntity.ok(documentMapper.entityToApi(documentOptional.get()));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    @Override
    public ResponseEntity<List<Document>> getDocuments() {
        return ResponseEntity.ok(documentMapper.entityToApi(documentServices.findAll()));
    }

    @Override
    public ResponseEntity<ResponseMessage> postDocument(Document document) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (document.getId() == null) {
            DocumentEntity documentEntity = documentMapper.apiToEntity(document);
            documentEntity.setUpdateBy(authentication.getName());
            documentServices.save(documentEntity);
            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento inserito correttamente"));

        } else {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "Id documento deve essere null in inserimento");
        }
    }

    @Override
    public ResponseEntity<ResponseMessage> putDocument(Document document) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Optional<DocumentEntity> documentOptional = documentServices.findById(document.getId());
        if (documentOptional.isPresent()) {
            DocumentEntity documentEntity = documentOptional.get();
            documentEntity.setUpdateBy(authentication.getName());
            documentServices.save(documentEntity);

            return ResponseEntity.ok(ResponseCode.OK.getResponseMessage("Documento aggiornato correttamente"));
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

}

