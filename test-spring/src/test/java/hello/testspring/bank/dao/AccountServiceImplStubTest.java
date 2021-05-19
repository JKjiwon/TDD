package hello.testspring.bank.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.testspring.bank.domain.Account;
import hello.testspring.bank.exception.InsufficientBalanceException;
import hello.testspring.bank.service.AccountService;
import hello.testspring.bank.service.AccountServiceImpl;

public class AccountServiceImplStubTest {

	private static final String TEST_ACCOUNT_NO = "1234";

	private AccountDaoStub accountDaoStub;
	private AccountService accountService;

	private class AccountDaoStub implements AccountDao {

		private String accountNo;
		private double balance;

		@Override
		public void createAccount(Account account) { }

		@Override
		public void removeAccount(Account account) { }

		@Override
		public Account findAccount(String accountNo) {
			return new Account(this.accountNo, this.balance);
		}

		@Override
		public void updateAccount(Account account) {
			this.accountNo = account.getAccountNo();
			this.balance = account.getBalance();
		}
	}

	@BeforeEach
	public void init() {
		accountDaoStub = new AccountDaoStub();
		accountDaoStub.accountNo = TEST_ACCOUNT_NO;
		accountDaoStub.balance = 100;
		accountService = new AccountServiceImpl(accountDaoStub);
	}

	@Test
	public void deposit() {
		accountService.deposit(TEST_ACCOUNT_NO, 50);
		assertEquals(accountDaoStub.accountNo, TEST_ACCOUNT_NO);
		assertEquals(accountDaoStub.balance, 150.0);
	}

	@Test
	public void withdrawWithSufficientBalance() {
		accountService.withdraw(TEST_ACCOUNT_NO, 50);
		assertEquals(accountDaoStub.accountNo, TEST_ACCOUNT_NO);
		assertEquals(accountDaoStub.balance, 50,0);
	}

	@Test
	public void withdrawWithInsufficientBalance() {
		assertThrows(InsufficientBalanceException.class,
			() -> accountService.withdraw(TEST_ACCOUNT_NO, 150));
	}

}
