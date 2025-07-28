package com.progmatic.store.account.dto;

import java.math.BigInteger;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private BigInteger id;
    @NotBlank(message = "This field is required.")
    @Email(message = "Enter a valid email address.")
    private String emailId;
    @NotBlank(message = "This field is required.")
    @Size(min = 6, message = "Create a password at least 6 characters long.")
    private String password;
    @NotBlank(message = "This field is required.")
    // // This username isn't available. Please try another.
    private String username;

    private String fullName;
}
