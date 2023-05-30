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
public class HolidayPackage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pid;
	private String package_name;
	private int day;
	private double price;
	private int no_of_activty;
	private String imageUrl;
	
	private String description;
    @ManyToMany
	private List<HotelA> hotelA;
    @ManyToMany
	private List<TrainAd> trainAd;
    @ManyToMany
	private List<BusA> busA;
    @ManyToMany
	private List<FlightA> flightA;
    
    
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "holidayPackage")
	private List<BookPackage> bookPackages ;
	public HolidayPackage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HolidayPackage(int pid, String package_name, int day, double price, int no_of_activty, String imageUrl,
			String description, List<HotelA> hotelA, List<TrainAd> trainAd, List<BusA> busA, List<FlightA> flightA) {
		super();
		this.pid = pid;
		this.package_name = package_name;
		this.day = day;
		this.price = price;
		this.no_of_activty = no_of_activty;
		this.imageUrl = imageUrl;
		this.description = description;
		this.hotelA = hotelA;
		this.trainAd = trainAd;
		this.busA = busA;
		this.flightA = flightA;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNo_of_activty() {
		return no_of_activty;
	}
	public void setNo_of_activty(int no_of_activty) {
		this.no_of_activty = no_of_activty;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<HotelA> getHotelA() {
		return hotelA;
	}
	public void setHotelA(List<HotelA> hotelA) {
		this.hotelA = hotelA;
	}
	public List<TrainAd> getTrainAd() {
		return trainAd;
	}
	public void setTrainAd(List<TrainAd> trainAd) {
		this.trainAd = trainAd;
	}
	public List<BusA> getBusA() {
		return busA;
	}
	public void setBusA(List<BusA> busA) {
		this.busA = busA;
	}
	public List<FlightA> getFlightA() {
		return flightA;
	}
	public void setFlightA(List<FlightA> flightA) {
		this.flightA = flightA;
	}
	
	
	
}