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
import java.util.Arrays;
import java.util.List;

import static com.clearsolutions.task.solution.utils.TestUtils.getUserRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void updateUser_Success() {
        userService = new UserServiceImpl(userRepository);
        UserRequest userRequest = getUserRequest(2000);
        UserDAO existingUser = UserDAO.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("test")
                .lastName("test")
                .birthDate(LocalDate.of(2004, 10, 24))
                .address("test")
                .phone("+1-234-567")
                .build();

        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserDAO updatedUser = userService.updateUser(userRequest);

        assertNotNull(updatedUser);
        assertEquals(userRequest.getEmail(), updatedUser.getEmail());
        assertEquals(userRequest.getBirthDate(), updatedUser.getBirthDate());
        assertEquals(userRequest.getAddress(), updatedUser.getAddress());
    }

    @Test
    void updateUser_ShouldThrowException() {
        userService = new UserServiceImpl(userRepository);
        UserRequest userRequest = getUserRequest(2000);

        doThrow(new NotFoundException("User not found")).when(userRepository).findByEmail(userRequest.getEmail());

        assertThrows(NotFoundException.class, () -> userService.updateUser(userRequest));
    }


    @Test
    void deleteUser_Success() {
        userService = new UserServiceImpl(userRepository);
        String userEmail = "test@example.com";
        UserDAO existingUser = UserDAO.builder()
                .id(1L)
                .email(userEmail)
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000, 1, 1))
                .address("Test Street")
                .phone("+1-234-567-89")
                .build();

        when(userRepository.findByEmail(userEmail)).thenReturn(existingUser);

        userService.deleteUser(userEmail);

        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(userRepository, times(1)).deleteUserByEmail(userEmail);
    }

    @Test
    void deleteUser_ShouldThrowException() {
        userService = new UserServiceImpl(userRepository);
        String userEmail = "test@example.com";
        UserRequest userRequest = getUserRequest(2000);

        doThrow(new NotFoundException("User not found")).when(userRepository).findByEmail(userEmail);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userRequest.getEmail()));
    }

    @Test
    void searchUsersByBirthDateRange() {
        userService = new UserServiceImpl(userRepository);
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);
        UserDAO user1 = UserDAO.builder()
                .id(1L)
                .email("test1@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995, 5, 15))
                .address("Test Street 1")
                .phone("+1-234-567-89")
                .build();
        UserDAO user2 = UserDAO.builder()
                .id(2L)
                .email("test2@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1998, 8, 20))
                .address("Test Street 2")
                .phone("+1-234-567-90")
                .build();
        List<UserDAO> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findByBirthDateBetween(fromDate, toDate)).thenReturn(expectedUsers);

        List<UserDAO> actualUsers = userService.searchUsersByBirthDateRange(fromDate, toDate);

        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
    }

    @Test
    void searchUsersByBirthDateRange_ExceptionThrown() {
        userService = new UserServiceImpl(userRepository);
        LocalDate fromDate = LocalDate.of(2000, 1, 1);

        assertThrows(BadRequestException.class, () ->
                userService.searchUsersByBirthDateRange(fromDate, fromDate.minusDays(1)));
    }
}