package it.eg.cookbook.service;

import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.model.Task;
import it.eg.cookbook.model.entity.JobEntity;
import it.eg.cookbook.model.mapper.JobMapper;
import it.eg.cookbook.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobService {

    private final JobMapper jobMapper;
    private final JobRepository jobRepository;


    public Job createJob(Task task) {
        JobEntity job = jobMapper.apiToEntity(task);
        jobRepository.saveAndFlush(job);

        return jobMapper.entityToApi(job);
    }

    public Job restartJob(Long id) {
        JobEntity jobEntity = jobRepository.findByIdOrThrow(id);
        jobEntity.setStatus(JobStatus.RUNNING);
        jobEntity.setStartDate(LocalDateTime.now());
        jobEntity.setEndDate(null);
        jobEntity.setMessage("Restarted");

        jobRepository.saveAndFlush(jobEntity);

        return jobMapper.entityToApi(jobEntity);
    }

    private void terminateJob(Long id, JobStatus jobStatus, String message) {
        JobEntity jobEntity = jobRepository.findByIdOrThrow(id);
        jobEntity.setStatus(jobStatus);
        jobEntity.setEndDate(LocalDateTime.now());
        jobEntity.setMessage(message);

        jobRepository.saveAndFlush(jobEntity);
    }

    @Async
    public void execute(Job job) {
        // Avvio il task
        CompletableFuture.supplyAsync(() -> {
            try {
                task1();

                // Update Job
                log.info("Job terminato con successo");
                terminateJob(job.getId(), JobStatus.COMPLETED, "Job terminato con successo");

            } catch (Exception e) {
                // Update Job
                log.info("Job fallito");
                terminateJob(job.getId(), JobStatus.FAILED, "Job fallito");

            }
            return null;
        });
    }

    private void task1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(15);
    }

}
