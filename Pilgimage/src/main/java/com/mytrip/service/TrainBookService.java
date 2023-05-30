package com.mytrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytrip.dao.BookTrainRepository;
import com.mytrip.modal.BookTrain;
import com.mytrip.modal.User;

@Service
public class TrainBookService {
	
	@Autowired
	private BookTrainRepository trainRepository;
	
	public BookTrain bookBus(BookTrain bookTrain) {
		
		return this.trainRepository.save(bookTrain);
		
	}
	
	public BookTrain FindByOrderId(String oid) {
		
		return this.trainRepository.findByOrderId(oid);
	}

	public long getCountByPaid() {
		
		return this.trainRepository.countByStatus("paid");
	}
	
	public List<BookTrain> getAllDetailTrain() {
		
	 List<BookTrain> findByStatus = this.trainRepository.findByStatus("paid");
	
	 return findByStatus;
	}
	
public List<BookTrain> getTrainBookingByUser(User user) {
		
		return this.trainRepository.findByUserAndStatus(user,"paid");
		
	}

   public void cancleTrain(int id) {
	   this.trainRepository.deleteById(id);
   }
}
