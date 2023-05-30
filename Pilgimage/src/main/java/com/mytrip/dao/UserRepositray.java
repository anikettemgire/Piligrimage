package com.mytrip.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mytrip.modal.User;
public interface UserRepositray extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.email=:email")
	public User getUserByName(@Param("email")String email);

}
