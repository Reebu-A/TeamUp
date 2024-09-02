package com.Teamup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teamup.model.Login;
import com.Teamup.repository.Loginrepo;

@Service

public class LoginService 
{
	@Autowired
	private Loginrepo login;
	
	public List<Login> login(String username, String password)
	{
		System.out.println(username+"********"+password);
		return login.findByEmailAndPassword(username, password);
	}

	public long userAccountActivationPendingCount()
	{
		return login.countByStatusAndRole(0, "USER");
	}
	
	public long userAccountActivatedCount()
	{
		return login.countByStatusAndRole(1, "USER");
	}
	
	public long turfownerAccountActivationPendingCount()
	{
		return login.countByStatusAndRole(0, "Turf Owner");
	}
	
	public List<Login> activateuser()
	{
		return login.findByStatusAndRole(0,"USER");
	}
	public List<Login> activateturfowner()
	{
		return login.findByStatusAndRole(0,"Turf Owner");
	}
	
	public Login accountactivation(int id)
	{
	 Login lg=login.findById(id).get();
		lg.setStatus(1);
		return login.save(lg);
		
	}
	public Login accountdeactivation(int id)
	{
	 Login lg=login.findById(id).get();
		lg.setStatus(0);
		lg.setDeactivatedstatus(1);
		return login.save(lg);
		
	}
	public List<Login> activatedusers()
	{
		return login.findByStatusAndRole(1,"USER");
	}
	
	public List<Login> activatedturfowners()
	{
		return login.findByStatusAndRole(1,"Turf Owner");
	}
	
	
	
}
