package it.eg.cookbook.service;

import it.eg.cookbook.model.Autore;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.entity.DocumentoAutoreEntity;
import it.eg.cookbook.model.entity.DocumentoEntity;
import it.eg.cookbook.model.mapper.DocumentoMapper;
import it.eg.cookbook.repository.AutoreRepository;
import it.eg.cookbook.repository.DocumentoRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentoService {

    private final DocumentoMapper documentoMapper;
    private final DocumentoRepository documentoRepository;
    private final AutoreRepository autoreRepository;
    private final EntityManager entityManager;

    @Transactional
    public Documento update(Documento documento) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        DocumentoEntity documentoEntity = documentoRepository.findByIdOrThrow(documento.getId());
        documentoMapper.updateEntity(documentoEntity, documento, authentication.getName());

        // Elimino gli autori attulamente presenti
        documentoEntity.getDocumentoAutoreList().clear();
        documentoRepository.saveAndFlush(documentoEntity);

        // Aggiungo gli autori nuovi
        for (Autore autore : documento.getAutori()) {
            DocumentoAutoreEntity documentoAutoreEntity = new DocumentoAutoreEntity();
            documentoAutoreEntity.setDocumento(documentoEntity);
            documentoAutoreEntity.setAutore(autoreRepository.findByIdOrThrow(autore.getId()));

            documentoEntity.getDocumentoAutoreList().add(documentoAutoreEntity);
        }
        documentoRepository.saveAndFlush(documentoEntity);

        return documentoMapper.entityToApi(documentoEntity);
    }
}
