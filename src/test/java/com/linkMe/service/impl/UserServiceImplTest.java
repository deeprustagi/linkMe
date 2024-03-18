package com.linkMe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.linkMe.AbstractTestClass;
import com.linkMe.models.User;
import com.linkMe.repository.UserRepository;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest extends AbstractTestClass {

  @Mock
  private UserRepository userRepository;

  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
      this.userService = new UserServiceImpl(this.userRepository);
  }

  @Test
  void testFindUserByUserName() {
    User user = getSampleUser();
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
    User testUser = userService.findUserByUserName(user.getUserName());
    assertEquals(user.getId(), testUser.getId());
  }

  @Test
  void testLoginUser_Success() throws Exception {
    User user = getSampleUser();
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
    User testUser = userService.loginUser(user);
    assertEquals(user.getId(), testUser.getId());
  }

  @Test
  void testLoginUser_Null() {
    User user = getSampleUser();
    Mockito.when(userRepository.findByUserName(anyString())).thenReturn(null);
    assertThrows(Exception.class, () -> {userService.loginUser(user);});
  }

  @Test
  void testLoginUser_IncorrectPassword() {
    User user = getSampleUser();
    User incorrectUser = getSampleUser();
    incorrectUser.setPassword(sampleString);
    Mockito.when(userRepository.findByUserName(anyString())).thenReturn(incorrectUser);
    assertThrows(Exception.class, () -> {userService.loginUser(user);});
  }

  @Test
  void testRegisterUser() {
    User user = getSampleUser();
    Mockito.when(userRepository.save(any())).thenReturn(user);
    User testUser = userService.registerUser(user);
    assertEquals(user.getFirstName(), testUser.getFirstName());
  }
}
