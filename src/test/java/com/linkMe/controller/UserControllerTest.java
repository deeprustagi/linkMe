package com.linkMe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.linkMe.AbstractTestClass;
import com.linkMe.models.User;
import com.linkMe.service.UserService;

public class UserControllerTest extends AbstractTestClass {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = getSampleUser();
        when(userService.registerUser(user)).thenReturn(user);
        User result = userController.createUser(user);
        assertEquals(user, result);
        verify(userService, times(1)).registerUser(user);
    }

    @Test
    public void testLoginUser() throws Exception {
        User user = getSampleUser();
        when(userService.loginUser(user)).thenReturn(user);
        User result = userController.loginUser(user);
        assertEquals(user, result);
        verify(userService, times(1)).loginUser(user);
    }

    @Test
    public void testLoginUser_Exception() throws Exception {
        User user = getSampleUser();
        when(userService.loginUser(user)).thenThrow(new Exception());
        assertThrows(Exception.class, () -> {userController.loginUser(user);});
        verify(userService, times(1)).loginUser(user);
    }
}
