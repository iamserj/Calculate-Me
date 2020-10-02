package ru.iamserj.calculator.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringHelper {
	
	/**
	 * Description: check last character in string is numeric
	 * Example: "45+9/(54.5)" -> false
	 * Example: "45+9/(54.5)+14" -> true
	 *
	 * @param str incoming string value
	 * @return boolean is last character a numeric
	 */
	public static boolean checkLastCharIsNumeric(String str) {
		return Character.isDigit(str.charAt(str.length() - 1));
	}
	
	/**
	 * Description: get last number from given string
	 * Example: "45+9/54.5" -> "54.5"
	 *
	 * @param str incoming string value
	 * @return String presentation of last founded number
	 */
	public static String getLastNumber(String str) {
		Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(str);
		double result = 0;
		while (m.find()) {
			result = Double.parseDouble(m.group(1));
		}
		return removeFractionalPartIfInteger(result);
	}
	
	/**
	 * Description: check last number in string is negative
	 * Example: "45+9/54.5*(-3" -> true
	 * Example: "45+9/54.5*(3" -> false
	 *
	 * @param str incoming string value
	 * @return boolean true for negative number, false for positive number
	 */
	public static boolean checkLastNumberIsNegative(String str) {
		if (!checkLastCharIsNumeric(str)) return false;
		if (str.length() == 1) return false;
		String lastNumber = getLastNumber(str);
		int lastNumberPosition = str.lastIndexOf(lastNumber);
		if (lastNumberPosition == 0) return false;  // case "12345", index 0, no negative sign
		char previousSymbol = str.charAt(lastNumberPosition - 1);
		return previousSymbol == '-';
	}
	
	/**
	 * Description: Cut floating zero from integer
	 * Example: 32.0 -> "32"
	 * Example: 23.7 -> "23.7"
	 *
	 * @param value in double with or without zero decimal value
	 * @return String without zero decimal value
	 */
	public static String removeFractionalPartIfInteger(double value) {
		int j = (int) value;
		if (value == j) {
			return String.valueOf(j);
		} else {
			return String.valueOf(value);
		}
	}
	
	/**
	 * Description: remove dot from end if string ends with it
	 * Example: "8*15-32." -> "8*15-32"
	 *
	 * @param str incoming string value
	 * @return String without ending dot, if present
	 */
	public static String removeDotIfInTheEnd(String str) {
		if (str.endsWith(".")) {
			return str.substring(0, str.length() - 1);
		} else {
			return str;
		}
	}
	
	/**
	 * Description: remove opening bracket from string
	 * Example: "8*15-32." -> "8*15-32"
	 *
	 * @param str incoming string value
	 * @param c specified character
	 * @return String without ending dot, if present
	 */
	public static String removeLastSpecifiedCharacter(String str, char c) {
		int lastIndex = str.lastIndexOf(c);
		if (lastIndex != -1) {
			String beginString = str.substring(0, lastIndex);
			String endString = str.substring(lastIndex + 1);
			str = beginString + endString;
		}
		return str;
	}
	
	/**
	 * Description: delete math sign [+ - * /] ending
	 * Example: "8*15-32+" -> "8*15-32"
	 * Example: "8*15-32" -> "8*15-32"
	 *
	 * @param str incoming string value
	 * @return String without math sign at the end if present
	 */
	public static String removeMathSignEnding(String str) {
		if (str.length() == 0) return "";
		char lastSymbol = str.charAt(str.length() - 1);
		switch (lastSymbol) {
			case '+':
			case '-':
			case '*':
			case '/':
				str = str.substring(0, str.length() - 1);
				break;
		}
		return str;
	}
}
