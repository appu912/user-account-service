package com.progmatic.store.account.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private BigInteger id;
    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String userName;
}
