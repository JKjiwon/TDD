package hello.testspring.bank.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hello.testspring.bank.exception.AccountNotFoundException;
import hello.testspring.bank.exception.DuplicateAccountException;
import hello.testspring.bank.domain.Account;

public class InMemoryAccountDao implements AccountDao {

	private Map<String, Account> accounts;

	public InMemoryAccountDao() {
		accounts = Collections.synchronizedMap(new HashMap<>());
	}

	public boolean accountExists(String accountNo) {
		return accounts.containsKey(accountNo);
	}

	@Override
	public void createAccount(Account account) {
		if (accountExists(account.getAccountNo())) {
			throw new DuplicateAccountException();
		}
		accounts.put(account.getAccountNo(), account);
	}

	@Override
	public void updateAccount(Account account) {
		if (!accountExists(account.getAccountNo())) {
			throw new AccountNotFoundException();
		}
		accounts.put(account.getAccountNo(), account);
	}

	@Override
	public void removeAccount(Account account) {
		if (!accountExists(account.getAccountNo())) {
			throw new AccountNotFoundException();
		}
		accounts.remove(account.getAccountNo());
	}

	@Override
	public Account findAccount(String accountNo) {
		Account account = accounts.get(accountNo);
		if (account == null) {
			throw new AccountNotFoundException();
		}
		return account;
	}
}
