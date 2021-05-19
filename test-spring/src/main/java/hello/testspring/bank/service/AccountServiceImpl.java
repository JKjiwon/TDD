package hello.testspring.bank.service;

import hello.testspring.bank.dao.AccountDao;
import hello.testspring.bank.domain.Account;
import hello.testspring.bank.exception.InsufficientBalanceException;

public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;

	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public void createAccount(String accountNo) {
		accountDao.createAccount(new Account(accountNo, 0));
	}

	@Override
	public void removeAccount(String accountNo) {
		Account account = accountDao.findAccount(accountNo);
		accountDao.removeAccount(account);
	}

	@Override
	public void deposit(String accountNo, double amount) {
		Account account = accountDao.findAccount(accountNo);
		account.setBalance(account.getBalance() + amount);
		accountDao.updateAccount(account);
	}

	@Override
	public void withdraw(String accountNo, double amount) {
		Account account = accountDao.findAccount(accountNo);

		if (account.getBalance() < amount) {
			throw new InsufficientBalanceException();
		}
		account.setBalance(account.getBalance() - amount);
		accountDao.updateAccount(account);
	}

	@Override
	public double getBalance(String accountNo) {
		return accountDao.findAccount(accountNo).getBalance();
	}
}
