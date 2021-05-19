package hello.testspring.bank.dao;

import hello.testspring.bank.domain.Account;

public interface AccountDao {

	void createAccount(Account account);

	void updateAccount(Account account);

	void removeAccount(Account account);

	public Account findAccount(String accountNo);
}
