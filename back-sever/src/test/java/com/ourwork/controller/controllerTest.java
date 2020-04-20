package com.ourwork.controller;

import com.google.gson.Gson;
import com.ourwork.model.model;
import com.ourwork.service.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controller.class)
public class controllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private service service;
    private List<model> tasks = new ArrayList<model>();

    @BeforeEach
    void setUp() {
        tasks.add(new model(1L, "a"));
    }

    @Test
    public void shouldGetAll() throws Exception {
        when(service.getAll()).thenReturn(tasks);

        this.mockMvc.perform(get("/api/schedule")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].including").value("a"));
    }


    @Test
    public void shouldFindTaskByIdIfPresent() throws Exception {
        when(service.find(3L)).thenReturn(Optional.of(new model(3L, "X")));
        this.mockMvc.perform(get("/api/schedule/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.including").value("X"));
    }

    @Test
    public void shouldReturnNotFoundWhenFindByIdIfNotPresent() throws Exception {
        when(service.find(3L)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/schedule/3")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteWhenExist() throws Exception {
        when(service.delete(2L)).thenReturn(Optional.of(new model(2L, "B")));

        this.mockMvc.perform(delete("/api/schedule/2")).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundWhenDeleteIfNotPresent() throws Exception {
        when(service.delete(2L)).thenReturn(Optional.empty());

        this.mockMvc.perform(delete("/api/schedule/2")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateTask() throws Exception {
        model task = new model(1L, "new");
        model savedTask = new model(1L, "new");
        when(service.saveTask(task)).thenReturn(savedTask);
        this.mockMvc.perform(post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(task)))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void shouldChangeTaskById() throws Exception {
        model task = new model(2L, "updated");
        model updated = new model(1L, "updated");
        when(service.update(any())).thenReturn(Optional.of(updated));
        this.mockMvc.perform(put("/api/schedule/1")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(task)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.including").value("updated"));
    }

    @Test
    public void shouldReturnNotFoundWhenChangeTaskButDoesNotExit() throws Exception {
        model task = new model(2L, "updated");
        when(service.update(any())).thenReturn(Optional.empty());
        this.mockMvc.perform(put("/api/schedule/1")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(task)))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
