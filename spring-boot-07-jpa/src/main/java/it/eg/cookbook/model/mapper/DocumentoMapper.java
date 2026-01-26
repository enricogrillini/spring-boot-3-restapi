package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Autore;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.entity.AutoreEntity;
import it.eg.cookbook.model.entity.DocumentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {

    Autore entityToApi(AutoreEntity entity);

    AutoreEntity apiToEntity(Autore apiDto);

    @Mapping(target = "autori", source = "autoreEntityList")
    Documento entityToApi(DocumentoEntity entity);

    DocumentoEntity apiToEntity(Documento apiDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateBy", source = "updatedBy")
    void updateEntity(@MappingTarget DocumentoEntity entity, Documento apiDto, String updatedBy);

    List<Documento> listEntityToApiDocumento(Iterable<DocumentoEntity> entityList);

    List<DocumentoEntity> listApiToEntityDocumento(Iterable<Documento> apiDtoList);

    List<Autore> listEntityToApiAutore(List<AutoreEntity> entityList);

    List<AutoreEntity> listApiToEntityAutore(List<Autore> apiDtoList);

}
