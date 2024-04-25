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
}
