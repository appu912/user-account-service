package com.progmatic.store.account.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progmatic.store.account.constants.UserConstants;
import com.progmatic.store.account.dto.UserDTO;
import com.progmatic.store.account.entity.User;
import com.progmatic.store.account.exception.UserNotFoundException;
import com.progmatic.store.account.repository.UserRepository;
import com.progmatic.store.account.util.ConvertUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private User user;

    @Mock
    private UserDTO userDTO;

    @Mock
    private UserRepository userRepository;

    @InjectMocks private UserServiceImpl userService;

    @Test
    public void testGetAllUsersWithNoUserData() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetAllUsersWithUserData() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertFalse(userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetUserByEmailIdWithNoUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> { userService.getUserByEmailId("notexist@gmail.com"); });
        assertEquals(String.format(UserConstants.USER_NOT_FOUND, "notexist@gmail.com"), exception.getMessage());
    }

    @Test
    public void testGetUserByEmailIdWithUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        assertEquals(ConvertUtilities.toUserDTO(user), userService.getUserByEmailId("exist@gmail.com"));
    }

//    @Test
//    public void testCreateUserWithNoUserData() {
//        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
//        when(ConvertUtilities.toUserDTO(user)).thenReturn(userDTO);
//        assertEquals(userDTO, userService.createUser(userDTO));
//    }

    @Test
    public void testDeleteUserWithNoUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> { userService.deleteUser("notexist@gmail.com"); });
        assertEquals(String.format(UserConstants.USER_NOT_FOUND, "notexist@gmail.com"), exception.getMessage());
    }

    @Test
    public void testDeleteUserWithUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        assertEquals(ConvertUtilities.toUserDTO(user), userService.deleteUser("exist@gmail.com"));
    }
}
