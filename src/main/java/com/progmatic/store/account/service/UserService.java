package com.progmatic.store.account.service;

import java.util.List;

import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.exception.UserAlreadyExistsException;
import com.progmatic.store.account.exception.UserNotFoundException;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserByEmailId(String emailId) throws UserNotFoundException;

    UserDTO createUser(UserDTO userDTO) throws UserAlreadyExistsException;

    UserDTO updateUser(String emailId, UserDTO userDTO) throws UserNotFoundException;

    UserDTO deleteUser(String emailId) throws UserNotFoundException;
}
