package com.Teamup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int status;
	private String email;
	private String password;
	private String role;
	@OneToOne
	@JoinColumn(name="userlogid")
	private User user;

	@OneToOne
	@JoinColumn(name="tfologid")
	private TurfOwner tf;
	
	@OneToOne
	@JoinColumn(name="adminlogid")
	private Admin admin;

	private int deactivatedstatus;

	



	public Login(int id, int status, String email, String password, String role, User user, TurfOwner tf, Admin admin,
			int deactivatedstatus) {
		super();
		this.id = id;
		this.status = status;
		this.email = email;
		this.password = password;
		this.role = role;
		this.user = user;
		this.tf = tf;
		this.admin = admin;
		this.deactivatedstatus = deactivatedstatus;
	}



	public int getId() {
	return id;
}



public void setId(int id) {
	this.id = id;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
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

	public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

	public TurfOwner getTf() {
	return tf;
}



public void setTf(TurfOwner tf) {
	this.tf = tf;
}



	public Admin getAdmin() {
	return admin;
}



public void setAdmin(Admin admin) {
	this.admin = admin;
}



	public Login() {
		
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getDeactivatedstatus() {
		return deactivatedstatus;
	}



	public void setDeactivatedstatus(int deactivatedstatus) {
		this.deactivatedstatus = deactivatedstatus;
	}

	
}
