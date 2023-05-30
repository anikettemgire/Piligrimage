package com.mytrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.BookHotelepository;
import com.mytrip.modal.BookHotel;
import com.mytrip.modal.BookPackage;
import com.mytrip.modal.User;

@Service
public class HotelBookService {
	
	
	@Autowired
	private BookHotelepository bookHotelepository;
	public BookHotel hotelBook(BookHotel bookHotel) {
		
		return this.bookHotelepository.save(bookHotel);
		
	}

	
	public BookHotel FindByOrderId(String oid) {
		
		
		BookHotel findByOrderId = this.bookHotelepository.findByOrderId(oid);
		
		return findByOrderId;
	}
	
	public long getCountByPaid() {
		return this.bookHotelepository.countByStatus("paid");
	}
	
	public List<BookHotel> getBookigHotelDetail() {
		 return this.bookHotelepository.findByStatus("paid");
	}
	
	
public List<BookHotel> getHotekBookingByUser(User user) {
		
		return this.bookHotelepository.findByUserAndStatus(user,"paid");
		
	}


   public void  cancleHotel(int id) {
	   this.bookHotelepository.deleteById(id);
   }
}
