package hello.testspring.bank.domain;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Account {

	private String accountNo;
	private double balance; // 잔액

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account)o;
		return Objects.equals(this.accountNo, account.accountNo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNo);
	}
}
