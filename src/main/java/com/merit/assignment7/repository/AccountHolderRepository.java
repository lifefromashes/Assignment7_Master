package com.merit.assignment7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merit.assignment7.model.AccountHolder;


public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
	AccountHolder findById(long id);

	
 	
	

}
