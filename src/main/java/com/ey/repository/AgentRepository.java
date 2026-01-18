package com.ey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.entity.Agent;
import com.ey.entity.User;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	boolean existsByUser(User user);
	
}

