package com.mytrip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.HotelA;
public interface HotelRepositoriy extends JpaRepository<HotelA, Integer> {
	
	

}
