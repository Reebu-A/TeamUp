package com.Teamup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Teamup.model.TurfOwner;

public interface TurfownerRepo extends JpaRepository<TurfOwner, Integer>
{
	public List<TurfOwner> findAllById(int tid);

}
