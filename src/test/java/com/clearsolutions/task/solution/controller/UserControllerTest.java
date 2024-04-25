package com.clearsolutions.task.solution.controller;

import com.clearsolutions.task.solution.data.UserRequest;
import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void shouldCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setBirthDate(LocalDate.of(2000, 1, 1));
        userRequest.setAddress("Test Street");
        userRequest.setPhone("+1-234-567-89");

        when(userService.createUser(userRequest)).thenReturn(new UserDAO());

        ResponseEntity<?> response = userController.createUser(userRequest);

        verify(userService, times(1)).createUser(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldThrowBadRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setBirthDate(LocalDate.of(2000, 1, 1));
        userRequest.setAddress("Test Street");
        userRequest.setPhone("+1-234-567-89");

        ResponseEntity<?> response = userController.createUser(userRequest);

        assertThrows(MethodArgumentNotValidException.class, () -> userController.createUser(userRequest));


    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void searchUsersByBirthDateRange() {
    }
}