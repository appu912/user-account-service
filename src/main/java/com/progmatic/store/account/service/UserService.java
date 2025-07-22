package com.progmatic.store.account.service;

import java.util.List;

import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.exception.UserAPIException;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserByEmailId(String emailId) throws UserAPIException;

    UserDTO createUser(UserDTO userDTO) throws UserAPIException;

    UserDTO updateUser(String emailId, UserDTO userDTO) throws UserAPIException;
}
