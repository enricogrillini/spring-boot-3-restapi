package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.model.entity.DocumentoEntity;
import it.eg.cookbook.model.mapper.DocumentoMapper;
import it.eg.cookbook.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentoController implements DocumentoApi {

    private final DocumentoMapper documentoMapper;
    private final DocumentoRepository documentServices;

    @Override
    public ResponseEntity<Documento> create(Documento documento) {
        if (documento.getId() != null) {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "L'id documento non deve essere indicato");
        }

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        DocumentoEntity documentoEntity = documentoMapper.apiToEntity(documento);
        documentoMapper.updateEntity(documentoEntity, documento, authentication.getName());
        documentServices.save(documentoEntity);

        return ResponseEntity.ok(documentoMapper.entityToApi(documentoEntity));
    }

    @Override
    public ResponseEntity<Message> delete(Long id) {
        DocumentoEntity documentoEntity = documentServices.findByIdOrThrow(id);
        documentServices.delete(documentoEntity);

        return ResponseEntity.ok(ResponseCode.OK.getMessage("Documento eliminato correttamente"));
    }

    @Override
    public ResponseEntity<List<Documento>> find() {
        Iterable<DocumentoEntity> list = documentServices.findAll();

        return ResponseEntity.ok(documentoMapper.listEntityToApi(list));
    }

    @Override
    public ResponseEntity<Documento> get(Long id) {
        DocumentoEntity documentoEntity = documentServices.findByIdOrThrow(id);

        return ResponseEntity.ok(documentoMapper.entityToApi(documentoEntity));
    }

    @Override
    public ResponseEntity<Documento> update(Long id, Documento documento) {
        if (!id.equals(documento.getId())) {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "L'id documento incoerente");
        }

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        DocumentoEntity documentoEntity = documentServices.findByIdOrThrow(id);
        documentoMapper.updateEntity(documentoEntity, documento, authentication.getName());
        documentServices.save(documentoEntity);

        return ResponseEntity.ok(documentoMapper.entityToApi(documentoEntity));
    }

}

