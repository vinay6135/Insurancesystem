package com.ey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}

