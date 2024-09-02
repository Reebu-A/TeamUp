package com.Teamup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teamup.model.Login;
import com.Teamup.model.TurfOwner;
import com.Teamup.repository.Loginrepo;
import com.Teamup.repository.TurfownerRepo;

@Service
public class TurfownerService 
{
	@Autowired
	private Loginrepo loginrepo;
	
	@Autowired
	private TurfownerRepo tfrepo;
	
	public TurfOwner saveturfowner (TurfOwner tf)
	{
		Login log=new Login();
		TurfOwner save=tfrepo.save(tf);
		log.setTf(tf);
		log.setRole("Turf Owner");
		log.setEmail(tf.getEmail());
		log.setPassword(tf.getPassword());
		log.setStatus(0);
		log.setDeactivatedstatus(0);
		loginrepo.save(log);
		return save;
		
	}
	public List<TurfOwner> gettf(int id)
	{
		return tfrepo.findAllById(id);
	}
	
	public Login updateturfowner(TurfOwner tfu)
	{
		TurfOwner tfo= tfrepo.findById(tfu.getId()).get();
		tfo.setPhone(tfu.getPhone());
		tfo.setEmail(tfu.getEmail());
		tfo.setPassword(tfu.getPassword());
		tfrepo.save(tfo);
		Login log = loginrepo.findByTf(tfo).orElseThrow(() -> new RuntimeException("Login not found for TurfOwner"));
		log.setEmail(tfu.getEmail());
		log.setPassword(tfu.getPassword());
		
		return loginrepo.save(log);
	}

	

}
