package com.mytrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.BookFlightRepository;
import com.mytrip.modal.BookFlight;
import com.mytrip.modal.User;

@Service
public class FlightBookService {
	
	@Autowired
	private BookFlightRepository bookFlightRepository;
	public BookFlight bookFlight(BookFlight bookFlight) {
		
		
		return this.bookFlightRepository.save(bookFlight);
		
		
		
	}
	
	
	public BookFlight findByOrderId(String oid) {
		BookFlight findByOrderId = this.bookFlightRepository.findByOrderId(oid);
		return findByOrderId;
	}
	
	public List<BookFlight> getBookingFligth() {
		
		return this.bookFlightRepository.findByStatus("paid");
	}
	
	public long getCountByPaid() {
		return this.bookFlightRepository.countByStatus("paid");
	}
	
public List<BookFlight> getFlightBookingByUser(User user) {
		
		return this.bookFlightRepository.findByUserAndStatus(user,"paid");
		
	}

    public void cancleFlight(int id) {
    	this.bookFlightRepository.deleteById(id);
    }
	
	}


