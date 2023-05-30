package com.mytrip.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.TrainRepositoriy;
import com.mytrip.modal.TrainAd;

@Service
public class TrainService {

	@Autowired
	private TrainRepositoriy trainRepositoriy;

	public TrainAd saveTrain(TrainAd trainAd) {
		
		try {
			String s1 = trainAd.getDeparture_time();
			String s2 = trainAd.getDestination_time();
			SimpleDateFormat ss = new SimpleDateFormat("hh:mm");
			Date de = ss.parse(s1);
			Date ds = ss.parse(s2);

			long diff = Math.abs(ds.getTime() - de.getTime());

			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			String hours = String.valueOf(diffHours);
			String min = String.valueOf(diffMinutes);
			trainAd.setTotal_time(hours + "h:" + min + "m");
			
			
		}catch(Exception e) {
			
		}



		return this.trainRepositoriy.save(trainAd);
	}

	public List<TrainAd> getAllDataOftarin() {

		List<TrainAd> allBus =  this.trainRepositoriy.findAll();

		return allBus;
	}
	
	public TrainAd getTrainById(int id) {
		
		Optional<TrainAd> findById = this.trainRepositoriy.findById(id);
		TrainAd trainAd = findById.get();
		return trainAd;
	}
	
	
	
	public long getCountTrain() {
		return  this.trainRepositoriy.count();
		
	}
	public void deleteTrain(int id) {
		
		this.trainRepositoriy.deleteById(id);
	}

}
