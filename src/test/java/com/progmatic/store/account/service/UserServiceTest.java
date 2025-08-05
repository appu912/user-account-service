package com.progmatic.store.account.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progmatic.store.account.constants.UserConstants;
import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.entity.User;
import com.progmatic.store.account.exception.UserAlreadyExistsException;
import com.progmatic.store.account.exception.UserNotFoundException;
import com.progmatic.store.account.repository.UserRepository;
import com.progmatic.store.account.util.ConvertUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user;

    @Mock
    private UserRepository userRepository;

    @InjectMocks private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(BigInteger.ONE);
        user.setEmailId("user_email@gmail.com");
        user.setPassword("user_password");
        user.setUsername("user_username");
        user.setFullName("User");
    }

    @Test
    public void testGetAllUsers_withNoUserData() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetAllUsers_withUserData() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertFalse(userService.getAllUsers().isEmpty());
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    public void testGetUserByEmailId_withNoUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> { userService.getUserByEmailId("notexist@gmail.com"); });
        assertEquals(String.format(UserConstants.USER_NOT_FOUND, "notexist@gmail.com"), exception.getMessage());
    }

    @Test
    public void testGetUserByEmailId_withUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        assertEquals(ConvertUtilities.toUserDTO(user), userService.getUserByEmailId("user_email@gmail.com"));
    }

    @Test
    public void testCreateUser_withUserData() {
        UserDTO inputPayload = ConvertUtilities.toUserDTO(user);
        inputPayload.setId(null);
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> { userService.createUser(inputPayload); });
        assertEquals(String.format(UserConstants.USER_ALREADY_EXISTS, "user_email@gmail.com"), exception.getMessage());
    }

    @Test
    public void testCreateUser_withNoUserData() {
        UserDTO inputPayload = ConvertUtilities.toUserDTO(user);
        inputPayload.setId(null);
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(ConvertUtilities.toUserDTO(user), userService.createUser(inputPayload));
    }

    @Test
    public void testUpdateUser_withNoUserData() {
        UserDTO inputPayload = ConvertUtilities.toUserDTO(user);
        inputPayload.setId(null);
        inputPayload.setPassword(null);
        inputPayload.setFullName(null);
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> { userService.updateUser("user_email@gmail.com", inputPayload); });
        assertEquals(String.format(UserConstants.USER_NOT_FOUND, "user_email@gmail.com"), exception.getMessage());
    }

    @Test
    public void testUpdateUser_withUserData() {
        UserDTO inputPayload = ConvertUtilities.toUserDTO(user);
        inputPayload.setEmailId("update_user@gmail.com");
        inputPayload.setUsername("update_username");
        User updatedUser = ConvertUtilities.toUser(inputPayload);
        inputPayload.setId(null);
        inputPayload.setPassword(null);
        inputPayload.setFullName("");
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        assertEquals(ConvertUtilities.toUserDTO(updatedUser), userService.updateUser("user_email@gmail.com", inputPayload));
    }

    @Test
    public void testDeleteUser_withNoUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> { userService.deleteUser("notexist@gmail.com"); });
        assertEquals(String.format(UserConstants.USER_NOT_FOUND, "notexist@gmail.com"), exception.getMessage());
    }

    @Test
    public void testDeleteUser_withUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        assertEquals(ConvertUtilities.toUserDTO(user), userService.deleteUser("user_email@gmail.com"));
    }
}
