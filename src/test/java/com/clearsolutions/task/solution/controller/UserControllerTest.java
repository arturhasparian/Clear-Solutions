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

import static com.clearsolutions.task.solution.utils.TestUtils.getUserRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_Success() {
        UserRequest userRequest = getUserRequest(2000);
        when(userService.createUser(any())).thenReturn(UserDAO.builder()
                .build());

        ResponseEntity<?> response = userController.createUser(userRequest);

        verify(userService, times(1)).createUser(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createUser_shouldThrowBadRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");

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