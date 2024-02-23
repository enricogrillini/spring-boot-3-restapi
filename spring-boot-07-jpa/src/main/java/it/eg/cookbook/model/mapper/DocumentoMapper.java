package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.entity.DocumentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {

    Documento entityToApi(DocumentoEntity entity);

    DocumentoEntity apiToEntity(Documento apiDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateBy", source = "updatedBy")
    void updateEntity(@MappingTarget DocumentoEntity entity, Documento apiDto, String updatedBy);

    List<Documento> listEntityToApi(Iterable<DocumentoEntity> entityList);

    List<DocumentoEntity> listApiToEntity(Iterable<Documento> apiDtoList);
}
