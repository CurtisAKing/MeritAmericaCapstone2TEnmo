package com.techelevator.tenmo.tenmo.services;

import com.techelevator.tenmo.tenmo.model.Account;
import com.techelevator.tenmo.tenmo.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//carriers the workload of the AccountController
@Service
public class AccountService {

	AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account getAccount(int userId){
		return accountRepository.findByUserId(userId);
	}

	public Account saveAccount (Account account){
		return accountRepository.save(account);
	}

	public List<Account> getAccounts() {
		return accountRepository.findAll();
	}

	public Account getAccountByAccountId(int accountId) {
		return accountRepository.findByAccountId(accountId);
	}
}
