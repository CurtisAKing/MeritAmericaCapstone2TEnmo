package com.techelevator.tenmo.tenmo.repositories;

import com.techelevator.tenmo.tenmo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//database interaction interface for account
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account findByUserId(int userId);

	Account findByAccountId(int accountId);
}
