package com.mytrip.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.BusRespositoriy;
import com.mytrip.modal.BusA;

@Service
public class BusService {
	@Autowired
	private BusRespositoriy busRespositoriy;

	public BusA saveBus(BusA busA) {
		
		  try {
		  
		  String s1 = busA.getDeparture_time(); 
		  String s2 = busA.getDestination_time();
		  SimpleDateFormat ss = new SimpleDateFormat("hh:mm"); Date de = ss.parse(s1);
		  Date ds = ss.parse(s2);
		  
		  long diff = Math.abs(ds.getTime() - de.getTime());
		  
		  long diffMinutes = diff / (60 * 1000) % 60; long diffHours = diff / (60 * 60
		  * 1000) % 24; String hours = String.valueOf(diffHours); String min =
		  String.valueOf(diffMinutes); busA.setTotal_time(hours + "h:" + min + "m");
		  
		  
		  }catch(Exception e) {
		  
		  }
		 
		return this.busRespositoriy.save(busA);
	}

	public List<BusA> getAllDataOfBus() {

		List<BusA> allBus = this.busRespositoriy.findAll();

		return allBus;
	}
	
	public void deleteBus(int bus_id) {
		
		
		 this.busRespositoriy.deleteById(bus_id);
	}
	
	public BusA getBusById(int id) {
		
		 Optional<BusA> findById = this.busRespositoriy.findById(id);
		return findById.get();
		
	}
	
	public long getBusCount() {
		long count = this.busRespositoriy.count();
		return count;
	}

}
