package com.techelevator.tenmo.tenmo.controller;

import com.techelevator.tenmo.tenmo.model.Account;
import com.techelevator.tenmo.tenmo.services.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
 //Controller to interact with data persisting in account
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/account")
public class AccountController {

	AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/getaccounts")
	public List<Account> getAccounts(){
		return accountService.getAccounts();
	}

	@GetMapping("/getaccount/{userId}")
	public Account getAccount(@PathVariable int userId){
		return accountService.getAccount(userId);
	}

	@PostMapping("/updateaccount")
	public Account saveAccount(@RequestBody Account account){
		return accountService.saveAccount(account);
	}

	@GetMapping("/getaccountbyaccountid/{accountId}")
	public Account getAccountByAccountId(@PathVariable int accountId){
		return accountService.getAccountByAccountId(accountId);
	}

}
