package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.entity.DocumentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    Document entityToApi(DocumentEntity documentEntity);

    DocumentEntity apiToEntity(Document document);

    List<Document> entityToApi(Iterable<DocumentEntity> documentEntities);

    List<DocumentEntity> apiToEntity(Iterable<Document> documents);

}
