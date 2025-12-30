package it.eg.cookbook.controller;

import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.Task;
import it.eg.cookbook.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TaskController implements TaskApi {

    private final JobService jobService;

    @Override
    public ResponseEntity<Job> submitTask(Task task) {
        Job job = jobService.createJob(task);
        jobService.execute(job);

        return ResponseEntity.ok(job);
    }
}

