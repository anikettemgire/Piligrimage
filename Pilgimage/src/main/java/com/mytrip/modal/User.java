package com.mytrip.modal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "name filed is empty")
	@Size(min = 4,max = 20,message = "min 4 & max 20 character are allow")
	private String name;
  
	@Column(unique = true)
	private String email;
   
	private String mobileno;
   
	
	private String password;
    private String role;
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Bookbus> bookbus;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<BookFlight> bookFlight;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<BookTrain> bookTrains;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<BookHotel> bookHotels ;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<BookPackage> bookPackages ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<Bookbus> getBookbus() {
		return bookbus;
	}
	public void setBookbus(List<Bookbus> bookbus) {
		this.bookbus = bookbus;
	}
	public List<BookFlight> getBookFlight() {
		return bookFlight;
	}
	public void setBookFlight(List<BookFlight> bookFlight) {
		this.bookFlight = bookFlight;
	}
	public List<BookTrain> getBookTrains() {
		return bookTrains;
	}
	public void setBookTrains(List<BookTrain> bookTrains) {
		this.bookTrains = bookTrains;
	}
	public List<BookHotel> getBookHotels() {
		return bookHotels;
	}
	public void setBookHotels(List<BookHotel> bookHotels) {
		this.bookHotels = bookHotels;
	}
	public List<BookPackage> getBookPackages() {
		return bookPackages;
	}
	public void setBookPackages(List<BookPackage> bookPackages) {
		this.bookPackages = bookPackages;
	}
	
	

}
