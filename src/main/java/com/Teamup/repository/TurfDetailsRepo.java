package com.Teamup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Teamup.model.TurfDetails;

public interface TurfDetailsRepo extends JpaRepository<TurfDetails, Integer>
{
	public List<TurfDetails> findAllByTurfownerid(int id);
	public long countByStatus(int st);
	List<TurfDetails> findAllByStatus(int st);
	public long countByStatusAndTurfownerid(int st, int tfo);
	public long countByTurfownerid(int tfo);
	public List<TurfDetails> findByTurfid(int id);
}
