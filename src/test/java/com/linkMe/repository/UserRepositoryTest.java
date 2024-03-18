package com.linkMe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.linkMe.AbstractTestClass;
import com.linkMe.models.User;

@SpringBootTest
public class UserRepositoryTest extends AbstractTestClass {

  @Autowired
  UserRepository userRepository;

  @Test
  void testFindByUserName() {
    User user = getSampleUser();
    User testUser = userRepository.findByUserName(user.getUserName());
    assertEquals(user.getId(), testUser.getId());
  }
}