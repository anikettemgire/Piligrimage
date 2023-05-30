package com.mytrip.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bookbus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int book_bus_id;
	private int no_of_seats;
	private String adress;
	private String date;
	private double booking_price;
	private String status;
	
	private String orderId;
	@ManyToOne
	private User user;
	@ManyToOne
	private BusA busA;
	public int getBook_bus_id() {
		return book_bus_id;
	}
	public void setBook_bus_id(int book_bus_id) {
		this.book_bus_id = book_bus_id;
	}
	public int getNo_of_seats() {
		return no_of_seats;
	}
	public void setNo_of_seats(int no_of_seats) {
		this.no_of_seats = no_of_seats;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
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
	public BusA getBusA() {
		return busA;
	}
	public void setBusA(BusA busA) {
		this.busA = busA;
	}


}
