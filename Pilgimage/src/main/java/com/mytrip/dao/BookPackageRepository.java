package com.mytrip.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.BookPackage;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.User;

public interface BookPackageRepository extends JpaRepository<BookPackage, Integer>{
	public BookPackage findByOrderId(String orderId);
	
	public List<BookPackage> findByStatus(String str);
	public long countByStatus(String stu);
	public List<BookPackage> findByUserAndStatus(User user,String stu);
}
