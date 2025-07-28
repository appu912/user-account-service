package com.progmatic.store.account.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.exception.UserAPIException;
import com.progmatic.store.account.response.UserListResponse;
import com.progmatic.store.account.response.UserResponse;
import com.progmatic.store.account.service.UserService;

@Slf4j
@RestController
@RequestMapping(value = "/user-service/api/v0")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<UserListResponse> getAllUsers() {
        log.info("Fetching users ...");
        List<UserDTO> userDTOs = userService.getAllUsers();
        UserListResponse response = new UserListResponse();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setPayload(userDTOs);
        response.setMessage(String.format("%d users found.", userDTOs.size()));
        response.setTimestamp(LocalDateTime.now());
        log.info("Users fetched successfully.");;
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/user/{emailId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "emailId") @Email(message = "Enter a valid email address.") String emailId) throws UserAPIException {
        UserResponse response = new UserResponse();
        log.info("Fetching user ...");
        UserDTO userDTO = userService.getUserByEmailId(emailId);
        response.setMessage("User found.");
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setTimestamp(LocalDateTime.now());
        response.setPayload(userDTO);
        log.info("User fetched successfully.");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserDTO userDTO) throws UserAPIException {
        UserResponse response = new UserResponse();
        log.info("Creating user ...");
        UserDTO createdUserDTO = userService.createUser(userDTO);
        response.setMessage("User created.");
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setTimestamp(LocalDateTime.now());
        response.setPayload(createdUserDTO);
        log.info("User created successfully.");
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

//    @PutMapping(value = "/user/{userId}")
//    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
//        UserResponse response = new UserResponse();
//        try {
//            log.info("Updating user ...");
//            UserDTO updatedUserDTO = userService.updateUser(userId, userDTO);
//            response.setMessage("User updated.");
//            response.setHttpStatus(HttpStatus.OK);
//            response.setPayload(updatedUserDTO);
//            log.info("User updated.");
//        }
//        catch (Exception exception) {
//            log.error("Exception occurred: {}", exception.getMessage(), exception);
//            response.setMessage("User not found.");
//            response.setHttpStatus(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(response, response.getHttpStatus());
//    }
//
    @DeleteMapping(value = "/user/{emailId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("emailId") @Email(message = "Enter a valid email address.") String emailId) throws UserAPIException {
        UserResponse response = new UserResponse();
        log.info("Deleting user ...");
        UserDTO deletedUserDTO = userService.deleteUser(emailId);
        response.setMessage("User deleted.");
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setTimestamp(LocalDateTime.now());
        response.setPayload(deletedUserDTO);
        log.info("User deleted successfully.");
        return ResponseEntity.ok().body(response);
    }
}
