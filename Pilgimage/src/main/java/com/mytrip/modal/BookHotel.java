package com.mytrip.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class BookHotel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int book_hotel_id;
	private int no_of_person;

	private String date;
	private double booking_price;
	private String status;
	private String orderId;
	
	@ManyToOne
	private User user;
	@ManyToOne
	private HotelA hotelA;
	public int getBook_hotel_id() {
		return book_hotel_id;
	}
	public void setBook_hotel_id(int book_hotel_id) {
		this.book_hotel_id = book_hotel_id;
	}
	public int getNo_of_person() {
		return no_of_person;
	}
	public void setNo_of_person(int no_of_person) {
		this.no_of_person = no_of_person;
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
	public HotelA getHotelA() {
		return hotelA;
	}
	public void setHotelA(HotelA hotelA) {
		this.hotelA = hotelA;
	}

}
