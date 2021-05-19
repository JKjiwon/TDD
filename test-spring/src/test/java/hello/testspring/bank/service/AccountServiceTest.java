package hello.testspring.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.testspring.bank.dao.InMemoryAccountDao;
import hello.testspring.bank.exception.AccountNotFoundException;

public class AccountServiceTest {

	private static final String TEST_ACCOUNT_NO = "1234";
	private AccountService accountService;

	@BeforeEach
	public void init() {
		accountService = new AccountServiceImpl(new InMemoryAccountDao());
		accountService.createAccount(TEST_ACCOUNT_NO);
		accountService.deposit(TEST_ACCOUNT_NO, 100);
	}

	@Test
	public void deposit() {
		accountService.deposit(TEST_ACCOUNT_NO, 50);
		assertEquals(accountService.getBalance(TEST_ACCOUNT_NO), 150);
	}

	@Test
	public void withDraw() {
		accountService.withdraw(TEST_ACCOUNT_NO, 50);
		assertEquals(accountService.getBalance(TEST_ACCOUNT_NO), 50);
	}

	@Test
	@DisplayName("삭제한 계죄를 조회시 AccountNotFoundException 이 발생")
	public void cleanup() {
		accountService.removeAccount(TEST_ACCOUNT_NO);

		assertThrows(AccountNotFoundException.class,
			() -> accountService.getBalance(TEST_ACCOUNT_NO));
	}
}
