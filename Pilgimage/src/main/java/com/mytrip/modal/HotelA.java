package com.mytrip.modal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity

public class HotelA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hid;
	private String hotel_name;
	private String hotel_location;
	private int no_of_person;
	private double price;
	private String type;
	private String noofroom;
	private String hotel_descrption;
	
	private String hotel_img_url;
	@ManyToMany(mappedBy = "hotelA",cascade = CascadeType.ALL)
	private List<HolidayPackage> holidayPackage;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "hotelA")
	private List<BookHotel> bookHotels ;

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getHotel_name() {
		return hotel_name;
	}

	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}

	public String getHotel_location() {
		return hotel_location;
	}

	public void setHotel_location(String hotel_location) {
		this.hotel_location = hotel_location;
	}

	public int getNo_of_person() {
		return no_of_person;
	}

	public void setNo_of_person(int no_of_person) {
		this.no_of_person = no_of_person;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNoofroom() {
		return noofroom;
	}

	public void setNoofroom(String noofroom) {
		this.noofroom = noofroom;
	}

	public String getHotel_descrption() {
		return hotel_descrption;
	}

	public void setHotel_descrption(String hotel_descrption) {
		this.hotel_descrption = hotel_descrption;
	}

	public String getHotel_img_url() {
		return hotel_img_url;
	}

	public void setHotel_img_url(String hotel_img_url) {
		this.hotel_img_url = hotel_img_url;
	}

	public List<HolidayPackage> getHolidayPackage() {
		return holidayPackage;
	}

	public void setHolidayPackage(List<HolidayPackage> holidayPackage) {
		this.holidayPackage = holidayPackage;
	}

	public List<BookHotel> getBookHotels() {
		return bookHotels;
	}

	public void setBookHotels(List<BookHotel> bookHotels) {
		this.bookHotels = bookHotels;
	}
	

	

	 

}
