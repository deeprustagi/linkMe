package com.linkMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.linkMe.models.File;

public interface FileRepository extends JpaRepository<File, Integer> {

  @Query("select f from File f where f.user.id = ?1")
	public List<File> findByUserId(Integer id);
  
}
