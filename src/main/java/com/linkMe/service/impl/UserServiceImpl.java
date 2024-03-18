package com.linkMe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkMe.models.User;
import com.linkMe.repository.UserRepository;
import com.linkMe.service.UserService;;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		return userRepository.save(
			new User(
					user.getFirstName(), 
					user.getLastName(), 
					user.getUserName(),
					user.getPassword()
				)
			);
	}

	@Override
	public User loginUser(User user) throws Exception {
		User searchedUser = findUserByUserName(user.getUserName());
		if (searchedUser != null && searchedUser.getPassword().equals(user.getPassword())) {
				return searchedUser;
		}
		throw new Exception("User not exist with username: " + user.getUserName());
	}

	@Override
	public User findUserByUserName(String userName) throws Exception {
		return userRepository.findByUserName(userName);
	}

}