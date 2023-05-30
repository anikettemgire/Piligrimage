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

public class TrainAd {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int train_id;
	private String train_no;
	
	private String company;
	private String departure_city;
	private String destination_city;
	private String departure_time;
	private String  destination_time;
	private String total_time;
	private String  detail;
	private double price;
	
	@ManyToMany(mappedBy = "trainAd",cascade = CascadeType.ALL)
	private List<HolidayPackage> holidayPackage;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "trainAd")
	private List<BookTrain> bookTrains;

	public int getTrain_id() {
		return train_id;
	}

	public void setTrain_id(int train_id) {
		this.train_id = train_id;
	}

	public String getTrain_no() {
		return train_no;
	}

	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDeparture_city() {
		return departure_city;
	}

	public void setDeparture_city(String departure_city) {
		this.departure_city = departure_city;
	}

	public String getDestination_city() {
		return destination_city;
	}

	public void setDestination_city(String destination_city) {
		this.destination_city = destination_city;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getDestination_time() {
		return destination_time;
	}

	public void setDestination_time(String destination_time) {
		this.destination_time = destination_time;
	}

	public String getTotal_time() {
		return total_time;
	}

	public void setTotal_time(String total_time) {
		this.total_time = total_time;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<HolidayPackage> getHolidayPackage() {
		return holidayPackage;
	}

	public void setHolidayPackage(List<HolidayPackage> holidayPackage) {
		this.holidayPackage = holidayPackage;
	}

	public List<BookTrain> getBookTrains() {
		return bookTrains;
	}

	public void setBookTrains(List<BookTrain> bookTrains) {
		this.bookTrains = bookTrains;
	}


	
	
}
