package com.clearsolutions.task.solution.utils;

import com.clearsolutions.task.solution.data.UserRequest;

import java.time.LocalDate;

public class TestUtils {
    public static UserRequest getUserRequest(int year) {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setBirthDate(LocalDate.of(year, 1, 1));
        userRequest.setAddress("Test Street");
        userRequest.setPhone("+1-234-567-89");
        return userRequest;
    }

    public static UserRequest getChangedUserRequest(int year) {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setFirstName("Alan");
        userRequest.setLastName("Poe");
        userRequest.setBirthDate(LocalDate.of(year, 5, 5));
        userRequest.setAddress("Test");
        userRequest.setPhone("+1-234-567");
        return userRequest;
    }
}
