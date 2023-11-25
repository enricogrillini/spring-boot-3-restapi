package it.eg.cookbook.controller;

import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.ResponseCode;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.service.DocumentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document")
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
    public Message deleteDocument(@PathVariable Integer documentId) {
        documentServices.delete(documentId);
        return new Message(ResponseCode.OK.toString(), ResponseCode.OK.getDescription(), "Documento eliminato correttamente");
    }


    /**
     * Crea un documento
     *
     * @return
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message postDocument(@RequestBody Document document) {
        documentServices.save(document);
        return new Message(ResponseCode.OK.toString(), ResponseCode.OK.getDescription(), "Documento creato correttamente");

    }

    /**
     * Aggiorna un documento
     *
     * @return
     */
    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message putDocument(@RequestBody Document document) {
        documentServices.save(document);
        return new Message(ResponseCode.OK.toString(), ResponseCode.OK.getDescription(), "Documento aggiornato correttamente");
    }
}




