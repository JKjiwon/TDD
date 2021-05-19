package hello.testspring.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InterestCalculatorImplTest {

	private InterestCalculator interestCalculator;
	
	@BeforeEach
	private void init() {
		interestCalculator = new InterestCalculatorImpl();
		interestCalculator.setRate(0.05);
	}
	
	@Test
	@DisplayName("이자 계산")
	public void calculate(){
		double interest = interestCalculator.calculate(10000, 2);
		assertEquals(interest, 1000.0);
	}
	@Test
	@DisplayName("금액이 0보다 작으면 오류 발생")
	public void illegalCalculate(){
		assertThrows(IllegalArgumentException.class, () -> interestCalculator.calculate(-10000, 2));
	}

}