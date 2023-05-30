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

public class FlightA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int filght_id;
	private String flight_no;
	private String company;
	private String departure_city;
	private String destination_city;
	private String total_time;
	private String departure_time;
	private String  destination_time;
	private String  detail;
	private double price;
	
	
	@ManyToMany(mappedBy = "flightA",cascade = CascadeType.ALL)
	private List<HolidayPackage> holidayPackage;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "flightA")
	private List<BookFlight> bookFlights;
	public int getFilght_id() {
		return filght_id;
	}
	public void setFilght_id(int filght_id) {
		this.filght_id = filght_id;
	}
	public String getFlight_no() {
		return flight_no;
	}
	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
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
	public String getTotal_time() {
		return total_time;
	}
	public void setTotal_time(String total_time) {
		this.total_time = total_time;
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
	public List<BookFlight> getBookFlights() {
		return bookFlights;
	}
	public void setBookFlights(List<BookFlight> bookFlights) {
		this.bookFlights = bookFlights;
	}

	
	
	
	


}
