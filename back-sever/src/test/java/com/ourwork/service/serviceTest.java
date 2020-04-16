package com.ourwork.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.ourwork.model.model;
import com.ourwork.store.store;


public class serviceTest {
    @Mock
    private store taskStore;

    @InjectMocks
    private service taskService = new service();

    private ArrayList<model> tasks;


    @BeforeEach
    void setUp() {
        tasks = new ArrayList<>();
    }

    @Test
    public void shouldSaveTask() {
        when(taskStore.readModel()).thenReturn(tasks);

        model savedTask = taskService.saveTask(new model(1L, "newTask"));

        assertNotNull(savedTask.getuTime());
        verify(taskStore).writeTasks(any());
    }

    @Test
    public void shouldGetAllTasks() {
        when(taskStore.readModel()).thenReturn(tasks);

        List<model> all = taskService.getAll();

        assertEquals(tasks, all);
    }

    public void shouldFindTask() {
        tasks.add(new model(1L, "task"));
        when(taskStore.readModel()).thenReturn(tasks);

        Optional<model> optionalTask = taskService.find(1L);

        model task = optionalTask.get();
        assertEquals(1L, task.getId());
        assertEquals("task", task.getIncluding());
    }

    @Test
    public void shouldGetEmptyTask() {
        when(taskStore.readModel()).thenReturn(tasks);

        Optional<model> optionalTask = taskService.find(1L);

        assertFalse(optionalTask.isPresent());
    }

    @Test
    public void shouldUpdateTask() {
        tasks.add(new model(1L, "task"));
        when(taskStore.readModel()).thenReturn(tasks);

        Optional<model> optionalTask = taskService.update(new model(1L, "new task"));

        model task = optionalTask.get();
        assertEquals(1L, task.getId());
        assertEquals("new task", task.getIncluding());
        assertNotNull(task.getuTime());
        verify(taskStore).writeTasks(any());
    }

    @Test
    public void shouldNotUpdateTaskWhenNotExist() {
        when(taskStore.readModel()).thenReturn(tasks);

        Optional<model> optionalTask = taskService.update(new model(1L, "new task"));

        assertFalse(optionalTask.isPresent());
        verify(taskStore, new Times(0)).writeTasks(any());
    }

    @Test
    public void shouldDeleteTask() {
        tasks.add(new model(1L, "task"));
        when(taskStore.readModel()).thenReturn(tasks);

        Optional<model> optionalTask = taskService.delete(1L);

        model task = optionalTask.get();
        assertEquals(1L, task.getId());
        verify(taskStore).writeTasks(any());
    }

    @Test
    public void shouldNotDeleteTaskWhenNotExist() {
        when(taskStore.readModel()).thenReturn(tasks);

        Optional<model> optionalTask = taskService.delete(1L);

        assertFalse(optionalTask.isPresent());
        verify(taskStore, new Times(0)).writeTasks(any());
    }
}
