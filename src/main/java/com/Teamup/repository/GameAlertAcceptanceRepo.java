package com.Teamup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Teamup.model.GameAlertAcceptance;
import com.Teamup.model.GameAlerts;
import com.Teamup.model.User;

public interface GameAlertAcceptanceRepo extends JpaRepository<GameAlertAcceptance, Integer> 
{
	public List<GameAlertAcceptance> findAllByacceptedBy(User id);
	public List<GameAlertAcceptance> findAllByAlert(GameAlerts id);
	//public List<User> findAcceptedbyByAlert(GameAlerts id);
	 @Query("SELECT ga.acceptedBy FROM GameAlertAcceptance ga WHERE ga.alert = :alert")
	   List<User> findAcceptedbyByAlert(@Param("alert") GameAlerts alert);
	 void deleteByAlert(GameAlerts alert);
	

}
