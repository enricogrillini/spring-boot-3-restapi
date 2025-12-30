package it.eg.cookbook.repository;


import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.model.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    List<JobEntity> findByIdOrStatus(Long id, JobStatus jobStatus);

}