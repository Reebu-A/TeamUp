package com.Teamup.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teamup.model.Login;
import com.Teamup.model.TurfBooking;
import com.Teamup.model.TurfDetails;
import com.Teamup.repository.TurfBookingRepo;
import com.Teamup.repository.TurfDetailsRepo;

@Service
public class TurfdetailService 
{
	@Autowired
	private TurfDetailsRepo turfdetailrepo;
	
	@Autowired TurfBookingRepo bookingrepo;
	
	public TurfDetails saveturf(TurfDetails td)
	{
		td.setStatus(0);
		td.setDeactivatedstatus(0);
		return turfdetailrepo.save(td);
	}

	public List<TurfDetails> viewturf(int id)
	{
		return turfdetailrepo.findAllByTurfownerid(id);
	}
	public List<TurfDetails> viewallturf()
	{
		return turfdetailrepo.findAll();
	}
	public List<TurfDetails> viewnotactivatedturf()
	{
		return turfdetailrepo.findAllByStatus(0);
	}
	public List<TurfDetails> viewactivatedturf()
	{
		return turfdetailrepo.findAllByStatus(1);
	}
	public long turfActivationPendingcount()
	{
		return turfdetailrepo.countByStatus(0);
	}
	public long turfActivatedcount()
	{
		return turfdetailrepo.countByStatus(1);
	}
	public long turfActivationPendingcountByTfoId(int id)
	{
		return turfdetailrepo.countByStatusAndTurfownerid(0, id);
	}
	public long turfCountByTfoId(int id)
	{
		return turfdetailrepo.countByTurfownerid(id);
	}
	
	public TurfDetails turfactivation(int id)
	{
		TurfDetails td=turfdetailrepo.findById(id).get();
		td.setStatus(1);
		return turfdetailrepo.save(td);
		
	}
	public TurfDetails turfdeactivation(int id)
	{
		TurfDetails td=turfdetailrepo.findById(id).get();
		td.setStatus(0);
		td.setDeactivatedstatus(1);
		return turfdetailrepo.save(td);
		
	}
	public TurfBooking bookturf(int alertid,String turfname,String gamename,LocalTime gametime,LocalDate gamedate,int turfrate,int userid,int turfownerid)
	{
		TurfBooking tb=new TurfBooking();
		tb.setAlertid(alertid);
		tb.setTurfname(turfname);
		tb.setGamename(gamename);
		tb.setGametime(gametime);
		tb.setGamedate(gamedate);
		tb.setTurfrate(turfrate);
		tb.setUserid(userid);
		tb.setTurfownerid(turfownerid);
		return bookingrepo.save(tb);
	}
	public List<LocalTime> getBookedSlots(String turfName, String date) {
		
		LocalDate gameDate = LocalDate.parse(date);
        return bookingrepo.findBookedSlots(turfName, gameDate);
    }

	public List<TurfBooking> getBookedSlotsOfTurf(String turfName, LocalDate gameDate2) {
		
		//LocalDate gameDate = LocalDate.parse(gameDate2);
        return bookingrepo.findByTurfnameAndGamedate(turfName, gameDate2);
        }
	 
	    public TurfDetails getTurfById(int turfId) {
	        return turfdetailrepo.findById(turfId).orElse(null);
	    }
	    
	public List<TurfDetails> TurfdetailForUpdate(int turfId) 
	{
		
		return turfdetailrepo.findByTurfid(turfId);
	        
	 }
	
	public TurfDetails saveturfupdate(TurfDetails td)
	{
		TurfDetails tdu= turfdetailrepo.findByTurfid(td.getTurfid()).get(0);
		tdu.setTurfname(td.getTurfname());
		tdu.setPhone(td.getPhone());
		tdu.setTurflocation(td.getTurflocation());
		tdu.setStandardrate(td.getStandardrate());
		tdu.setPeaktimerate(td.getPeaktimerate());
		tdu.setDesigntype(td.getDesigntype());
		if(td.getTurfimage() != null)
		{
			tdu.setTurfimage(td.getTurfimage());
		}
		
		return turfdetailrepo.save(tdu);
	}
	
	public List<TurfBooking> getBookingsOfTurf(int turfownerid) {
		
        return bookingrepo.findAllByTurfownerid(turfownerid);
        }
	
	 public void deleteTurfById(int turfid) 
	    {
	        
		   turfdetailrepo.deleteById(turfid);
	    }
	
}
