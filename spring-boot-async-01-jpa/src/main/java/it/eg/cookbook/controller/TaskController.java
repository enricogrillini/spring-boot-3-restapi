package it.eg.cookbook.controller;

import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.Message;
import it.eg.cookbook.model.Task;
import it.eg.cookbook.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TaskController implements TaskApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<Job> submitTask(Task task) {
        return ResponseEntity.ok(taskService.execute(task));
    }
}

