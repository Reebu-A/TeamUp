package com.Teamup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Teamup.model.GameAlerts;



public interface GameAlertRepo extends JpaRepository<GameAlerts, Integer>
{
	public List<GameAlerts> findAllByUseridNot(int id);
	public List<GameAlerts> findAllByUseridAndTimestatusAndDeactivatedstatus(int id, int tst, int st);
	List<Integer> findIdsByUseridAndTimestatus(int id, int st);
	public List<GameAlerts> findAllByStatus(int st);
	public long countByStatus(int st);
	
	 @Query("SELECT ga FROM GameAlerts ga WHERE ga.userid != :userId AND ga.status = 1")
	 List<GameAlerts> findByUseridNotAndStatus(@Param("userId") int userId);
	
}
