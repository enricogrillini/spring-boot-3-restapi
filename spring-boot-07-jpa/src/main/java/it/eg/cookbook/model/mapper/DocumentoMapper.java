package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Autore;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.entity.AutoreEntity;
import it.eg.cookbook.model.entity.DocumentoAutoreEntity;
import it.eg.cookbook.model.entity.DocumentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {


    @Mapping(target = "autori", source = "documentoAutoreList")
    Documento entityToApi(DocumentoEntity entity);

    DocumentoEntity apiToEntity(Documento apiDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateBy", source = "updatedBy")
    @Mapping(target = "documentoAutoreList", ignore = true)
    void updateEntity(@MappingTarget DocumentoEntity entity, Documento apiDto, String updatedBy);

    List<Documento> documentoAutorelistEntityToApi(Iterable<DocumentoEntity> entityList);

    List<DocumentoEntity> listApiToEntity(Iterable<Documento> apiDtoList);


    Autore autoreEntityToApi(AutoreEntity entity);
    default List<Autore> documentoAutorelistEntityToApi(List<DocumentoAutoreEntity> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }

        return entityList.stream().map(da -> autoreEntityToApi(da.getAutore())).toList();
    }

}
