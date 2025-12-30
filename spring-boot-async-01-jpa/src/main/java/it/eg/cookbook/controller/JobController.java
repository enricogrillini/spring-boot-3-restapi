package it.eg.cookbook.controller;

import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.model.Task;
import it.eg.cookbook.model.entity.JobEntity;
import it.eg.cookbook.model.mapper.JobMapper;
import it.eg.cookbook.repository.JobRepository;
import it.eg.cookbook.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JobController implements JobApi {


    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public ResponseEntity<List<Job>> search(Long id, JobStatus status) {
        List<JobEntity> jobEntityList = jobRepository.findByIdOrStatus(id, status);

        return ResponseEntity.ok(jobMapper.entityToApi(jobEntityList));
    }
}

