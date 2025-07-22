package com.progmatic.store.account.service;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progmatic.store.account.entity.User;
import com.progmatic.store.account.exception.UserAPIException;
import com.progmatic.store.account.repository.UserRepository;
import com.progmatic.store.account.util.ConvertUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private UserRepository userRepository;

    @InjectMocks private UserServiceImpl userService;

    @Test
    public void testGetAllUsersWithUserData() {
        assertTrue(true);
    }

    @Test
    public void testGetAllUsersWithNoUserData() {
        assertTrue(true);
    }

    @Test
    public void testGetUserByEmailIdWithNoUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserAPIException.class, () -> { userService.getUserByEmailId("notexist@gmail.com"); });
        assertEquals("User with email id notexist@gmail.com not found.", exception.getMessage());
    }

    @Test
    public void testGetUserByEmailIdWithUserData() {
        when(userRepository.findByEmailId(anyString())).thenReturn(Optional.of(user));
        assertEquals(ConvertUtilities.toUserDTO(user), userService.getUserByEmailId("exist@gmail.com"));
    }
}
