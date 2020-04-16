package com.ourwork.service;
import com.ourwork.store.store;
import com.ourwork.model.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.ourwork.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class service {
    @Autowired
    public store store;

    public List<model> getAll() {
        return store.readModel();
    }

    public model saveTask(model task) {
        List<model> tasks = new ArrayList<>(store.readModel());
        task.setuTime();
        tasks.add(task);
        store.writeTasks(tasks);
        return task;
    }

    public Optional<model> find(Long id) {
        return store.readModel().stream().filter(task -> task.getId() == id).findAny();
    }

    public Optional<model> update(model task) {
        List<model> tasks = new ArrayList<>(store.readModel());
        Optional<model> any = tasks.stream().filter(task1 -> task1.getId() == task.getId()).findAny();
        if (any.isPresent()) {
            any.get().setIncluding(task.getIncluding());
            any.get().setuTime();
            store.writeTasks(tasks);
        }
        return any;
    }
    public Optional<model> delete(Long id) {
        List<model> tasks = store.readModel();
        Optional<model> any = tasks.stream().filter(task1 -> task1.getId() == id).findAny();
        if (any.isPresent()) {
            store.writeTasks(tasks.stream().filter(task -> task.getId() != id).collect(Collectors.toList()));
            return any;
        }
        return any;
    }
}