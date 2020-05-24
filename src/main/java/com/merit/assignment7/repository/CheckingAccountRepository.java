package com.merit.assignment7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merit.assignment7.model.CheckingAccount;



public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long>{
	List<CheckingAccount> findByAccountHolder(long id);

}
