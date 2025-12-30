package it.eg.cookbook.repository;


import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.model.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {


    @Query("""
            SELECT j 
            FROM JobEntity j 
            WHERE (:id IS NULL OR j.id = :id) And
                  (:jobStatus IS NULL OR j.status = :jobStatus)
            """)
    List<JobEntity> find(@Param("id") Long id, JobStatus jobStatus);

    default JobEntity findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND, "Job non trovato"));
    }




}