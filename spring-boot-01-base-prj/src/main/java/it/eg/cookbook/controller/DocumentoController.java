package it.eg.cookbook.controller;

import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.model.ResponseCode;
import it.eg.cookbook.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DocumentoController {

    @Autowired
    DocumentoService documentServices;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/documento",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<Documento> create(@RequestBody Documento documento) {
        documentServices.save(documento);

        return ResponseEntity.ok(documentServices.save(documento));
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/documento/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<Message> delete(@PathVariable("id") Long id) {
        documentServices.findByIdOrThrow(id);
        documentServices.delete(id);

        return ResponseEntity.ok(ResponseCode.OK.getMessage("Documento eliminato correttamente"));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/documento",
            produces = {"application/json"}
    )
    public ResponseEntity<List<Documento>> find() {
        return ResponseEntity.ok(documentServices.findAll());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/documento/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<Documento> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentServices.findByIdOrThrow(id));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/documento/{id}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<Documento> update(@PathVariable("id") Long id, @RequestBody Documento documento) {
        documentServices.findByIdOrThrow(id);

        return ResponseEntity.ok(documentServices.save(documento));
    }

}

