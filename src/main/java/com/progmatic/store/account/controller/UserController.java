package com.progmatic.store.account.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.response.UserListResponse;
import com.progmatic.store.account.response.UserResponse;
import com.progmatic.store.account.service.UserService;

@Slf4j
@RestController
@RequestMapping(value = "/user-service/api/v0")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<UserListResponse> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        UserListResponse response = new UserListResponse();
        response.setPayload(userDTOs);
        response.setMessage(String.format("%d Users found.", userDTOs.size()));
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "userId") String userId) {
        UserResponse response = new UserResponse();
        try {
            log.info("Querying user ...");
            UserDTO userDTO = userService.getUserByEmailId(userId);
            response.setMessage("User found.");
            response.setHttpStatus(HttpStatus.OK);
            response.setPayload(userDTO);
            log.info("User found.");
        }
        catch (Exception exception) {
            log.error("Exception occurred: {}", exception.getMessage(), exception);
            response.setMessage("User not found.");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO userDTO) {
        UserResponse response = new UserResponse();
        try {
            log.info("Creating user ...");
            UserDTO createdUserDTO = userService.createUser(userDTO);
            response.setMessage("User created.");
            response.setHttpStatus(HttpStatus.CREATED);
            response.setPayload(createdUserDTO);
            log.info("User created.");
        }
        catch (Exception exception) {
            log.error("Exception occurred: {}", exception.getMessage(), exception);
            response.setMessage("User already exists.");
            response.setHttpStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping(value = "/user/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
        UserResponse response = new UserResponse();
        try {
            log.info("Updating user ...");
            UserDTO updatedUserDTO = userService.updateUser(userId, userDTO);
            response.setMessage("User updated.");
            response.setHttpStatus(HttpStatus.OK);
            response.setPayload(updatedUserDTO);
            log.info("User updated.");
        }
        catch (Exception exception) {
            log.error("Exception occurred: {}", exception.getMessage(), exception);
            response.setMessage("User not found.");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("userId") String userId) {
        UserResponse response = new UserResponse();
        try {
            log.info("Deleting user ...");
            UserDTO deletedUserDTO = userService.deleteUser(userId);
            response.setMessage("User deleted.");
            response.setHttpStatus(HttpStatus.OK);
            response.setPayload(deletedUserDTO);
            log.info("User deleted.");
        }
        catch (Exception exception) {
            log.error("Exception occurred: {}", exception.getMessage(), exception);
            response.setMessage("User not found.");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
