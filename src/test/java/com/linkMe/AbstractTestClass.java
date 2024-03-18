package com.linkMe;

import com.linkMe.models.File;
import com.linkMe.models.User;

public class AbstractTestClass {

  public static final String sampleString = "SAMPLE";
  public static final Integer sampleInteger = 1;
  
  public User getSampleUser() {
    User user = new User("Test", "Test", "Test","Test");
    user.setId(6);
    return user;
  }

  public File getSampleFile() {
    byte[] content = new byte[1];
    File file = new File("FileName", content);
    return file;
  }
}
