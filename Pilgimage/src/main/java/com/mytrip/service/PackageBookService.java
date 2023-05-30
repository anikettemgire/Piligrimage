package com.mytrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.BookPackageRepository;
import com.mytrip.modal.BookPackage;
import com.mytrip.modal.User;

@Service
public class PackageBookService {
	
	@Autowired
	private BookPackageRepository bookPackageRepository;
	
	public BookPackage bookPackage(BookPackage bookPackage) {
		
		
		return this.bookPackageRepository.save(bookPackage);
		
		
		
	}
	
	public BookPackage FindByOrderId(String oid) {
		
	 return this.bookPackageRepository.findByOrderId(oid);
	}
	
	public long getCountByPaid() {
		return this.bookPackageRepository.countByStatus("paid");
	}
	
	public List<BookPackage> getBookingAllPacakge() {
		
		return this.bookPackageRepository.findByStatus("paid");
	}
public List<BookPackage> getHolidayBookingByUser(User user) {
		
		return this.bookPackageRepository.findByUserAndStatus(user,"paid");
		
	}

    public void cancleByPackage(int id) {
    	this.bookPackageRepository.deleteById(id);
    }
}
