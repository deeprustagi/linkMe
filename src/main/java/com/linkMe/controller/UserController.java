package com.linkMe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkMe.models.User;
import com.linkMe.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users/register")
	public User createUser(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@PostMapping("/users/login")
	public User loginUser(@RequestBody User user) throws Exception {
		return userService.loginUser(user);
	}
}