package com.Teamup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Teamup.model.User;

public interface Userrepo extends JpaRepository<User, Integer>
{
	public List<User> findAllById(int uid);

}
