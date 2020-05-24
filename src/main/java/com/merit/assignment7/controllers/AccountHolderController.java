package com.merit.assignment7.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.merit.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import com.merit.assignment7.exceptions.NegativeAmountException;
import com.merit.assignment7.exceptions.NoSuchResourceFoundException;
import com.merit.assignment7.model.AccountHolder;
import com.merit.assignment7.model.AccountHolderContactDetails;
import com.merit.assignment7.model.CDAccount;
import com.merit.assignment7.model.CDOffering;
import com.merit.assignment7.model.CheckingAccount;
import com.merit.assignment7.model.MeritBank;
import com.merit.assignment7.model.SavingsAccount;
import com.merit.assignment7.model.User;
import com.merit.assignment7.repository.AccountHolderContactDetailsRepository;
import com.merit.assignment7.repository.AccountHolderRepository;
import com.merit.assignment7.repository.CDAccountRepository;
import com.merit.assignment7.repository.CheckingAccountRepository;
import com.merit.assignment7.repository.SavingsAccountRepository;
import com.merit.assignment7.repository.UserRepository;
import com.merit.assignment7.security.AuthenticationRequest;
import com.merit.assignment7.security.AuthenticationResponse;
import com.merit.assignment7.security.JwtUtil;
import com.merit.assignment7.security.MyUserDetailsService;

import javassist.NotFoundException;

@RestController
public class AccountHolderController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass() );


	@Autowired
	private AccountHolderRepository accountHolderRepository;

	@Autowired
	private AccountHolderContactDetailsRepository accountHolderContactDetailsRepository;
	
	@Autowired
	private CheckingAccountRepository checkingAccountRepository;
	
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;
	
	@Autowired
	private CDAccountRepository cdAccountRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	// === CREATE USER ===
	
	
	@PostMapping(value = "/authenticate/createUser")
	public ResponseEntity<?> createUser(@RequestBody User user){
		userRepository.save(user);
		return ResponseEntity.ok(user);
	}
	
	// === ME CONTROLLER ===
	//@GetMapping(value = "/Me")
