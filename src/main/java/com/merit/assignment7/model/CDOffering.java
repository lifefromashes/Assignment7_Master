package com.merit.assignment7.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CDOffering {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<CDAccount> cdAccounts;
	
	private int term;
	private double interestRate;
	
	
	public CDOffering(int term, double interestRate) {
		super();
		this.term = term;
		this.interestRate = interestRate;
	}

	public CDOffering() {}

	public int getTerm() {
		return term;
	}


	public void setTerm(int term) {
		this.term = term;
	}


	public double getInterestRate() {
		return interestRate;
	}


	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}

	public void setCdAccounts(List<CDAccount> cdAccounts) {
		this.cdAccounts = cdAccounts;
	}
	
	
	
	
	

}
