package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.entity.DocumentoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {

    Documento entityToApi(DocumentoEntity entity);

    DocumentoEntity apiToEntity(Documento apiDto);

    List<Documento> listEntityToApi(Iterable<DocumentoEntity> entityList);

    List<DocumentoEntity> listApiToEntity(Iterable<Documento> apiDtoList);
}
