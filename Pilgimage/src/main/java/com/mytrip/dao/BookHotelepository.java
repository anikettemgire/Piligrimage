package com.mytrip.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mytrip.modal.BookHotel;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.User;

public interface BookHotelepository extends JpaRepository<BookHotel, Integer> {

	public BookHotel findByOrderId(String orderId);
	
	public List<BookHotel> findByStatus(String str);
	public long countByStatus(String stu);
	public List<BookHotel> findByUserAndStatus(User user,String stu);
}
