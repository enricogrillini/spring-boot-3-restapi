package it.eg.cookbook.controller;

import it.eg.cookbook.model.Message;
import it.eg.cookbook.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentoController implements TaskApi {


    @Override
    public ResponseEntity<Message> create(Task task) {
        return null;
    }
}

