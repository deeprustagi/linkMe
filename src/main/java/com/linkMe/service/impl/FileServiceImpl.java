package com.linkMe.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkMe.models.File;
import com.linkMe.models.User;
import com.linkMe.repository.FileRepository;
import com.linkMe.repository.UserRepository;
import com.linkMe.service.FileService;

@Service
public class FileServiceImpl implements FileService {

  @Autowired
  FileRepository fileRepository;

  @Autowired
  UserRepository userRepository;

  @Override
  public File createFile(File file, String userName) {
    File newFile = new File(file.getFileName(), file.getContent());
    User user = userRepository.findByUserName(userName);
    newFile.setUser(user);
    return fileRepository.save(newFile);
  }

  @Override
  public List<File> findAllByUserName(String userName) {
    User user = userRepository.findByUserName(userName);
    return fileRepository.findByUserId(user.getId());
  }

  @Override
  public String findById(String userName, Integer id) throws Exception {
    Optional<File> optionalFile = fileRepository.findById(id);
		if(optionalFile.isPresent()) {
			File file = optionalFile.get();
      User user = userRepository.findByUserName(userName);
      if (file.isShare() == true || file.getUser().getId() == user.getId()) {
        byte[] content = file.getContent();
        return Base64.getEncoder().encodeToString(content);
      }
		}
		throw new Exception("File not exist with fileId: " + id);
  }

  @Override
  public void shareFileById(Integer id) throws Exception {
    Optional<File> optionalFile = fileRepository.findById(id);
		if(optionalFile.isPresent()) {
			File file = optionalFile.get();
      if (file.isShare() == true) {
        file.setShare(false);
      } else {
        file.setShare(true);
      }
      fileRepository.save(file);
      return;
		}
		throw new Exception("File not exist with fileId: " + id);
  }
}