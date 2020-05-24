package com.merit.assignment7.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SavingsAccount extends BankAccount {
	

	private static final double DEFAULT_INTEREST_RATE = .01;
	
	@ManyToOne
	private AccountHolder accountHolders;
	
	public SavingsAccount() {
		super.setInterestRate(DEFAULT_INTEREST_RATE);
	}

	public SavingsAccount(double balance, double interestRate, long accountNumber, Date accountOpenedOn) {
		super(balance, interestRate, accountNumber, accountOpenedOn);
	}

	public SavingsAccount(double openingBalance) {
		super(openingBalance, DEFAULT_INTEREST_RATE);
	}

	// to load from account object
	public SavingsAccount(BankAccount bankAccount) {
		super(bankAccount.getBalance(), bankAccount.getInterestRate(), bankAccount.getAccountNumber(),
				bankAccount.getAccountOpenedOn());
	}
	
	private long accountHolder;

	public long getAccountHolder() {
		return this.accountHolder;
	}

	public void setAccountHolder(long n) {
		this.accountHolder = n;
	}

}

