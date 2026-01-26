package it.eg.cookbook.repository;


import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.entity.AutoreEntity;
import it.eg.cookbook.model.entity.DocumentoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoreRepository extends CrudRepository<AutoreEntity, Long> {

    default AutoreEntity findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND, "Autore non trovato"));
    }

}