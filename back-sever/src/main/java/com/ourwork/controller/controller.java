package com.ourwork.controller;
import com.ourwork.model.model;
import com.ourwork.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/api/schedule" )
public class controller {
    @Autowired
    public service taskService;

    @GetMapping(produces = "application/json")
    public List<model> list(){
        return  taskService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<model> find(@PathVariable Long id) {
        return ResponseEntity.of(taskService.find(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<model> create(@RequestBody model task) {
        taskService.saveTask(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/tasks/{id}")
                .buildAndExpand(task.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<model> update(@PathVariable Long id, @RequestBody model task) {
        Optional<model> updatedTask = taskService.update(new model(id, task.getIncluding()));
        return ResponseEntity.of(updatedTask);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<model> deletedTask = taskService.delete(id);
        if (deletedTask.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

