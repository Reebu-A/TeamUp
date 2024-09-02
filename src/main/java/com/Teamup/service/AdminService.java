package com.Teamup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teamup.model.Admin;
import com.Teamup.model.Login;
import com.Teamup.model.User;
import com.Teamup.repository.AdminRepo;
import com.Teamup.repository.Loginrepo;
import com.Teamup.repository.Userrepo;

@Service
public class AdminService 
{

	@Autowired
	private AdminRepo adminrepo;
	
	@Autowired
	private Loginrepo loginrepo;
	
	@Autowired
	private Userrepo userrepo;
	
	public Admin saveadmin(Admin ad)
	{
		Login log=new Login();
		Admin i=adminrepo.save(ad);
		log.setAdmin(ad);
		log.setRole("ADMIN");
		log.setEmail(ad.getEmail());
		log.setPassword(ad.getPassword());
		log.setStatus(1);
		loginrepo.save(log);
		return i;
	}
	
	public List<Admin> getadmininfo(int id)
	{
		return adminrepo.findAllById(id);
	}
	
}
