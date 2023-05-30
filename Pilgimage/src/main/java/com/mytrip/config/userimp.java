package com.mytrip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mytrip.dao.UserRepositray;
import com.mytrip.modal.User;

public class userimp implements UserDetailsService {
	@Autowired
	private UserRepositray userrepositray;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userrepositray.getUserByName(username);
		if (user == null) {

			throw new UsernameNotFoundException("Coloud Not Found User");
		}
		userDet userDetail = new userDet(user);
		return userDetail;
	}

}
