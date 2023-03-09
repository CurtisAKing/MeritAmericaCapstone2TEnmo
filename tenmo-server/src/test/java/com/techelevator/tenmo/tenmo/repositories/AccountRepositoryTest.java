package com.techelevator.tenmo.tenmo.repositories;

import com.techelevator.tenmo.tenmo.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

	@Autowired
	AccountRepository accountRepository;

	@BeforeEach
	void setUp(){
		Account account = Account.builder()
				.accountId(1011)
				.userId(1001)
				.balance(BigDecimal.valueOf(1000.00))
				.build();
		accountRepository.save(account);

		Account account2 = Account.builder()
				.accountId(1012)
				.userId(1002)
				.balance(BigDecimal.valueOf(1000.00))
				.build();
		accountRepository.save(account);

		Account account3 = Account.builder()
				.accountId(1012)
				.userId(1003)
				.balance(BigDecimal.valueOf(1000.00))
				.build();
		accountRepository.save(account);
	}

	@Test
	public void getAccountShouldReturnAccountWithMatchingID(){
		Account account = accountRepository.findByUserId(1002);
		assertThat(account.getAccountId()).isEqualTo(2002);
	}

	@Test
	public void updateAccountShouldSaveEmployee(){
		Account account = Account.builder()
				.accountId(1010)
				.userId(1001)
				.balance(BigDecimal.valueOf(1000.00))
				.build();
		accountRepository.save(account);

		assertThat(account.getAccountId()).isGreaterThan(0);
	}

}
