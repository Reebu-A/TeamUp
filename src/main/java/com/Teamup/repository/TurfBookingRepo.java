package com.Teamup.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Teamup.model.TurfBooking;

public interface TurfBookingRepo extends JpaRepository<TurfBooking, Integer> 
{
	List<TurfBooking> findAllByUserid(int id);
	@Query("SELECT b.gametime FROM TurfBooking b WHERE b.turfname = :turfName AND b.gamedate = :gameDate")
    List<LocalTime> findBookedSlots(String turfName, LocalDate gameDate);
	 List<TurfBooking> findByTurfnameAndGamedate(String turfName, LocalDate gameDate);
	 List<TurfBooking> findAllByTurfownerid(int tid);
}