//	public AccountHolder getMe(@AuthenticationPrincipal User userDetails) {
//		return ;
	@GetMapping(value = "/Me")
	public AccountHolder getMe(@RequestHeader (name = "Authorization")String token){
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		log.info(jwtTokenUtil.extractUsername(token));

		log.info("" + user.getAccountHolder());
		AccountHolder ah = accountHolderRepository.findById(user.getAccountHolder().getId());   //.findOne(user.getAccountHolder().getId());
		log.info(ah.getFirstName());
		return ah;//accountHolderRepository.findOne(user.getAccountHolder().getId());
	}
	
	@PostMapping(value = "/Me/CheckingAccounts")
	public CheckingAccount addMeChecking(@RequestHeader (name = "Authorization")String token, @RequestBody CheckingAccount checking) throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepository.findOne(user.getAccountHolder().getId());
		account.addCheckingAccount(checking);
		accountHolderRepository.save(account);
		return checking;
	}
	
	@GetMapping(value = "/Me/CheckingAccounts")
	public List<CheckingAccount> getMeChecking(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepository.findOne(user.getAccountHolder().getId()).getCheckingAccounts();
	}
	

	@PostMapping(value = "/Me/SavingsAccounts")
	public SavingsAccount addMeSavings(@RequestHeader (name = "Authorization")String token, @RequestBody SavingsAccount savings) throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepository.findOne(user.getAccountHolder().getId());
		account.addSavingsAccount(savings);
		accountHolderRepository.save(account);
		return savings;
	}
	
	@GetMapping(value = "/Me/SavingsAccounts")
	public List<SavingsAccount> getMeSavings(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepository.findOne(user.getAccountHolder().getId()).getSavingsAccounts();
	}
	
	@PostMapping(value = "/Me/CDAccounts")
	public CDAccount addMeCDAccount(@RequestHeader (name = "Authorization")String token, @RequestBody CDAccount cdAccount) throws ExceedsCombinedBalanceLimitException, NegativeAmountException {
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepository.findOne(user.getAccountHolder().getId());
		account.addCDAccount(cdAccount);
		accountHolderRepository.save(account);
		return cdAccount;
	}
	
	@GetMapping(value = "/Me/CDAccounts")
	public List<CDAccount> getMeCDAccount(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		User user = userRepository.findByUserName(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepository.findOne(user.getAccountHolder().getId()).getCdAccounts();
	}
	
	// === AccountHolder Controller ===
	
//	@PostMapping("/AccountHolders")
//	@ResponseStatus(HttpStatus.CREATED)
//	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
//		accountHolderRepository.save(accountHolder);
//		return accountHolder;
//	}
	
	/////====USE THIS ONE BUT FIX TO MAKE WORK ++++
	@PostMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolders(@RequestHeader ("Authorization")String auth, @RequestBody @Valid AccountHolder accountHolder) {
		
		//String token = auth.substring(7);
		//String username = jwtTokenUtil.extractUsername(token);
		AccountHolderContactDetails contact = new AccountHolderContactDetails(accountHolder);
		Optional<User> user = userRepository.findByUserName(accountHolder.getUsername());   //findOne(accountHolder.getUser().getId());
		User u = user.get();
		//u.setUserName(username);
		accountHolder.setAccountHolderContactDetails(contact);
		//u.setAccountHolder(accountHolder);
		//accountHolder.setUser(u);
		accountHolder.setUser(u); //userRepository.findByUserName(accountHolder.getUsername()));
		accountHolderRepository.save(accountHolder);
		userRepository.save(u);
		log.info("!!" + accountHolder.getUser());
		return accountHolder;
	}
	


	@GetMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.OK)
	public List<AccountHolder> getAccountHolders() {
		return accountHolderRepository.findAll();
	}

	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getAccountHolderById(@PathVariable(name = "id") long id) throws NoSuchResourceFoundException {
		AccountHolder accthold = accountHolderRepository.findById(id);
		return accthold;
	}



	@GetMapping(value = "/AccountHolders/{id}/ContactDetails")
	@ResponseStatus(HttpStatus.OK)
	public List<AccountHolderContactDetails> getContactDetails() {
		return accountHolderContactDetailsRepository.findAll();
	}

	@PostMapping(value = "/AccountHolders/{id}/ContactDetails")
	@ResponseStatus(HttpStatus.CREATED)
	public void addContactDetails(@PathVariable(name = "id") long id,
			@RequestBody AccountHolderContactDetails contactDetails) {
		AccountHolder acctholder = accountHolderRepository.findById(id);
		acctholder.setAccountHolderContactDetails(contactDetails);
//		contactDetails.setAccountHolder(acctholder);
		accountHolderRepository.save(acctholder);

	}
	
	// === CHECKING ACCOUNTS ===
	
	@GetMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getCheckingAccounts(@PathVariable(name = "id") long id)
			throws NoSuchResourceFoundException {
		
		
		AccountHolder acctholder = accountHolderRepository.findById(id);
		
		if(acctholder == null) {
			throw new NoSuchResourceFoundException("Invalid Id");
		}
		
		
		List<CheckingAccount> chkacc = checkingAccountRepository.findByAccountHolder(acctholder.getId());
		return chkacc;
	}

	@PostMapping("/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAccount(@PathVariable(name = "id") long id,
			@RequestBody @Valid CheckingAccount checkingAccount)
			throws ExceedsCombinedBalanceLimitException, NotFoundException, NegativeAmountException {

		AccountHolder acctholder = accountHolderRepository.findById(id);
		acctholder.addCheckingAccount(checkingAccount);
		checkingAccountRepository.save(checkingAccount);
		return checkingAccount;
	}
	
	// == Savings Accounts ==
	
	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<SavingsAccount> getSavingsAccounts(@PathVariable(name = "id") long id)
			throws NoSuchResourceFoundException {
		AccountHolder acctholder = accountHolderRepository.findById(id);
		List<SavingsAccount> savacc = savingsAccountRepository.findByAccountHolder(acctholder.getId());
		return savacc;
	}

	@PostMapping("/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAccount(@PathVariable(name = "id") long id,
			@RequestBody @Valid SavingsAccount savingsAccount)
			throws ExceedsCombinedBalanceLimitException, NotFoundException, NegativeAmountException {

		AccountHolder acctholder = accountHolderRepository.findById(id);
		acctholder.addSavingsAccount(savingsAccount);
		savingsAccountRepository.save(savingsAccount);
		return savingsAccount;
	}

	// === CD ACCOUNTS ===
	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CDAccount> getCDAccounts(@PathVariable(name = "id") long id) throws NoSuchResourceFoundException {
		AccountHolder acctholder = accountHolderRepository.findById(id);
		List<CDAccount> cdacc = cdAccountRepository.findByAccountHolder(acctholder.getId());
		return cdacc;
	}

	@PostMapping("/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCdAccount(@PathVariable(name = "id") long id, @RequestBody @Valid CDAccount cdAccount)
			throws ExceedsCombinedBalanceLimitException, NotFoundException, NegativeAmountException {

		AccountHolder acctholder = accountHolderRepository.findById(id);
		acctholder.addCDAccount(cdAccount);
		cdAccountRepository.save(cdAccount);
		return cdAccount;
	}
	
	@CrossOrigin
	@PostMapping("CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering createCDOffering(@RequestBody CDOffering cdo) {
		MeritBank.addCDOffering(cdo);
		return cdo;
	}
	
	@CrossOrigin
	@GetMapping("CDOfferings")
	public List<CDOffering> getCDOfferings() throws NotFoundException {
		List<CDOffering> cdo = MeritBank.getCDOfferings();
		return cdo;
	}

}
