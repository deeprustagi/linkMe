package com.linkMe.service;

import java.util.List;
import com.linkMe.models.File;

public interface FileService {

  File createFile(File file, String userName);

  List<File> findAllByUserName(String userName);

  String findById(String userName, Integer id) throws Exception;

  void shareFileById(Integer id) throws Exception;
  
}
