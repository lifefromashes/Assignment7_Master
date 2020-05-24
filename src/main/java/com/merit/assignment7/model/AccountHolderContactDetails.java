package com.merit.assignment7.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "account_holder_contact")//, catalog = "MeritAmerica")
public class AccountHolderContactDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	private String phoneNum;
	private String email;
	private String address;
	
	private String firstName;
	private String middleName;
	private String lastName;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private AccountHolder accountHolder;
	
	public AccountHolderContactDetails() {}

	public AccountHolderContactDetails(AccountHolder accountHolder) {
		this.firstName = accountHolder.getFirstName();
		this.middleName = accountHolder.getMiddleName();
		this.lastName = accountHolder.getLastName();
		this.accountHolder = accountHolder;
	}
	
	public long getId() { 
		return id; 
		}
	public void setId(long id) { 
		this.id = id; 
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

	public String getAddress() { return address; }
	public AccountHolderContactDetails setAddress(String address) { this.address = address; return this;}
	

//	public AccountHolder getAccountHolder() { return accountHolder; }
//	public void setAccountHolder(AccountHolder accountHolder) { this.accountHolder = accountHolder; }
	
//	private long accountHolder;
//
//	public long getAccountHolder() {
//		return this.accountHolder;
//	}
//
//	public void setAccountHolder(long n) {
//		this.accountHolder = n;
//	}
	

	
	
}
