package com.clearsolutions.task.solution.repository;

import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.util.exception.ConflictException;
import com.clearsolutions.task.solution.util.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, UserDAO> userMap = new HashMap<>();
    private long idCounter = 1;

    @Override
    public UserDAO save(UserDAO user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
        }
        userMap.put(user.getId(), user);
        return user;
    }
    @Override
    public void findByEmail(String email) {
        for (UserDAO user : userMap.values()) {
            if (user.getEmail().equals(email)) {
                throw new ConflictException("User with this email " + email + " already exists");
            }
        }
    }

    @Override
    public UserDAO findExistingUser(String email) {
        for (UserDAO user : userMap.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public void deleteUserByEmail(String email) {
        userMap.entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().equals(email))
                .findFirst()
                .map(Map.Entry::getKey)
                .ifPresent(userMap::remove);
    }

    @Override
    public List<UserDAO> findByBirthDateBetween(LocalDate fromDate, LocalDate toDate) {
        List<UserDAO> usersInRange = new ArrayList<>();
        for (UserDAO user : userMap.values()) {
            LocalDate userBirthDate = user.getBirthDate();
            if (userBirthDate != null && !userBirthDate.isBefore(fromDate) && !userBirthDate.isAfter(toDate)) {
                usersInRange.add(user);
            }
        }
        return usersInRange;
    }

}

