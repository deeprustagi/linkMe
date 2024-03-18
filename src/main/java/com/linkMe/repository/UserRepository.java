package com.linkMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkMe.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUserName(String userName);
}