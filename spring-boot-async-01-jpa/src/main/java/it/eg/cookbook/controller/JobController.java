package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.model.entity.JobEntity;
import it.eg.cookbook.model.mapper.JobMapper;
import it.eg.cookbook.repository.JobRepository;
import it.eg.cookbook.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JobController implements JobApi {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final JobService jobService;

    @Override
    public ResponseEntity<List<Job>> search(Long id, JobStatus status) {
        List<JobEntity> jobEntityList = jobRepository.find(id, status);

        return ResponseEntity.ok(jobMapper.entityToApi(jobEntityList));
    }

    @Override
    public ResponseEntity<Job> restartJob(Long id) {
        JobEntity jobEntity = jobRepository.findByIdOrThrow(id);

        if (jobEntity.getStatus() == JobStatus.FAILED) {
            Job job = jobService.restartJob(jobEntity.getId());
            jobService.execute(job);

            return ResponseEntity.ok(jobService.restartJob(jobEntity.getId()));

        } else if (jobEntity.getStatus() == JobStatus.RUNNING) {
            if (Duration.between(jobEntity.getStartDate(), LocalDateTime.now()).toMinutes() > 10) {
                Job job = jobService.restartJob(jobEntity.getId());
                jobService.execute(job);

                return ResponseEntity.ok(job);

            } else {
                throw new ApiException(ResponseCode.BUSINESS_ERROR, "Non è possibile rilanciare un Job in stato RUNNING avviato da meno di 10 minuti");
            }
        } else {
            throw new ApiException(ResponseCode.BUSINESS_ERROR, "Non è possibile rilanciare un Job in stato %s".formatted(jobEntity.getStatus() ));
        }
    }
}

