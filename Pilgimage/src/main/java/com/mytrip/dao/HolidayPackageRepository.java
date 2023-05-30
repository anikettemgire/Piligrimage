package com.mytrip.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.modal.HolidayPackage;
public interface HolidayPackageRepository  extends JpaRepository<HolidayPackage, Integer>{

}
