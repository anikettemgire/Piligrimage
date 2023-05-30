package com.mytrip.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.HotelRepositoriy;
import com.mytrip.modal.HotelA;

@Service
public class HotelService {
	@Autowired
	private HotelRepositoriy hotelRepositoriy;
	public HotelA addHotel(HotelA hotelA) {
		
		HotelA result = this.hotelRepositoriy.save(hotelA);
		
		return result;
	}
	
	public List<HotelA> getAllDeatilHotel() {
		
		List<HotelA> hotellist = this.hotelRepositoriy.findAll();
		
		return hotellist;
	}
	
	public HotelA findById(int id) {
		
		Optional<HotelA> findById = this.hotelRepositoriy.findById(id);
		 HotelA hotelA = findById.get();
		 return hotelA;
		
	}
	
	public long getCountHotel() {
		return  this.hotelRepositoriy.count();
		
	}
	
	public void deletehotel(int id) {
		this.hotelRepositoriy.deleteById(id);
	}

}
