package com.progmatic.store.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.entity.User;
import com.progmatic.store.account.exception.UserAPIException;
import com.progmatic.store.account.repository.UserRepository;
import com.progmatic.store.account.util.ConvertUtilities;
import com.progmatic.store.account.util.StringUtilities;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        userRepository.findAll().forEach((user) -> {
            userDTOs.add(ConvertUtilities.toUserDTO(user));
        });
        return userDTOs;
    }

    @Override
    public UserDTO getUserByEmailId(String emailId) throws UserAPIException {
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new UserAPIException(String.format("User with email id %s not found.", emailId)));
        return ConvertUtilities.toUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws UserAPIException {
        String emailId = userDTO.getEmailId();
        if (userRepository.findByEmailId(emailId).isPresent()) {
            throw new UserAPIException(String.format("User with email id %s already present.", emailId));
        }
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(userDTO.getPassword());
        return ConvertUtilities.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(String emailId, UserDTO userDTO) throws UserAPIException {
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new UserAPIException(String.format("User with email id %s not found.", emailId)));
        updateHelper(user, userDTO);
        return ConvertUtilities.toUserDTO(userRepository.save(user));
    }

    private void updateHelper(User user, UserDTO payload) {
        if (StringUtilities.isNotNullOrEmpty(payload.getFirstName())) {
            user.setFirstName(payload.getFirstName());
        }
        if (StringUtilities.isNotNullOrEmpty(payload.getLastName())) {
            user.setLastName(payload.getLastName());
        }
        if (StringUtilities.isNotNullOrEmpty(payload.getUserName())) {
            user.setUserName(payload.getUserName());
        }
    }
}
