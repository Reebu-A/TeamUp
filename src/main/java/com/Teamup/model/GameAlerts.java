package com.Teamup.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class GameAlerts 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalTime gametime;
	private LocalDate gamedate;
	private String turfname;
	private String gamename;
	private int turfrate;
	private int playersneeded;
	private String createdtime;
    private int userid;
    private String username;
    private int status;
    private int playersjoined;
    private int timestatus;
    private int playersstatus;
    private int turfownerid;
    private int deactivatedstatus;
    @Transient
    private List<User> acceptedUsers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public LocalTime getGametime() {
		return gametime;
	}

	public void setGametime(LocalTime gametime) {
		this.gametime = gametime;
	}

	public LocalDate getGamedate() {
		return gamedate;
	}

	public void setGamedate(LocalDate gamedate) {
		this.gamedate = gamedate;
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

	public int getPlayersneeded() {
		return playersneeded;
	}

	public void setPlayersneeded(int playersneeded) {
		this.playersneeded = playersneeded;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	

	public int getTurfrate() {
		return turfrate;
	}

	public void setTurfrate(int turfrate) {
		this.turfrate = turfrate;
	}

	public int getPlayersjoined() {
		return playersjoined;
	}

	public void setPlayersjoined(int playersjoined) {
		this.playersjoined = playersjoined;
	}
	

	public int getTimestatus() {
		return timestatus;
	}

	public void setTimestatus(int timestatus) {
		this.timestatus = timestatus;
	}

	public int getPlayersstatus() {
		return playersstatus;
	}

	public void setPlayersstatus(int playersstatus) {
		this.playersstatus = playersstatus;
	}
	
	

	public int getTurfownerid() {
		return turfownerid;
	}

	public void setTurfownerid(int turfownerid) {
		this.turfownerid = turfownerid;
	}

	
	public int getDeactivatedstatus() {
		return deactivatedstatus;
	}

	public void setDeactivatedstatus(int deactivatedstatus) {
		this.deactivatedstatus = deactivatedstatus;
	}

	public List<User> getAcceptedUsers() {
		return acceptedUsers;
	}

	public void setAcceptedUsers(List<User> acceptedUsers) {
		this.acceptedUsers = acceptedUsers;
	}

	

	

	public GameAlerts(int id, LocalTime gametime, LocalDate gamedate, String turfname, String gamename, int turfrate,
			int playersneeded, String createdtime, int userid, String username, int status, int playersjoined,
			int timestatus, int playersstatus, int turfownerid, int deactivatedstatus, List<User> acceptedUsers) {
		super();
		this.id = id;
		this.gametime = gametime;
		this.gamedate = gamedate;
		this.turfname = turfname;
		this.gamename = gamename;
		this.turfrate = turfrate;
		this.playersneeded = playersneeded;
		this.createdtime = createdtime;
		this.userid = userid;
		this.username = username;
		this.status = status;
		this.playersjoined = playersjoined;
		this.timestatus = timestatus;
		this.playersstatus = playersstatus;
		this.turfownerid = turfownerid;
		this.deactivatedstatus = deactivatedstatus;
		this.acceptedUsers = acceptedUsers;
	}

	public GameAlerts() {
		
	}

	@Override
	public String toString() {
		return "GameAlerts [id=" + id + ", gametime=" + gametime + ", gamedate=" + gamedate + ", turfname=" + turfname
				+ ", gamename=" + gamename + ", turfrate=" + turfrate + ", playersneeded=" + playersneeded
				+ ", createdtime=" + createdtime + ", userid=" + userid + ", username=" + username + ", status="
				+ status + ", playersjoined=" + playersjoined + ", timestatus=" + timestatus + ", playersstatus="
				+ playersstatus + "]";
	}

	

	
	

}
