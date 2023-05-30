package com.mytrip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.FlightA;

public interface FlightRepositoriy  extends JpaRepository<FlightA, Integer>{

}
