package com.Teamup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Teamup.model.Admin;


public interface AdminRepo extends JpaRepository<Admin, Integer>
{
	
	public List<Admin> findAllById(int aid);
}
