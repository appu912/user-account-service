package com.progmatic.store.account.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import com.progmatic.store.account.dto.UserDTO;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserListResponse {
    private String message;
    private HttpStatus httpStatus;
    private List<UserDTO> payload;
}
