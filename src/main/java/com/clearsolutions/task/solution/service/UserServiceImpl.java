package com.clearsolutions.task.solution.service;

import com.clearsolutions.task.solution.data.UserRequest;
import com.clearsolutions.task.solution.model.UserDAO;
import com.clearsolutions.task.solution.repository.UserRepository;
import com.clearsolutions.task.solution.util.exception.BadRequestException;
import com.clearsolutions.task.solution.util.exception.ConflictException;
import com.clearsolutions.task.solution.util.exception.ForbiddenException;
import com.clearsolutions.task.solution.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${user.age}")
    private int age;
    private final UserRepository userRepository;

    @Override
    public UserDAO createUser(UserRequest request) {
        userBirthDay(request);
        checkByEmail(request.getEmail());

        UserDAO newUser = new UserDAO();
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setBirthDate(request.getBirthDate());
        newUser.setAddress(request.getAddress());
        newUser.setPhone(request.getPhone());

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public UserDAO updateUser(UserRequest updatedUser) {
        String email = updatedUser.getEmail();
        UserDAO currentUser = userRepository.findByEmail(email);
        Optional.ofNullable(updatedUser.getFirstName())
                .filter(firstName -> !firstName.isEmpty())
                .ifPresent(currentUser::setFirstName);
        Optional.ofNullable(updatedUser.getLastName())
                .filter(lastName -> !lastName.isEmpty())
                .ifPresent(currentUser::setLastName);
        Optional.ofNullable(updatedUser.getBirthDate()).ifPresent(currentUser::setBirthDate);
        Optional.ofNullable(updatedUser.getAddress())
                .filter(address -> !address.isEmpty())
                .ifPresent(currentUser::setAddress);
        Optional.ofNullable(updatedUser.getPhone())
                .filter(phone -> !phone.isEmpty())
                .ifPresent(currentUser::setPhone);

        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.findByEmail(email);
        userRepository.deleteUserByEmail(email);
    }

    @Override
    public List<UserDAO> searchUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new BadRequestException("The from date must be before the to date");
        }
        return userRepository.findByBirthDateBetween(fromDate, toDate);
    }

    private void userBirthDay(UserRequest request) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthDay = request.getBirthDate();
        if (Period.between(userBirthDay, currentDate).getYears() < age) {
            throw new ForbiddenException("Access denied. User must be at least " + age + " years old.");
        }
    }

    private void checkByEmail(String email) {
        try {
            userRepository.findByEmail(email);
            throw new ConflictException("User with this email " + email + " already exists");
        } catch (NotFoundException ignored) {
        }
    }
}
