package com.progmatic.store.account.entity;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    @NotBlank(message = "Email should not be empty.")
    @Email(message = "Enter a valid email address.")
    private String emailId;
    @NotBlank(message = "Password should not be empty.")
    @Size(min = 6, message = "Create a password at least 6 characters long.")
    private String password;
    @NotBlank(message = "Username should not be empty.")
    private String username;

    private String fullName;
}
