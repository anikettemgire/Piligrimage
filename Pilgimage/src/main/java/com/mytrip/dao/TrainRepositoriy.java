package com.mytrip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.TrainAd;
public interface TrainRepositoriy  extends JpaRepository<TrainAd, Integer>{

}
