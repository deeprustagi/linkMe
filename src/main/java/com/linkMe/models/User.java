package com.linkMe.models;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String firstName;

	private String lastName;

	@Column(nullable = false, unique = true)
	private String userName;
	
	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<File> files = new ArrayList<>();
	
	public User() {
		
	}

	public User(String firstName, String lastName, String userName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public Integer getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addFile(File file) {
		files.add(file);
		file.setUser(this);
	}

	public void removeFile(File file) {
			files.remove(file);
			file.setUser(null);
	}
	
}