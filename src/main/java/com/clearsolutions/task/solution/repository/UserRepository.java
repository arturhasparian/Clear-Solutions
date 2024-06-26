package com.clearsolutions.task.solution.repository;

import com.clearsolutions.task.solution.model.UserDAO;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    UserDAO save(UserDAO user);

    UserDAO findByEmail(String email);

    void deleteUserByEmail(String email);

    List<UserDAO> findByBirthDateBetween(LocalDate fromDate, LocalDate toDate);
}
