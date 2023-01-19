package it.eg.cookbook.controller;

import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.ResponseCode;
import it.eg.cookbook.model.ResponseMessage;
import it.eg.cookbook.service.DocumentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {

    @Autowired
    private DocumentServices documentServices;

    /**
     * Ritorna la lista di tutti i documenti
     *
     * @return
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Document> getDocuments() {
        return documentServices.getDocuments();
    }

    /**
     * Ritorna un documento
     *
     * @return
     */
    @GetMapping(path = "/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Document getDocument(@PathVariable Integer documentId) {
        return documentServices.getDocument(documentId);
    }

    /**
     * Elimina un documento
     *
     * @return
     */
    @DeleteMapping(path = "/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage deleteDocument(@PathVariable Integer documentId) {
        documentServices.delete(documentId);
        return new ResponseMessage(ResponseCode.OK.toString(), ResponseCode.OK.getDescription(), "Documento eliminato correttamente");
    }


    /**
     * Crea un documento
     *
     * @return
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage postDocument(@RequestBody Document document) {
        documentServices.save(document);
        return new ResponseMessage(ResponseCode.OK.toString(), ResponseCode.OK.getDescription(), "Documento creato correttamente");

    }

    /**
     * Aggiorna un documento
     *
     * @return
     */
    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage putDocument(@RequestBody Document document) {
        documentServices.save(document);
        return new ResponseMessage(ResponseCode.OK.toString(), ResponseCode.OK.getDescription(), "Documento aggiornato correttamente");
    }
}




