package com.progmatic.store.account.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progmatic.store.account.constants.UserConstants;
import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.entity.User;
import com.progmatic.store.account.exception.UserAlreadyExistsException;
import com.progmatic.store.account.exception.UserNotFoundException;
import com.progmatic.store.account.repository.UserRepository;
import com.progmatic.store.account.util.ConvertUtilities;
import com.progmatic.store.account.util.StringUtilities;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Fetching users from database ...");
        List<UserDTO> userDTOs = new ArrayList<>();
        userRepository.findAll().forEach((user) -> userDTOs.add(ConvertUtilities.toUserDTO(user)));
        log.info("Users fetched from database.");
        return userDTOs;
    }

    @Override
    public UserDTO getUserByEmailId(String emailId) throws UserNotFoundException {
        log.info("Fetching user from database ...");
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new UserNotFoundException(String.format(UserConstants.USER_NOT_FOUND, emailId)));
        log.info("User fetched from database.");
        return ConvertUtilities.toUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws UserAlreadyExistsException {
        log.info("Creating user in database ...");
        String emailId = userDTO.getEmailId();
        if (userRepository.findByEmailId(emailId).isPresent()) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, emailId));
        }
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        log.info("User created in database.");
        return ConvertUtilities.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(String emailId, UserDTO userDTO) throws UserNotFoundException {
        log.info("Updating user in database ...");
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new UserNotFoundException(String.format(UserConstants.USER_NOT_FOUND, emailId)));
        updateHelper(user, userDTO);
        log.info("User updated in database.");
        return ConvertUtilities.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO deleteUser(String emailId) throws UserNotFoundException {
        log.info("Deleting user from database ...");
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new UserNotFoundException(String.format(UserConstants.USER_NOT_FOUND, emailId)));
        UserDTO userDTO = ConvertUtilities.toUserDTO(user);
        userRepository.delete(user);
        log.info("User deleted from database.");
        return userDTO;
    }

    private void updateHelper(User user, UserDTO payload) {
        if (StringUtilities.isNotNullOrEmpty(payload.getEmailId())) {
            user.setEmailId(payload.getEmailId());
        }
        if (StringUtilities.isNotNullOrEmpty(payload.getPassword())) {
            user.setPassword(payload.getPassword());
        }
        if (StringUtilities.isNotNullOrEmpty(payload.getFullName())) {
            user.setFullName(payload.getFullName());
        }
        if (StringUtilities.isNotNullOrEmpty(payload.getUsername())) {
            user.setUsername(payload.getUsername());
        }
    }
}
