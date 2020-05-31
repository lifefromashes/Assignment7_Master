package com.merit.assignment7.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.merit.assignment7.exceptions.ExceedsAvailableBalanceException;
import com.merit.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import com.merit.assignment7.exceptions.NegativeAmountException;

@Entity
@Table(name = "accountholders", catalog = "meritamerica7")
public class AccountHolder implements Comparable<AccountHolder> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id") // user_id") // primary key
	private long id;

	@NotBlank(message = "First Name is required")
	private String firstName;

	private String middleName;

	@NotBlank(message = "Last Name is required")
	private String lastName;

	@Size(min = 9, max = 11)
	@NotBlank(message = "SSN is required")
	private String ssn;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	private AccountHolderContactDetails accountHolderContactDetails;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	private User user;

	@OneToMany(cascade = CascadeType.ALL)
	private List<CheckingAccount> checkingAccounts;

	@OneToMany(cascade = CascadeType.ALL)
	private List<SavingsAccount> savingsAccounts;

	@OneToMany(cascade = CascadeType.ALL)
	private List<CDAccount> cdAccounts;

	@OneToMany(cascade = CascadeType.ALL)
	private List<CDOffering> cdOfferings;

	private String email;
	private String phoneNum;
	private String address;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AccountHolder() {
		this.checkingAccounts = new ArrayList<>();
		this.savingsAccounts = new ArrayList<>();
		this.cdAccounts = new ArrayList<>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	//add back in the CheckingAccount addCheckingAccount method so it
	//gives back and actual populated object
	
	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Combined balance of your Checking and Savings accounts can not exceed $250,000.");
		}
		if(openingBalance < 0) {
			throw new NegativeAmountException("Can't deposit negative amount.");
		}
		CheckingAccount newChkAccount = new CheckingAccount(openingBalance);
		checkingAccounts.add(newChkAccount);
		return newChkAccount;
	}

	public boolean addCheckingAccount(CheckingAccount chkacc) throws ExceedsCombinedBalanceLimitException {
		if (chkacc == null) {
			return false;
		}
		if (getCheckingBalance() + getSavingsBalance() + chkacc.getBalance() >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Unable To Process");
		}
		checkingAccounts.add(chkacc);
		chkacc.setAccountHolder(this.id);
		return true;
	}
	
	
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Combined balance of your Checking and Savings accounts can not exceed $250,000.");
		}
		if(openingBalance < 0) {
			throw new NegativeAmountException("Can't deposit negative amount.");
		}
		SavingsAccount newSavAccount = new SavingsAccount(openingBalance);
		savingsAccounts.add(newSavAccount);
		return newSavAccount;
	}

	public boolean addSavingsAccount(SavingsAccount savacc) throws ExceedsCombinedBalanceLimitException {
		if (savacc == null) {
			return false;
		}
		if (getCheckingBalance() + getSavingsBalance() + savacc.getBalance() >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Unable to Process");
		}
		savingsAccounts.add(savacc);
		savacc.setAccountHolder(this.id);
		return true;
	}

	public boolean addCDAccount(CDAccount cdacc) {
		if (cdacc == null) {
			return false;
		}
		cdAccounts.add(cdacc);
		cdacc.setAccountHolder(this.id);
		return true;
	}
	
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws NegativeAmountException{
		if(openingBalance < 0) {
			throw new NegativeAmountException("Unable to open with negative amount");
		}
		CDAccount newCDAccount = new CDAccount(offering, openingBalance);
		cdAccounts.add(newCDAccount);
		return newCDAccount;
	}

	public double getCheckingBalance() {
		double sum = 0;
		for (BankAccount bankacc : checkingAccounts) {
			sum += bankacc.getBalance();
		}
		return sum;
	}

	public double getSavingsBalance() {
		double sum = 0;
		for (BankAccount bankacc : savingsAccounts) {
			sum += bankacc.getBalance();
		}
		return sum;
	}

	public double getCDBalance() {
		double sum = 0;
		for (BankAccount bankacc : cdAccounts) {
			sum += bankacc.getBalance();
		}
		return sum;
	}

	public double getCombinedBalance() {
		double sum = 0;
		sum += getCheckingBalance();
		sum += getSavingsBalance();
		sum += getCDBalance();
		return sum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String s) {
		this.firstName = s;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String s) {
		this.middleName = s;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String s) {
		this.lastName = s;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<CheckingAccount> getCheckingAccounts() {
		return checkingAccounts;
	}

	public void setCheckingAccounts(List<CheckingAccount> chkacc) {
		this.checkingAccounts = chkacc;
	}

	public List<SavingsAccount> getSavingsAccounts() {
		return savingsAccounts;
	}

	public void setSavingsAccounts(List<SavingsAccount> savacc) {
		this.savingsAccounts = savacc;
	}

	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}

	public void setCdAccounts(List<CDAccount> cdAcc) {
		this.cdAccounts = cdAcc;
	}

	public List<CDOffering> getCdOfferings() {
		return cdOfferings;
	}

	public void setCdOfferings(List<CDOffering> cdOfferings) {
		this.cdOfferings = cdOfferings;
	}

	public void setAccountHolderContactDetails(AccountHolderContactDetails accountHolderContactDetails) {
		this.accountHolderContactDetails = accountHolderContactDetails;
	}

	public AccountHolderContactDetails getAccountHolderContactDetails() {
		return accountHolderContactDetails;
	}

	public int getNumberCheckingAccounts() {
		return this.checkingAccounts.size();
	}

	public int getNumberSavingsAccounts() {
		return this.savingsAccounts.size();
	}

	public int getNumberCDAccounts() {
		return this.cdAccounts.size();
	}

	public void setAccountHolderContactDetails() {
		accountHolderContactDetails = new AccountHolderContactDetails();
		accountHolderContactDetails.setEmail(this.email);
		accountHolderContactDetails.setPhoneNum(this.phoneNum);
		accountHolderContactDetails.setAddress(this.address);
	}

	@Override
	public int compareTo(AccountHolder other) {
		int mySum = (int) getCombinedBalance();
		int otherSum = (int) other.getCombinedBalance();
		return mySum - otherSum;
	}

}
