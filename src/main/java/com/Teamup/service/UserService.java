package com.Teamup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teamup.model.Login;
import com.Teamup.model.TurfOwner;
import com.Teamup.model.User;
import com.Teamup.repository.Loginrepo;
import com.Teamup.repository.TurfownerRepo;
import com.Teamup.repository.Userrepo;
@Service
public class UserService 
{
	@Autowired
	private Loginrepo loginrepo;
	
	@Autowired
	private Userrepo userrepo;
	
	
	public User saveuser(User us)
	{
		Login log=new Login();
		User i=userrepo.save(us);
		log.setUser(us);
		log.setRole("USER");	
		log.setEmail(us.getEmail());
		log.setPassword(us.getPassword());
		log.setStatus(0);
		log.setDeactivatedstatus(0);
		loginrepo.save(log);
		return i;
	}
	
	public List<User> getuser(int id)
	{
		return userrepo.findAllById(id);
	}
	
	public Login updateUser(User user)
	{
		User us= userrepo.findById(user.getId()).get();
		us.setPhone(user.getPhone());
		us.setEmail(user.getEmail());
		us.setAge(user.getAge());
		us.setLocality(user.getLocality());
		us.setPassword(user.getPassword());
		userrepo.save(us);
		Login log = loginrepo.findByUser(us).orElseThrow(() -> new RuntimeException("Login not found for TurfOwner"));
		log.setEmail(user.getEmail());
		log.setPassword(user.getPassword());
		
		return loginrepo.save(log);
	}

}
