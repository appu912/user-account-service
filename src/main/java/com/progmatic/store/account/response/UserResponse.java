package com.progmatic.store.account.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.progmatic.store.account.dto.UserDTO;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends CommonResponse {
    private UserDTO payload;
}
