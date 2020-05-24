//package com.merit.assignment6;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.merit.assignment7.exceptions.ExceedsAvailableBalanceException;
//import com.merit.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
//import com.merit.assignment7.exceptions.NegativeAmountException;
//import com.merit.assignment7.exceptions.NoSuchResourceFoundException;
//import com.merit.assignment7.model.AccountHolder;
//import com.merit.assignment7.model.AccountHolderContactDetails;
//import com.merit.assignment7.model.CDAccount;
//import com.merit.assignment7.model.CheckingAccount;
//import com.merit.assignment7.model.SavingsAccount;
//import com.merit.assignment7.repository.AccountHolderContactDetailsRepository;
//import com.merit.assignment7.repository.AccountHolderRepository;
//import com.merit.assignment7.repository.CDAccountRepository;
//import com.merit.assignment7.repository.CDOfferingRepository;
//import com.merit.assignment7.repository.CheckingAccountRepository;
//import com.merit.assignment7.repository.SavingsAccountRepository;
//
//@SpringBootTest
//class Assignment6ApplicationTests {
//
//	@Autowired
//	AccountHolderRepository accountHolderRepository;
//
//	@Autowired
//	CheckingAccountRepository checkingAccountRepository;
//
//	@Autowired
//	SavingsAccountRepository savingsAccountRepository;
//
//	@Autowired
//	CDAccountRepository cdAccountRepository;
//
//	@Autowired
//	CDOfferingRepository cdOfferingsRepository;
//
//	@Autowired
//	AccountHolderContactDetailsRepository accountHolderContactDetailsRepository;
//
//	@PersistenceContext
//	EntityManager entityManager;
//
//	@Test
//	void insertAccountHolder() {
//		AccountHolder accountHolder = new AccountHolder();
//		accountHolder.setFirstName("FirstName test");
//		accountHolder.setLastName("LastName test");
//		accountHolder.setSsn("7896541235");
//
//		accountHolderRepository.save(accountHolder);
//
//		AccountHolder dbAccHolder = accountHolderRepository.findById(accountHolder.getId());
//		assertNotNull(dbAccHolder);
//
//	}
//
////	@Test
////	void postAccountHolder() {
////		AccountHolder holder = new AccountHolder();
////		holder.setFirstName("First Name");
////		holder.setMiddleName("Middle Name");
////		holder.setLastName("Last Name");
////		holder.setSsn("123456789");
////		accountHolderRepository.save(holder);
////		AccountHolder holder2 = new AccountHolder();
////		holder2 = accountHolderRepository.findById(holder2.getId());
////		assertNotNull(holder2);
////	}
//
////	@Test
////	public void postContactDetails() {
////		AccountHolder holder = new AccountHolder();
////		AccountHolderContactDetails details = new AccountHolderContactDetails();
////		details.setAccountHolder(holder);
////		details.setPhoneNum("11111111111");
////		details.setAddress("1234 Main");
////		details.setEmail("mail@mail.com(opens in new tab)");
////		accountHolderContactDetailsRepository.save(details);
////
////		holder.setAccountHolderContactDetails(details);
////		assertNotNull(details);
////
////	}
//
////	@Test
////	public void getContactDetailsById() {
////		AccountHolder holder = new AccountHolder();
////		AccountHolderContactDetails details = new AccountHolderContactDetails();
////		details.setAccountHolder(holder);
////		details.setPhoneNum("11111111111");
////		details.setAddress("1234 Main");
////		details.setEmail("mail@mail.com(opens in new tab)");
////		//holder.setId(1);
////
////		accountHolderContactDetailsRepository.save(details);
////		//AccountHolder holder2 = accountHolderRepository.findById(holder.getId());
////		assertNotNull(holder);
////	}
//
//	@Test
//	public void createCheckingAccount()
//			throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsAvailableBalanceException {
//		AccountHolder holder = new AccountHolder();
//		CheckingAccount check = new CheckingAccount();
//		holder.addCheckingAccount(check);
//
//		check.deposit(500);
//		// check.withdraw(400);
//		checkingAccountRepository.save(check);
//		
//		try {
//		check.deposit(-100);
//		} catch (NegativeAmountException e) {
//			e.printStackTrace();
//		}
//		// check.withdraw(2000);
//
//		//AccountHolder check2 = accountHolderRepository.findById(holder.getId());
//
//		//assertNotNull(check.getBalance());
//	}
//
//	@Test
//	public void createSavingsAccount()
//			throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsAvailableBalanceException {
//		AccountHolder holder = new AccountHolder();
//		SavingsAccount savs = new SavingsAccount();
//		holder.addSavingsAccount(savs);
//
//		savs.deposit(3000);
//
//		savingsAccountRepository.save(savs);
//
//		try {
//		savs.deposit(-1000);
//		} catch (NegativeAmountException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			savs.withdraw(4000);
//
//		} catch (ExceedsAvailableBalanceException e) {
//			e.printStackTrace();
//		} catch (NoSuchResourceFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	
//		savingsAccountRepository.save(savs);
//		assertNotNull(savs);
//
//	}
//
//	@Test
//	public void createCDAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
//		AccountHolder holder = new AccountHolder();
//		CDAccount cd = new CDAccount();
//		holder.addCDAccount(cd);
//		cd.deposit(3000);
//		cdAccountRepository.save(cd);
//		assertNotNull(cd);
//
//	}
//
//	@Test
//	public void getCheckingAccountById() throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
//		AccountHolder holder = new AccountHolder();
//		CheckingAccount check = new CheckingAccount();
//		holder.addCheckingAccount(check);
//		checkingAccountRepository.save(check);
//
//		holder.getCheckingBalance();
//		holder.getCheckingAccounts();
//		assertNotNull(check);
//
//	}
//
//	@Test
//	public void getSavingsAccountById() throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
//		AccountHolder holder = new AccountHolder();
//		SavingsAccount savs = new SavingsAccount();
//		holder.addSavingsAccount(savs);
//		savingsAccountRepository.save(savs);
//
//		holder.getSavingsBalance();
//		holder.getSavingsAccounts();
//		assertNotNull(savs);
//	}
//
//	@Test
//	public void getCDAccountById() throws NegativeAmountException {
//		AccountHolder holder = new AccountHolder();
//		CDAccount cd = new CDAccount();
//		holder.addCDAccount(cd);
//		cdAccountRepository.save(cd);
//
//		holder.getCdAccounts();
//		assertNotNull(cd);
//	}
//}
