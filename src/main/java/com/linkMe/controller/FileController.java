package com.linkMe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkMe.models.File;
import com.linkMe.service.FileService;

@RestController
public class FileController {
  
  @Autowired
  FileService fileService;

  @PostMapping("/files/{username}")
  public File createFile(@RequestBody File file, @PathVariable String username) {
      return fileService.createFile(file, username);
  }

  @GetMapping("/files/{username}")
  public List<File> getFilesByUserName(@PathVariable String username) throws Exception {
      return fileService.findAllByUserName(username);
  }

  @GetMapping("/files/file/{username}/{id}")
  public ResponseEntity<String> getFileContentById(@PathVariable String username, @PathVariable Integer id) {
    try {
        String content = fileService.findById(username, id);
        return ResponseEntity.ok(content);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch text");
    }
  }

  @PostMapping("/files/share/{id}")
  public void shareFileById(@PathVariable Integer id) throws Exception {
    fileService.shareFileById(id);
  }
  
}