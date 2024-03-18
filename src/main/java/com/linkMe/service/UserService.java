package com.linkMe.service;

import com.linkMe.models.User;

public interface UserService {
	
	public User registerUser(User user);

	public User loginUser(User user) throws Exception;
	
	public User findUserByUserName(String userName) throws Exception;
}