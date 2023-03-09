package com.techelevator.tenmo.tenmo.model;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Builder
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "account_id")
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;

	@Column(name = "user_id")
	@NotNull
	private int userId;

	@Column(name = "balance")
	@NotNull
	private BigDecimal balance;

	public Account(int userId, BigDecimal balance) {
		this.userId = userId;
		this.balance = balance;
	}

	public Account(int accountId, int userId, BigDecimal balance) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}

	public Account() {
	}


	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account{" +
				"accountId=" + accountId +
				", userId=" + userId +
				", balance=" + balance +
				'}';
	}
}