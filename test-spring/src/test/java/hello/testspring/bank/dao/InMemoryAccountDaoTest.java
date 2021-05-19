package hello.testspring.bank.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.testspring.bank.exception.AccountNotFoundException;
import hello.testspring.bank.exception.DuplicateAccountException;
import hello.testspring.bank.domain.Account;

class InMemoryAccountDaoTest {

	private static final String EXISTING_ACCOUNT_NO = "1234";
	private static final String NEW_ACCOUNT_NO = "5678";

	private Account existingAccount;
	private Account newAccount;
	private InMemoryAccountDao accountDao;

	@BeforeEach
	public void init() {
		existingAccount = new Account(EXISTING_ACCOUNT_NO, 100);
		newAccount = new Account(NEW_ACCOUNT_NO, 200);
		accountDao = new InMemoryAccountDao();
		accountDao.createAccount(existingAccount);
	}

	@Test
	@DisplayName("계좌가 존재하는지 테스트")
	public void accountExists() {
		assertTrue(accountDao.accountExists(EXISTING_ACCOUNT_NO));
		assertFalse(accountDao.accountExists(NEW_ACCOUNT_NO));
	}

	@Test
	@DisplayName("새로운 계좌 생성")
	public void createNewAccount() {
		accountDao.createAccount(newAccount);
		assertEquals(accountDao.findAccount(NEW_ACCOUNT_NO), newAccount);
	}

	@Test
	@DisplayName("이미 존재하는 계좌를 생성하면 오류가 발생")
	public void createDuplicateAccount() {
		assertThrows(DuplicateAccountException.class,
			() -> accountDao.createAccount(existingAccount));
	}

	@Test
	@DisplayName("계좌 업데이트")
	public void updateExistedAccount() {
		existingAccount.setBalance(150);
		accountDao.updateAccount(existingAccount);
		Account updatedAccount = accountDao.findAccount(EXISTING_ACCOUNT_NO);
		assertEquals(updatedAccount.getBalance(), 150);
	}

	@Test
	@DisplayName("존재하지 않는 계좌를 업데이트하면 오류가 발생")
	public void updatedNotExistedAccount() {
		assertThrows(AccountNotFoundException.class,
			() -> accountDao.updateAccount(newAccount));
	}

	@Test
	@DisplayName("계좌 삭제")
	public void removeExistedAccount() {
		accountDao.removeAccount(existingAccount);
		assertFalse(accountDao.accountExists(EXISTING_ACCOUNT_NO));
	}

	@Test
	@DisplayName("존재하지 않는 계좌를 삭제하면 오류 발생")
	public void removeNotExistedAccount() {
		assertThrows(AccountNotFoundException.class,
			() -> accountDao.removeAccount(newAccount));
	}

	@Test
	@DisplayName("계좌 찾기")
	public void findExistedAccount() {
		Account account = accountDao.findAccount(EXISTING_ACCOUNT_NO);
		assertEquals(account, existingAccount);
	}

	@Test
	@DisplayName("존재하지 않는 계좌 찾기하면 오류가 발생")
	public void findNotExistedAccount() {
		assertThrows(AccountNotFoundException.class,
			() -> accountDao.findAccount(NEW_ACCOUNT_NO));
	}
}