package com.mytrip.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.BookBusRepository;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.User;
@Service
public class BusBookService {
	
	@Autowired
	private BookBusRepository bookBusRepository;
	
	public Bookbus saveBookingBus(Bookbus bookbus) {
		
		return this.bookBusRepository.save(bookbus);
		
	}
	
	public Bookbus  FindByBookBusById(int bid) {
		
		
		 Optional<Bookbus> findById = this.bookBusRepository.findById(bid);
		
		Bookbus bookbus = findById.get();
		return bookbus;
	}
	
	public Bookbus FindByOrderId(String oid) {
		Bookbus findByOrderId = this.bookBusRepository.findByOrderId(oid);
		
		return findByOrderId;
		
	}
	
	public List<Bookbus> getBookingBus() {
		
		List<Bookbus> bookbus = this.bookBusRepository.findByStatus("paid");
		return bookbus;
	}

	public long getBusCountByPaid() {
		
		  return this.bookBusRepository.countByStatus("paid");
	}
	
	public List<Bookbus> getBusBookingByUser(User user) {
		
		return this.bookBusRepository.findByUserAndStatus(user,"paid");
		
	}
	
	public void cancleBus(int id) {
		this.bookBusRepository.deleteById(id);
	}
}
