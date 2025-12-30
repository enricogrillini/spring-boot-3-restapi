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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {

    private final JobMapper jobMapper;
    private final JobRepository jobRepository;


    @Async
    public Job execute(Task task) {
        JobEntity job = jobMapper.apiToEntity(task);
        jobRepository.saveAndFlush(job);

        // Avvio il task
        CompletableFuture.supplyAsync(() -> {
            try {
                task1();

                // Update Job
                log.info("Job terminato con successo");
                job.setStatus(JobStatus.COMPLETED);
                jobRepository.saveAndFlush(job);

            } catch (Exception e) {
                // Update Job
                log.info("Job fallito");
                job.setStatus(JobStatus.FAILED);
                jobRepository.saveAndFlush(job);
            }
            return null;
        });

        return jobMapper.entityToApi(job);
    }

    private void task1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
    }

}
