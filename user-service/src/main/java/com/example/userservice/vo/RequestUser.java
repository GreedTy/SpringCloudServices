package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not bo less than two characters")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not bo less than two characters")
    @Email
    private String name;

    @NotNull(message = "password cannot be null")
    @Size(min = 2, message = "password not bo less than two characters")
    @Email
    private String password;
}
