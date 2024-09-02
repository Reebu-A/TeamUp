package com.Teamup.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TurfBooking
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int bookingid;
	int alertid;
	String turfname;
	String gamename;
	LocalTime gametime;
	LocalDate gamedate;
	int turfrate;
	int userid;
	int turfownerid;
	public int getBookingid() {
		return bookingid;
	}
	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}
	public int getAlertid() {
		return alertid;
	}
	public void setAlertid(int alertid) {
		this.alertid = alertid;
	}
	public String getTurfname() {
		return turfname;
	}
	public void setTurfname(String turfname) {
		this.turfname = turfname;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public LocalTime getGametime() {
		return gametime;
	}
	public void setGametime(LocalTime gametime) {
		this.gametime = gametime;
	}
	
	public int getTurfrate() {
		return turfrate;
	}
	public void setTurfrate(int turfrate) {
		this.turfrate = turfrate;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public LocalDate getGamedate() {
		return gamedate;
	}
	public void setGamedate(LocalDate gamedate) {
		this.gamedate = gamedate;
	}
	
	public int getTurfownerid() {
		return turfownerid;
	}
	public void setTurfownerid(int turfownerid) {
		this.turfownerid = turfownerid;
	}
	
	public TurfBooking(int bookingid, int alertid, String turfname, String gamename, LocalTime gametime,
			LocalDate gamedate, int turfrate, int userid, int turfownerid) {
		super();
		this.bookingid = bookingid;
		this.alertid = alertid;
		this.turfname = turfname;
		this.gamename = gamename;
		this.gametime = gametime;
		this.gamedate = gamedate;
		this.turfrate = turfrate;
		this.userid = userid;
		this.turfownerid = turfownerid;
	}
	public TurfBooking() {
		
	}
	

	
}
