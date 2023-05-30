package com.mytrip.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.FlightRepositoriy;
import com.mytrip.modal.FlightA;

@Service
public class FlightService {
	@Autowired
	private FlightRepositoriy flightRepositoriy;
	
	public FlightA saveFlight(FlightA flightA ) {
		
		try {
			
			String s1 = flightA.getDeparture_time();
			String s2 = flightA.getDestination_time();
			SimpleDateFormat ss = new SimpleDateFormat("hh:mm");
			Date de = ss.parse(s1);
			Date ds = ss.parse(s2);

			long diff = ds.getTime() - de.getTime();

			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			String hours = String.valueOf(diffHours);
			String min = String.valueOf(diffMinutes);
			flightA.setTotal_time(hours + "h:" + min + "m");
			
			
		}catch(Exception e) {
			
		}
		
		
		return this.flightRepositoriy.save(flightA);
		
		
	}
	
	public List<FlightA> getAllDetailFlight() {
		
		
		
		List<FlightA> detail = this.flightRepositoriy.findAll();
		
		return detail;
	}
	
	public FlightA getFlightById(int id) {
		Optional<FlightA> findById = this.flightRepositoriy.findById(id);
		   FlightA flightA = findById.get();
		   return flightA;
	}
	
	public void deletFlight(int fid) {
		
		
		this.flightRepositoriy.deleteById(fid);
	}
	
	public long countFlight() {
		
		return this.flightRepositoriy.count();
	}

}
