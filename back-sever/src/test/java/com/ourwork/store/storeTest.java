package com.ourwork.store;
import com.ourwork.model.model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class storeTest {
    @Autowired
    private store taskStore;

    @AfterEach
    void tearDown() {
        taskStore.writeTasks(Arrays.asList(createTask(1L, "test")));
    }
    private model createTask(long l, String test) {
        model task = new model(l, test);
        task.setuTime();
        return task;
    }

    @Test
    public void shouldReadTasks() {
        List<model> tasks = taskStore.readModel();
        assertEquals(1, tasks.size());
        assertEquals(1L, tasks.get(0).getId());
        assertEquals("test", tasks.get(0).getIncluding());
        assertEquals(LocalDateTime.of(2020, 4, 5, 0, 0), tasks.get(0).getuTime());
    }

    @Test
    public void shouldWriteTasks() {
        List<model> tasks = Arrays.asList(createTask(1L, "task 1"), createTask(2L, "task 2"));

        taskStore.writeTasks(tasks);

        List<model> tasksInStore = taskStore.readModel();
        assertEquals(2, tasksInStore.size());
        assertNotNull(tasksInStore.get(1).getuTime());
        assertEquals("task 2", tasksInStore.get(1).getIncluding());
    }
}

