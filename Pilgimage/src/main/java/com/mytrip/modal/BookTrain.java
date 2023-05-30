package com.mytrip.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class BookTrain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int book_train_id;
	private int no_of_seats;

	private String date;
	private double booking_price;
	private String status;
	
	private String orderId;
	
	@ManyToOne
	private User user;
	@ManyToOne
	private TrainAd trainAd;
	public int getBook_train_id() {
		return book_train_id;
	}
	public void setBook_train_id(int book_train_id) {
		this.book_train_id = book_train_id;
	}
	public int getNo_of_seats() {
		return no_of_seats;
	}
	public void setNo_of_seats(int no_of_seats) {
		this.no_of_seats = no_of_seats;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getBooking_price() {
		return booking_price;
	}
	public void setBooking_price(double booking_price) {
		this.booking_price = booking_price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public TrainAd getTrainAd() {
		return trainAd;
	}
	public void setTrainAd(TrainAd trainAd) {
		this.trainAd = trainAd;
	}
	

}
