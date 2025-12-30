package it.eg.cookbook.model.mapper;


import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.model.Task;
import it.eg.cookbook.model.entity.JobEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", imports = {JobStatus.class, LocalDateTime.class})
public interface JobMapper {

//    Documento entityToApi(DocumentoEntity entity);
//

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskName", source = "name")
    @Mapping(target = "taskDetail", source = "detail")
    @Mapping(target = "startDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(JobStatus.RUNNING)")
    JobEntity apiToEntity(Task task);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "task.name", source = "taskName")
    @Mapping(target = "task.detail", source = "taskDetail")
    Job entityToApi(JobEntity jobEntity);

//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "updateBy", source = "updatedBy")
//    void updateEntity(@MappingTarget DocumentoEntity entity, Documento apiDto, String updatedBy);
//
//    List<Documento> listEntityToApi(Iterable<DocumentoEntity> entityList);
//
//    List<DocumentoEntity> listApiToEntity(Iterable<Documento> apiDtoList);


    default LocalDateTime offsetDateTimeToLocalDateTime(OffsetDateTime value) {
        return value != null
                ? value.atZoneSameInstant(ZoneId.of("Europe/Rome")).toLocalDateTime()
                : null;
    }

    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime value) {
        return value != null
                ? value.atZone(ZoneId.of("Europe/Rome")).toOffsetDateTime()
                : null;
    }
}
