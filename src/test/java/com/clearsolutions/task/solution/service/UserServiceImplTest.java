package com.clearsolutions.task.solution.service;

import com.clearsolutions.task.solution.data.UserRequest;
import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.repository.UserRepository;
import com.clearsolutions.task.solution.util.exception.BadRequestException;
import com.clearsolutions.task.solution.util.exception.NotFoundException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.clearsolutions.task.solution.utils.TestUtils.getUserRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @Test
    void createUser_Success() {
        when(userRepository.findByEmail(any())).thenThrow(new NotFoundException(""));
        userService = new UserServiceImpl(userRepository);
        UserRequest userRequest = getUserRequest(2000);

        UserDAO user = userService.createUser(userRequest);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals(LocalDate.of(2000, 1, 1), user.getBirthDate());
    }

    @Test
    void createUser_ShouldThrowException() throws IllegalAccessException {
        userService = new UserServiceImpl(userRepository);
        FieldUtils.writeField(userService, "age", 18, true);
        UserRequest userRequest = getUserRequest(2020);

        assertThrows(BadRequestException.class, () -> userService.createUser(userRequest));
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