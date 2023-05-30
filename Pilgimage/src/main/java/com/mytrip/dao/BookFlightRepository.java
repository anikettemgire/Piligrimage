package com.mytrip.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.BookFlight;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.User;

public interface BookFlightRepository extends JpaRepository<BookFlight, Integer> {

	public BookFlight findByOrderId(String orderId); 
	
	public List<BookFlight> findByStatus(String str);
	public long countByStatus(String stu);
	
	public List<BookFlight> findByUserAndStatus(User user,String stu);
}
