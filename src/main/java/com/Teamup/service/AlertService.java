package com.Teamup.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Teamup.model.GameAlertAcceptance;
import com.Teamup.model.GameAlerts;
import com.Teamup.model.TurfBooking;
import com.Teamup.model.User;
import com.Teamup.repository.GameAlertAcceptanceRepo;
import com.Teamup.repository.GameAlertRepo;
import com.Teamup.repository.TurfBookingRepo;

import jakarta.transaction.Transactional;


@Service
public class AlertService
{
	@Autowired
	public GameAlertRepo galertrepo;
	@Autowired
	public GameAlertAcceptanceRepo gaacceptancerepo;
	@Autowired
	public TurfBookingRepo tbookingrepo;

	public GameAlerts savegamealert(GameAlerts ga)
	{
		LocalDateTime time = LocalDateTime.now();
        ZonedDateTime zoned = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println(zoned);
        LocalDateTime now = zoned.toLocalDateTime();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  
        String datetime = now.format(format);
		ga.setCreatedtime(datetime);
		ga.setStatus(1);
		ga.setTimestatus(0);
		ga.setPlayersstatus(0);
		return galertrepo.save(ga);
		
	}
	
	public GameAlertAcceptance acceptgamealert(int alertid, User user)
	{
		GameAlerts galert=galertrepo.findById(alertid).get();
		
		int playersneeded=galert.getPlayersneeded();
		int playersjoined=galert.getPlayersjoined();
		System.out.println("playerjoined="+playersjoined);
		if(playersneeded==playersjoined)
		{
			galert.setPlayersstatus(1);
		}
		else {
			int addplayer=playersjoined+1;
			System.out.println("addplayer="+addplayer);
			galert.setPlayersjoined(addplayer);	
		}
		GameAlertAcceptance galertaccept= new GameAlertAcceptance();	
		galertrepo.save(galert);
		galertaccept.setAlert(galert);
		galertaccept.setAcceptedBy(user);
		return gaacceptancerepo.save(galertaccept);
	}
	
	public List<GameAlerts> findacceptedalerts(int uid)
	{
		 User user = new User(); 
	     user.setId(uid);
		List<GameAlertAcceptance> accepted= gaacceptancerepo.findAllByacceptedBy(user);
//		List<GameAlerts> allgamealert=galertrepo.findAllByUseridNot(uid);
		List<GameAlerts> allgamealert=galertrepo.findByUseridNotAndStatus(uid);
		for (GameAlertAcceptance alerts : accepted) {
	    	  System.out.println("User Accepted Alert Details:");
	    	  System.out.println(" Accepted Alert ID: " + alerts.getAlert()); 	    	 
		}
		for (GameAlerts allalerts : allgamealert) {
	    	  System.out.println("All Alert Details:");
	    	  System.out.println("  ID: " + allalerts.getId()); 
		}
		 Set<GameAlerts> acceptedAlertIds = accepted.stream()
	                .map(GameAlertAcceptance::getAlert)
	                .collect(Collectors.toSet());
		 
		 for (GameAlerts alert : acceptedAlertIds) {
			  System.out.println("ID in Set:");
			  System.out.println("  ID to remove: " + alert.getId());
			}
		 
		 List<GameAlerts> updatedGameAlertsList = allgamealert.stream()
                 .filter(gameAlert -> !acceptedAlertIds.contains(gameAlert))
                 .collect(Collectors.toList());
		 
		 for (GameAlerts alert : updatedGameAlertsList) {
			  System.out.println("updated list");
			  System.out.println("  newID: " + alert.getId());
			}
		 
	        return updatedGameAlertsList;
		
	}
	
	public List<GameAlerts> findmyalerts(int uid)
	{
	     List<GameAlerts> mygamealert=galertrepo.findAllByUseridAndTimestatusAndDeactivatedstatus(uid, 0, 0);
	    
	     List<TurfBooking> booked=tbookingrepo.findAllByUserid(uid);
	     
	     Set<Integer> bookedAlertIds = booked.stream()
                 .map(TurfBooking::getAlertid)
                 .collect(Collectors.toSet());
	     
	     mygamealert.removeIf(alert -> bookedAlertIds.contains(alert.getId()));

	     for (GameAlerts allalerts : mygamealert) {
	    	 //ga.setId(allalerts.getId());
	    	 //galertrepo.save(ga) ;
	    	 List<User> acceptedusers = gaacceptancerepo.findAcceptedbyByAlert(allalerts);
	         
	         allalerts.setAcceptedUsers(acceptedusers);
	    	  for (User alerts : acceptedusers) {
		    	  System.out.println("Users Accepted:");
		    	  System.out.println(" Accepted user ID: " + alerts.getId()+"  "+alerts.getName()); 	    	 
			}
	    	 }
 
	    // List<GameAlertAcceptance> acceptedby= gaacceptancerepo.findAllByAlert(ga);
	    
	   
		  return mygamealert;		
	}
	
	
	@Scheduled(fixedRate = 60000) // Run every minute
    public void updateGameStatusontime() {
        List<GameAlerts> games = galertrepo.findAll();
        LocalDateTime time = LocalDateTime.now();
        ZonedDateTime zoned = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println(zoned);
        LocalDateTime now = zoned.toLocalDateTime();

        
        for(GameAlerts game : games)
        {
        	LocalDateTime gamedatetime = null;
            
            // Check if gamedate and gametime are not null
            if (game.getGamedate() != null && game.getGametime() != null) {
                gamedatetime = LocalDateTime.of(game.getGamedate(), game.getGametime());
            }
        	//LocalDateTime gamedatetime = LocalDateTime.of(game.getGamedate(), game.getGametime());
            if (gamedatetime != null) {
            LocalDateTime oneHourBeforeGame = gamedatetime.minusHours(1);
        	
        	if (now.isAfter(oneHourBeforeGame) && game.getTimestatus() != 1) {
               game.setTimestatus(1);
                galertrepo.save(game);
        	}
        	if(game.getPlayersneeded()==game.getPlayersjoined())
        	{
        		game.setPlayersstatus(1);
        		galertrepo.save(game);
        	}
        	if(game.getPlayersstatus()==1 || game.getTimestatus()==1)
        	{
        		 game.setStatus(0);
        		 galertrepo.save(game);
        	}
            } else {
                System.out.println("Game date or time is null for game with id: " + game.getId());
            }
        }
	
	}
	
	public List<GameAlerts> findAllActiveAlerts()
	{
		return galertrepo.findAllByStatus(1);
	}
	public List<GameAlerts> findAlerthistory()
	{
		return galertrepo.findAllByStatus(0);
	}
	
	public Long activealertcount()
	{
		return galertrepo.countByStatus(1);
	}
	
	public List<GameAlerts> findAllAlerts()
	{
		return galertrepo.findAll();
	}
	
	public GameAlerts gamealertdeactivation(int alertid)
	{
		GameAlerts galert=galertrepo.findById(alertid).get();
		galert.setDeactivatedstatus(1);
		galert.setStatus(0);
		return galertrepo.save(galert);
	}
	public List<TurfBooking> bookinghistory(int id)
	{
		 return tbookingrepo.findAllByUserid(id);
	}
	@Transactional
	public void deleteAlert (int id)
	{
		GameAlerts galert=galertrepo.findById(id).get();
		gaacceptancerepo.deleteByAlert(galert);
		galertrepo.deleteById(id);
	}
	
}
