package com.merit.assignment7.model;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.merit.assignment7.exceptions.ExceedsAvailableBalanceException;
import com.merit.assignment7.exceptions.NegativeAmountException;
import com.merit.assignment7.exceptions.NoSuchResourceFoundException;



//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Table(name = "AccountHolder")
//@DiscriminatorColumn(name = "", discriminatorType = DiscriminatorType.STRING)

@MappedSuperclass
@Table(name = "bankAccounts")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long accountNumber;

	@Min(0)
	private double balance;
	
	private double interestRate;

	private Date accountOpenedOn;
	
	public BankAccount() {
		this.accountOpenedOn = new Date();

	}

	public BankAccount(double balance, double interestRate, long accountNumber, Date accountOpenedOn) {

		this.balance = balance;
		this.interestRate = interestRate;
		


	}

	public BankAccount(double balance, double interestRate) {
		this.balance = balance;
		this.interestRate = interestRate;
	}

	public boolean withdraw(double amount) throws ExceedsAvailableBalanceException, NoSuchResourceFoundException {
		if (amount > this.balance) {
			throw new ExceedsAvailableBalanceException("Exceeds Available Balance");
		}

		if (amount < 0) {
			throw new NoSuchResourceFoundException("Cannot withdraw neg amount");
		}

		this.balance = this.balance - amount;
		return true;

	}

	public boolean deposit(double amount) throws NegativeAmountException{
		if (amount < 0)
			throw new NegativeAmountException("Unable to deposit");

		this.balance = this.balance + amount;
		return true;
	}

	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getAccountOpenedOn() {
		return accountOpenedOn;
	}

	public void setAccountOpenedOn(Date accountOpenedOn) {
		this.accountOpenedOn = accountOpenedOn;
	}
	
	

}

