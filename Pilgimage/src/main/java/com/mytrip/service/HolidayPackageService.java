package com.mytrip.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.HolidayPackageRepository;
import com.mytrip.modal.HolidayPackage;

@Service
public class HolidayPackageService {
	
	@Autowired
	private HolidayPackageRepository holidayPackageRepository;
	public HolidayPackage addPacakge(HolidayPackage holidayPackage) {
		
		
		return this.holidayPackageRepository.save(holidayPackage);
		
		
	}
	
	public List<HolidayPackage> getAllDeatilPackage() {
		
		List<HolidayPackage> alldeatil= this.holidayPackageRepository.findAll();
		
		
		return alldeatil;
	}

	public HolidayPackage getPacakge(int id) {
		Optional<HolidayPackage> findById = this.holidayPackageRepository.findById(id);
		                HolidayPackage holidayPackage = findById.get();
		                return holidayPackage;
	}
	
	public long getCountPackage() {
		return  this.holidayPackageRepository.count();
		
	}
	

	
	public void deletePackage(int pid) {
		
		
		this.holidayPackageRepository.deleteById(pid);
	}

}
