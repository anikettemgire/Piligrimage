package com.mytrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.UserRepositray;
import com.mytrip.modal.User;

@Service
public class Userservice {
	@Autowired
	private UserRepositray userRepositray;
	public User userSave(User user) {
		User result = this.userRepositray.save(user);
		return result;
	}
	
	public User getUser(String name) {
		User user = this.userRepositray.getUserByName(name);
		return user;
	}
	
	public long getCountUser() {
		return this.userRepositray.count();
	}

}
