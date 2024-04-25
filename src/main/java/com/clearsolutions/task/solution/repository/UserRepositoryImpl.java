package com.clearsolutions.task.solution.repository;

import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.util.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, UserDAO> userMap = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1L);

    @Override
    public UserDAO save(UserDAO user) {
        if (user.getId() == null) {
            user.setId(idCounter.incrementAndGet());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public UserDAO findByEmail(String email) {
        return userMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found"));
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
        return userMap.values().stream()
                .filter(user -> {
                    LocalDate userBirthDate = user.getBirthDate();
                    return userBirthDate != null && !userBirthDate.isBefore(fromDate) && !userBirthDate.isAfter(toDate);
                }).toList();
    }

}

