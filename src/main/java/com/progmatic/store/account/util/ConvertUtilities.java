package com.progmatic.store.account.util;

import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.entity.User;

public class ConvertUtilities {

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setPassword(user.getPassword());
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        return user;
    }

}
