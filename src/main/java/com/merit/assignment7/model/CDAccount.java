package com.merit.assignment7.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cdAccounts", catalog = "meritamerica7")
public class CDAccount extends BankAccount{
	
	private int term;
	
	@ManyToOne
	private AccountHolder accountHolders; /////just have this in other classes!!! no other extra annotations
	
	@ManyToOne	
	private CDOffering cdOfferings;

	public CDAccount() {
		super();
	}
	
	public CDAccount(double balance, double interestRate, long accountNumber, Date accountOpenedOn) {
		super(balance, interestRate, accountNumber, accountOpenedOn);
	}

	public CDAccount(double openingBalance, double interestRate, int term) {
		super(openingBalance, interestRate);
		this.term = term;
	}
	
	@Override
	public boolean withdraw(double amount) {
		return false;
	}
	
	public boolean deposit(double amount) {
		return false;
	}
	
	public int getTerm() {
		return this.term;
	}
	
	private long accountHolder;

	public long getAccountHolder() {
		return this.accountHolder;
	}

	public void setAccountHolder(long n) {
		this.accountHolder = n;
	}

	public AccountHolder getAccountHolders() {
		return accountHolders;
	}

	public void setAccountHolders(AccountHolder accountHolders) {
		this.accountHolders = accountHolders;
	}

	public CDOffering getCdOfferings() {
		return cdOfferings;
	}

	public void setCdOfferings(CDOffering cdOfferings) {
		this.cdOfferings = cdOfferings;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
	
	
	
}
