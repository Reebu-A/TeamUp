package com.Teamup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GameAlertAcceptance 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
    @JoinColumn(name = "alertid")
    private GameAlerts alert;

    @ManyToOne
    @JoinColumn(name = "acceptedby")
    private User acceptedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameAlerts getAlert() {
		return alert;
	}

	public void setAlert(GameAlerts alert) {
		this.alert = alert;
	}

	public User getAcceptedBy() {
		return acceptedBy;
	}

	public void setAcceptedBy(User acceptedBy) {
		this.acceptedBy = acceptedBy;
	}

	public GameAlertAcceptance(int id, GameAlerts alert, User acceptedBy) {
		super();
		this.id = id;
		this.alert = alert;
		this.acceptedBy = acceptedBy;
	}

	public GameAlertAcceptance() {
		
	}
	
    

}
