package com.mytrip.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.BookTrain;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.User;

public interface BookTrainRepository  extends JpaRepository<BookTrain, Integer>{
	
	
	public BookTrain findByOrderId(String orderId); 
	
	public List<BookTrain> findByStatus(String str);
	public long countByStatus(String stu);
	public List<BookTrain> findByUserAndStatus(User user,String stu);

}
