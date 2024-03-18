package com.linkMe.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.linkMe.models.File;
import com.linkMe.repository.FileRepository;
import com.linkMe.repository.UserRepository;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest extends AbstractTestClass {

  @Mock
  private UserRepository userRepository;

  @Mock
  private FileRepository fileRepository;

  private FileServiceImpl fileService;

  @BeforeEach
  void setUp() {
      this.fileService = new FileServiceImpl(this.fileRepository, this.userRepository);
  }

  @Test
  void testCreateFile() {
    File file = getSampleFile();
    User user = getSampleUser();
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
    Mockito.when(fileRepository.save(any())).thenReturn(file);
    File testFile = fileService.createFile(file, user.getUserName());
    assertEquals(file.getFileName(), testFile.getFileName());
  }

  @Test
  void testFindAllByUserName() {
    File file = getSampleFile();
    List<File> listFiles = new ArrayList<>();
    listFiles.add(file);
    User user = getSampleUser();
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
    Mockito.when(fileRepository.findByUserId(user.getId())).thenReturn(listFiles);
    List<File> testListFiles = fileService.findAllByUserName(user.getUserName());
    assertEquals(listFiles.get(0).getFileName(), testListFiles.get(0).getFileName());
  }

  @Test
  void testFindById_Sharable() throws Exception {
    File file = getSampleFile();
    file.setShare(true);
    Optional<File> optionalFile = Optional.of(file);
    User user = getSampleUser();
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
    Mockito.when(fileRepository.findById(any())).thenReturn(optionalFile);
    String content = fileService.findById(user.getUserName(), sampleInteger);
    assertNotNull(content);
  }

  @Test
  void testFindById_Self() throws Exception {
    File file = getSampleFile();
    file.setShare(false);
    User user = getSampleUser();
    file.setUser(user);
    Optional<File> optionalFile = Optional.of(file);
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
    Mockito.when(fileRepository.findById(any())).thenReturn(optionalFile);
    String content = fileService.findById(user.getUserName(), sampleInteger);
    assertNotNull(content);
  }

  @Test
  void testFindById_Empty() {
    Mockito.when(fileRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(Exception.class, () -> {fileService.findById(sampleString, sampleInteger);});
  }

  @Test
  void testFindById_IncorrectId() throws Exception {
    File file = getSampleFile();
    file.setShare(false);
    User user = getSampleUser();
    file.setUser(user);
    Optional<File> optionalFile = Optional.of(file);
    User incorrectUser = getSampleUser();
    incorrectUser.setId(-1);
    Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(incorrectUser);
    Mockito.when(fileRepository.findById(any())).thenReturn(optionalFile);
    assertThrows(Exception.class, () -> {fileService.findById(user.getUserName(), sampleInteger);});
  }

  @Test
  void testShareFileById_Sharable() {
    File file = getSampleFile();
    file.setShare(true);
    Optional<File> optionalFile = Optional.of(file);
    Mockito.when(fileRepository.findById(any())).thenReturn(optionalFile);
    Mockito.when(fileRepository.save(any())).thenReturn(file);
    assertDoesNotThrow(() -> {fileService.shareFileById(sampleInteger);});
  }

  @Test
  void testShareFileById_NotSharable() {
    File file = getSampleFile();
    file.setShare(false);
    Optional<File> optionalFile = Optional.of(file);
    Mockito.when(fileRepository.findById(any())).thenReturn(optionalFile);
    Mockito.when(fileRepository.save(any())).thenReturn(file);
    assertDoesNotThrow(() -> {fileService.shareFileById(sampleInteger);});
  }

  @Test
  void testShareFileById_Empty() {
    Mockito.when(fileRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(Exception.class, () -> {fileService.shareFileById(1);});
  }
}
