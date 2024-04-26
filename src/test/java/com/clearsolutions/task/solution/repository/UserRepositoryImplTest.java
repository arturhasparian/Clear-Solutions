package com.clearsolutions.task.solution.repository;

import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void saveUser_Success() {
        UserDAO user = UserDAO.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        UserDAO savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getBirthDate(), savedUser.getBirthDate());
    }

    @Test
    void findByEmail_UserExists_Success() {
        String email = "test@example.com";
        UserDAO user = UserDAO.builder()
                .email(email)
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        userRepository.save(user);

        UserDAO foundUser = userRepository.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    void findByEmail_UserNotFound_ExceptionThrown() {
        assertThrows(NotFoundException.class, () -> userRepository.findByEmail("nonexistent@example.com"));
    }

    @Test
    void deleteUserByEmail_UserExists_Success() {
        String email = "test@example.com";
        UserDAO user = UserDAO.builder()
                .email(email)
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        userRepository.save(user);

        userRepository.deleteUserByEmail(email);

        assertThrows(NotFoundException.class, () -> userRepository.findByEmail(email));
    }

    @Test
    void findByBirthDateBetween_Success() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2002, 12, 31);
        UserDAO user1 = UserDAO.builder()
                .email("user1@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        UserDAO user2 = UserDAO.builder()
                .email("user2@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(2001, 5, 15))
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        List<UserDAO> users = userRepository.findByBirthDateBetween(fromDate, toDate);

        assertEquals(2, users.size());
    }
}
