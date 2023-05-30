package com.mytrip.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.Bookbus;
import com.mytrip.modal.User;

public interface BookBusRepository  extends JpaRepository<Bookbus, Integer>{
	
	public Bookbus findByOrderId(String orderId);
	

	
	public List<Bookbus> findByStatus(String stu);
	
	public long countByStatus(String stu);
 
	public List<Bookbus> findByUserAndStatus(User user,String stu);
}
