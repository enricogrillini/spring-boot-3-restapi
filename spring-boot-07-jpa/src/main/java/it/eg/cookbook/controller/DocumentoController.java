package it.eg.cookbook.controller;

import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.model.entity.DocumentoEntity;
import it.eg.cookbook.model.mapper.DocumentoMapper;
import it.eg.cookbook.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentoController implements DocumentoApi {

    private final DocumentoMapper documentoMapper;
    private final DocumentoRepository documentServices;

    @Override
    public ResponseEntity<Message> create(Documento document) {
        return null;
    }

    @Override
    public ResponseEntity<Message> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Documento>> find() {
        Iterable<DocumentoEntity> list = documentServices.findAll();

        return ResponseEntity.ok(documentoMapper.listEntityToApi(list));
    }

    @Override
    public ResponseEntity<Documento> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Message> update(Documento document) {
        return null;
    }

//    @Override
//    public ResponseEntity<Message> deleteDocument(Integer id) {
//        if (documentServices.get(documentId) != null) {
//            documentServices.delete(documentId);
//
//            return ResponseEntity.ok(ResponseCode.OK.getMessage("Documento eliminato correttamente"));
//        } else {
//            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
//        }
//    }
//
//    @Override
//    public ResponseEntity<Document> getDocument(Integer documentId) {
//        if (documentServices.getDocument(documentId) != null) {
//            return ResponseEntity.ok(documentServices.getDocument(documentId));
//        } else {
//            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
//        }
//    }
//
//    @Override
//    public ResponseEntity<List<Document>> getDocuments() {
//        return ResponseEntity.ok(documentServices.getDocuments());
//    }
//
//    @Override
//    public ResponseEntity<Message> postDocument(Document document) {
//        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        if (documentServices.getDocument(document.getId()) == null) {
//            document.setUpdateBy(authentication.getName());
//            documentServices.save(document);
//            return ResponseEntity.ok(ResponseCode.OK.getMessage("Documento inserito correttamente"));
//
//        } else {
//            throw new ApiException(ResponseCode.BUSINESS_ERROR, "Documento già presente");
//        }
//    }
//
//    @Override
//    public ResponseEntity<Message> putDocument(Document document) {
//        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        if (documentServices.getDocument(document.getId()) != null) {
//            document.setUpdateBy(authentication.getName());
//            documentServices.save(document);
//            return ResponseEntity.ok(ResponseCode.OK.getMessage("Documento aggiornato correttamente"));
//        } else {
//            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
//        }
//    }

}

