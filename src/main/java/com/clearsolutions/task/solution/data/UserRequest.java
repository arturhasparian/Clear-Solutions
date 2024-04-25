package com.clearsolutions.task.solution.data;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
    message = "Email is not valid")
    private String email;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Past(message = "Birth date must be in the past")
    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;
    private String address;
    private String phone;
}
