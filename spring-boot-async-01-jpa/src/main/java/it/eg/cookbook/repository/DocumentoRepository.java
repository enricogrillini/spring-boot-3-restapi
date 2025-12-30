package it.eg.cookbook.repository;


import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.entity.DocumentoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends CrudRepository<DocumentoEntity, Long> {

    default DocumentoEntity findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato"));
    }

}