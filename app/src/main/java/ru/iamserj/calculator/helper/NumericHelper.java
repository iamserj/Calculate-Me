package ru.iamserj.calculator.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class NumericHelper {
	
	/**
	 * Description: round double number to given number of decimal places
	 * Example: fun(3.123456789, 3) -> 3.123
	 * Example: fun(3.123456789, 5) -> 3.12346
	 * Example: fun(3.1, 5) -> 3.1
	 *
	 * @param value  given binary number
	 * @param places number of places after floating point
	 * @return double value with given number of decimal places
	 */
	public static double roundDoubleToSpecifiedDecimalPlaces(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
}
