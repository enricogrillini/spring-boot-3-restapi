package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentoController implements DocumentoApi {

    private final DocumentoService documentServices;

    @Override
    public ResponseEntity<Documento> create(Documento documento) {
        if (documento.getId() != null) {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "L'id documento non deve essere indicato");
        }

        documentServices.save(documento);

        return ResponseEntity.ok(documentServices.save(documento));
    }

    @Override
    public ResponseEntity<Message> delete(Long id) {
        documentServices.findByIdOrThrow(id);
        documentServices.delete(id);

        return ResponseEntity.ok(ResponseCode.OK.getMessage("Documento eliminato correttamente"));
    }

    @Override
    public ResponseEntity<List<Documento>> find() {
        return ResponseEntity.ok(documentServices.findAll());
    }

    @Override
    public ResponseEntity<Documento> get(Long id) {
        return ResponseEntity.ok(documentServices.findByIdOrThrow(id));
    }

    @Override
    public ResponseEntity<Documento> update(Long id, Documento documento) {
        if (!id.equals(documento.getId())) {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "Id documento incoerente");
        }

        documentServices.findByIdOrThrow(id);

        return ResponseEntity.ok(documentServices.save(documento));
    }

}

