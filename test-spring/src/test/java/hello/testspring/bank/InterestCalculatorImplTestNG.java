package hello.testspring.bank;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InterestCalculatorImplTestNG {

	private InterestCalculator interestCalculator;

	@BeforeMethod
	public void init() {
		interestCalculator = new InterestCalculatorImpl();
		interestCalculator.setRate(0.05);
	}

	@Test
	public void calculate1() {
		double interest = interestCalculator.calculate(10000, 2);
		assertEquals(interest, 1000.0);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void illegalCalculate1() {
		interestCalculator.calculate(-10000, 2);
	}

	@DataProvider(name = "legal")
	public Object[][] createLegalInterestParameters() {
		return new Object[][] {new Object[] {10000, 2, 1000.0}};
	}

	@DataProvider(name = "illegal")
	public Object[][] createIllegalInterestParameters() {
		return new Object[][] {
			new Object[] {-10000, 2},
			new Object[] {10000, -2},
			new Object[] {-10000, -2}
		};
	}

	@Test(dataProvider = "legal")
	public void calculate2(double amount, double year, double result) {
		double interest = interestCalculator.calculate(amount, year);
		assertEquals(interest, result);
	}

	@Test(dataProvider = "illegal",
		expectedExceptions = IllegalArgumentException.class)
	public void illegalCalculate2(double amount, double year) {
		interestCalculator.calculate(amount, year);
	}
}
