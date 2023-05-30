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

public class BusA {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bus_id;
	private String bus_no;
	private String company;
	private String departure_city;
	private String destination_city;
	private String departure_time;
	private String total_time;
	private String destination_time;
	private String detail;
	private double price;
	@ManyToMany(mappedBy = "busA",cascade = CascadeType.ALL)
	private List<HolidayPackage> holidayPackage;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "busA")
	private List<Bookbus> bookbus;
	public int getBus_id() {
		return bus_id;
	}
	public void setBus_id(int bus_id) {
		this.bus_id = bus_id;
	}
	public String getBus_no() {
		return bus_no;
	}
	public void setBus_no(String bus_no) {
		this.bus_no = bus_no;
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
	public String getTotal_time() {
		return total_time;
	}
	public void setTotal_time(String total_time) {
		this.total_time = total_time;
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
	public List<Bookbus> getBookbus() {
		return bookbus;
	}
	public void setBookbus(List<Bookbus> bookbus) {
		this.bookbus = bookbus;
	}
	

	
}
