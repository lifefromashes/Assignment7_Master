package com.merit.assignment7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merit.assignment7.model.CDOffering;

public interface CDOfferingRepository extends JpaRepository<CDOffering, Long>{

	CDOffering findById(long id);
}
