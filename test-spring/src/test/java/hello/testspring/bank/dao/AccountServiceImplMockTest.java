package hello.testspring.bank.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import hello.testspring.bank.domain.Account;
import hello.testspring.bank.exception.InsufficientBalanceException;
import hello.testspring.bank.service.AccountService;
import hello.testspring.bank.service.AccountServiceImpl;

public class AccountServiceImplMockTest {

	private static final String TEST_ACCOUNT_NO = "1234";

	private AccountDao accountDao;
	private AccountService accountService;

	@BeforeEach
	public void init() {
		accountDao = mock(AccountDao.class);
		accountService = new AccountServiceImpl(accountDao);
	}

	@Test
	public void deposit() {
		// given
		Account account = new Account(TEST_ACCOUNT_NO, 100);
		when(accountDao.findAccount(TEST_ACCOUNT_NO)).thenReturn(account);

		// when
		accountService.deposit(TEST_ACCOUNT_NO, 50);

		// then

		// accountDao.findAccount(param) 이 1번 실행 되었냐?
		// verify(accountDao, times(1)).findAccount(any(String.class));
		verify(accountDao, times(1)).findAccount(TEST_ACCOUNT_NO);
		// accountDao.updateAccount(account) 이 1번 실행 되었냐?
		verify(accountDao, times(1)).updateAccount(account);
	}

	@Test
	public void withdrawWithSufficientBalance() {
		// given
		Account account = new Account(TEST_ACCOUNT_NO, 100);
		when(accountDao.findAccount(TEST_ACCOUNT_NO)).thenReturn(account);

		// when()
		accountService.withdraw(TEST_ACCOUNT_NO, 50);

		// then
		verify(accountDao, times(1)).findAccount(TEST_ACCOUNT_NO);
		verify(accountDao, times(1)).updateAccount(account);

		assertEquals(account.getBalance(), 50);
	}

	@Test
	public void withdrawWithInsufficientBalance() {
		// given
		Account account = new Account(TEST_ACCOUNT_NO, 100);
		when(accountDao.findAccount(TEST_ACCOUNT_NO)).thenReturn(account);

		assertThrows(InsufficientBalanceException.class,
			() -> accountService.withdraw(TEST_ACCOUNT_NO, 150));
	}
}
