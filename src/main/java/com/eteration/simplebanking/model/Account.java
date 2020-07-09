package com.eteration.simplebanking.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// This class is a place holder you can change the complete implementation

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID")
	private Long accountId;
	private String owner;
	private String accountNumber;
	private Double balance = 0.0;
	private Date createDate = new Date();

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private Set<Transaction> transactions = new HashSet<Transaction>();


	public Long getAccountId() {
		return accountId;
	}

	public Account() {
	}

	public Account(String owner, String accountNumber) {
		this.owner = owner;
		this.accountNumber = accountNumber;
	}

	public void post(Transaction t) throws InsufficientBalanceException {
		if (t instanceof WithdrawalTransaction) {
			withdraw(t.getAmount());
		} else if (t instanceof DepositTransaction) {
			deposit(t.getAmount());
		}
		transactions.add(t);
	}

	public Double getBalance() {
		return balance;
	}

	public String getOwner() {
		return owner;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void deposit(int i) {
		this.deposit(Double.valueOf(i));
	}

	public void deposit(Double i) {
		this.balance = this.balance + i;
	}

	public void withdraw(int amount) throws InsufficientBalanceException {
		this.withdraw(Double.valueOf(amount));
	}

	public void withdraw(Double i) throws InsufficientBalanceException {
		if (this.balance < i) {
			throw new InsufficientBalanceException();
		}
		this.balance = this.balance - i;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
