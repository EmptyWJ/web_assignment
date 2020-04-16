package com.ourwork;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @Order(1)
    public void noParamTasksShouldReturnAllTasksFromService() throws Exception {
        this.mockMvc.perform(get("/api/schedule")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].including").value("test"));
    }

    @Test
    @Order(2)
    public void shouldSaveTask() throws Exception {
        this.mockMvc.perform(post("/api/schedule")
                .content("{ \"id\" : 2, \"including\" : \"check if it works\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void shouldGetTaskByTaskId() throws Exception {
        this.mockMvc.perform(get("/api/schedule/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.including").value("check if it works"));
    }

    @Test
    @Order(4)
    public void shouldUpdateContentByTaskId() throws Exception {
        this.mockMvc.perform(put("/api/schedule/2")
                .content("{ \"including\" : \"it should work\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.including").value("it should work"));
    }

    @Test
    @Order(5)
    public void shouldDeleteByTaskId() throws Exception {
        this.mockMvc.perform(delete("/api/schedule/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void shouldGetNotFoundWhenTaskDoesNotExist() throws Exception {
        this.mockMvc.perform(delete("/api/schedule/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
