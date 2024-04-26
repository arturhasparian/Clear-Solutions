package com.clearsolutions.task.solution.controller;

import com.clearsolutions.task.solution.util.UserUris;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.clearsolutions.task.solution.utils.TestUtils.getChangedUserRequest;
import static com.clearsolutions.task.solution.utils.TestUtils.getUserRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @Order(1)
    void createUser_Success() {
        mockMvc.perform(post(UserUris.ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserRequest(2000))))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @Order(3)
    void createUser_shouldThrowBadRequest() {
        mockMvc.perform(post(UserUris.ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserRequest(2020))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @Order(2)
    void createUser_shouldThrowConflict() {
        mockMvc.perform(post(UserUris.ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserRequest(2000))))
                .andExpect(status().isConflict());
    }

    @Test
    @SneakyThrows
    @Order(4)
    void updateUser_Success() {
        mockMvc.perform(put(UserUris.ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getChangedUserRequest(2004))))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @Order(6)
    void updateUser_ShouldThrowNotFound() {
        mockMvc.perform(put(UserUris.ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getChangedUserRequest(2004))))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Order(5)
    void deleteUser_Success() {
        mockMvc.perform(delete("/api/v1/user/{email}", "test@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @Order(7)
    void deleteUser_ShouldThrowNotFound() {
        mockMvc.perform(delete(UserUris.EMAIL, "test@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @Order(8)
    void searchUsersByBirthDateRange() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2005, 12, 31);
        mockMvc.perform(post("/api/v1/user/search")
                        .param("fromDate", fromDate.toString())
                        .param("toDate", toDate.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @Order(9)
    void searchUsersByBirthDateRange_ShouldThrowBadRequest() {
        LocalDate toDate = LocalDate.of(2000, 1, 1);
        LocalDate fromDate = LocalDate.of(2005, 12, 31);
        mockMvc.perform(post("/api/v1/user/search")
                        .param("fromDate", fromDate.toString())
                        .param("toDate", toDate.toString()))
                .andExpect(status().isBadRequest());
    }
}