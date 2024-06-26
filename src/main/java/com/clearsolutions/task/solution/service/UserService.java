package com.clearsolutions.task.solution.service;

import com.clearsolutions.task.solution.data.UserRequest;
import com.clearsolutions.task.solution.model.UserDAO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDAO createUser(UserRequest request);

    UserDAO updateUser(UserRequest updatedUser);

    void deleteUser(String email);

    List<UserDAO> searchUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}
