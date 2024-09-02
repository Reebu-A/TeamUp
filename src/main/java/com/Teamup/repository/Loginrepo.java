package com.Teamup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Teamup.model.Login;
import com.Teamup.model.TurfOwner;
import com.Teamup.model.User;

public interface Loginrepo extends JpaRepository<Login, Integer>
{
	
	List<Login> findByEmailAndPassword(String email, String password);
	
	long countByStatus(int st);
	List<Login> findByStatusAndRole(int st, String role);
	Optional<Login> findByTf(TurfOwner tfo);
	Optional<Login> findByUser(User us);
	long countByStatusAndRole(int st, String role);

}
