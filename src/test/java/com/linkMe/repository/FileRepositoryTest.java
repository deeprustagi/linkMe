package com.linkMe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.linkMe.AbstractTestClass;
import com.linkMe.models.File;
import com.linkMe.models.User;

@SpringBootTest
public class FileRepositoryTest extends AbstractTestClass {

  @Autowired
  private FileRepository fileRepository;

  @Test
  void testFindByUserId() {
    User user = getSampleUser();
    File file = getSampleFile();
    file.setUser(user);
    File savedfile = fileRepository.save(file);
    List<File> testFiles = fileRepository.findByUserId(user.getId());
    assertEquals(savedfile.getUser().getId(), testFiles.get(0).getUser().getId());
  }
}