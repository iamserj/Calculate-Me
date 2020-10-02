package ru.iamserj.calculator;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ru.iamserj.calculator.helper.CalculationHelper;
import ru.iamserj.calculator.helper.ImageHelper;

import static ru.iamserj.calculator.helper.NumericHelper.roundDoubleToSpecifiedDecimalPlaces;
import static ru.iamserj.calculator.helper.StringHelper.*;

public class MainActivity extends Activity implements View.OnTouchListener {
	
	private static final String TAG = "123456";
	private static final String NO_BREAK_SPACE = "\u00A0";
	private static final int MAX_NUMBER = 999_999_999;
	
	private boolean resultIsEmpty = true;
	private boolean resultIsGiven = false;
	private boolean dotPresence = false;
	private boolean bracketOpened = false;
	private boolean bracketWithSignAdded = false;
	private boolean lastDigitIsNumeric = true;
	private boolean zeroWasAdded = false;
	
	private TextView tv_resultDisplay, tv_historyDisplay;
	private String currentText;
	
	// IDs of all the numeric buttons
	private int[] numericButtons = {R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9};
	
	// IDs of all the operator buttons
	private int[] operatorButtons = {R.id.bt_add, R.id.bt_backspace, R.id.bt_brackets, R.id.bt_invert, R.id.bt_clear, R.id.bt_divide, R.id.bt_dot,
			R.id.bt_equal, R.id.bt_multiply, R.id.bt_subtract};
	
	// IDs of all fake background buttons
	private int[] fakeButtons = {R.id.bt_fake_0, R.id.bt_fake_1, R.id.bt_fake_2, R.id.bt_fake_3, R.id.bt_fake_4, R.id.bt_fake_5, R.id.bt_fake_6,
			R.id.bt_fake_7, R.id.bt_fake_8, R.id.bt_fake_9, R.id.bt_fake_10, R.id.bt_fake_11, R.id.bt_fake_12, R.id.bt_fake_13, R.id.bt_fake_14,
			R.id.bt_fake_15, R.id.bt_fake_16, R.id.bt_fake_17, R.id.bt_fake_18, R.id.bt_fake_19};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bitmap bg = ImageHelper.CreateBitmapRoundCorner(getWindowManager(), getResources());
		ImageView iv_mainBackground = findViewById(R.id.iv_mainBackground);
		iv_mainBackground.setImageBitmap(bg);
		
		tv_resultDisplay = findViewById(R.id.tv_resultDisplay);
		tv_historyDisplay = findViewById(R.id.tv_historyDisplay);
		
		tv_resultDisplay.setOnClickListener(resultDisplayClickListener);
		
		for (int id : numericButtons) {
			findViewById(id).setOnTouchListener(this);
		}
		for (int id : operatorButtons) {
			findViewById(id).setOnTouchListener(this);
		}
		for (int id : fakeButtons) {
			findViewById(id).setVisibility(View.INVISIBLE);
		}
	}
	
	
	View.OnClickListener resultDisplayClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (view.getId() == R.id.tv_resultDisplay) {
				if (!resultIsEmpty && resultIsGiven) {
					ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					ClipData clip = ClipData.newPlainText("CalculateMe_Label", currentText);
					clipboard.setPrimaryClip(clip);
					Toast.makeText(getApplicationContext(), R.string.result_copied_message, Toast.LENGTH_LONG).show();
				}
			}
		}
	};
	
	
	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		
		int action = motionEvent.getAction();
		int x = (int) motionEvent.getX();
		int y = (int) motionEvent.getY();
		
		final String buttonTag = (String) view.getTag();
		final int currentFakeButtonID = getResources().getIdentifier(buttonTag, "id", getPackageName());
		final SquareButton currentFakeButton = findViewById(currentFakeButtonID);
		
		if (action == MotionEvent.ACTION_DOWN) {
			currentFakeButton.setVisibility(View.VISIBLE);
			view.animate().scaleX(0.97f).setDuration(50).start();
			view.animate().scaleY(0.97f).setDuration(50).start();
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(50);
			buttonClickListener(view);
		} else if (action == MotionEvent.ACTION_UP) {
			view.performClick();
			view.animate().scaleX(1f).setDuration(100).start();
			view.animate().scaleY(1f).setDuration(100).start();
			new android.os.Handler().postDelayed(new Runnable() {
				public void run() {
					currentFakeButton.setVisibility(View.INVISIBLE);
				}
			}, 100);
		}
		
		return true;
	}
	
	public void buttonClickListener(View view) {
		currentText = tv_resultDisplay.getText().toString();
		currentText = currentText.replaceAll(NO_BREAK_SPACE, "");     // remove no-break spaces
		int viewID = view.getId();
		if (resultIsEmpty) {
			switch (viewID) {
				case R.id.bt_1:
				case R.id.bt_2:
				case R.id.bt_3:
				case R.id.bt_4:
				case R.id.bt_5:
				case R.id.bt_6:
				case R.id.bt_7:
				case R.id.bt_8:
				case R.id.bt_9:
					resultIsEmpty = false;
					currentText = "";
					break;
			}
		}
		if (resultIsGiven) {
			switch (viewID) {
				
				case R.id.bt_0:
				case R.id.bt_dot:
					tv_historyDisplay.setText(tv_resultDisplay.getText().toString());
					tv_resultDisplay.setText("0");
					currentText = "0";
					resetBooleans();
					break;
				
				case R.id.bt_1:
				case R.id.bt_2:
				case R.id.bt_3:
				case R.id.bt_4:
				case R.id.bt_5:
				case R.id.bt_6:
				case R.id.bt_7:
				case R.id.bt_8:
				case R.id.bt_9:
					tv_historyDisplay.setText(tv_resultDisplay.getText().toString());
					resultIsEmpty = false;
					currentText = "";
					break;
			}
			if (viewID != R.id.bt_equal) {
				resultIsGiven = false;
			}
		}
		
		if (viewID != R.id.bt_dot) {
			zeroWasAdded = false;
		}
		
		switch (viewID) {
			// NUMERIC BUTTONS
			case R.id.bt_0:
				if (!resultIsEmpty) {
					typeNumber("0");
				}
				break;
			case R.id.bt_1:
				typeNumber("1");
				break;
			case R.id.bt_2:
				typeNumber("2");
				break;
			case R.id.bt_3:
				typeNumber("3");
				break;
			case R.id.bt_4:
				typeNumber("4");
				break;
			case R.id.bt_5:
				typeNumber("5");
				break;
			case R.id.bt_6:
				typeNumber("6");
				break;
			case R.id.bt_7:
				typeNumber("7");
				break;
			case R.id.bt_8:
				typeNumber("8");
				break;
			case R.id.bt_9:
				typeNumber("9");
				break;
			
			// UTILS BUTTONS
			case R.id.bt_backspace:
				operateBackspace();
				break;
			case R.id.bt_brackets:
				operateBrackets();
				break;
			case R.id.bt_invert:
				operateInvert();
				break;
			case R.id.bt_clear:
				operateClear();
				break;
			case R.id.bt_dot:
				operateDot();
				break;
			
			// OPERATION BUTTONS
			case R.id.bt_add:
				operateMath("+");
				break;
			case R.id.bt_subtract:
				operateMath("-");
				break;
			case R.id.bt_multiply:
				operateMath("*");
				break;
			case R.id.bt_divide:
				operateMath("/");
				break;
			
			case R.id.bt_equal:
				operateEqual();
				break;
		}
	}
	
	private void typeNumber(String s) {
		if (lastDigitIsNumeric) {
			boolean numberIsTooLarge = getLastNumber(currentText).length() >= String.valueOf(MAX_NUMBER).length();
			if (numberIsTooLarge) return;
		}
		if (checkLastCharIsClosingBracket()) {
			setSpannedText(currentText + "*" + s);
		} else {
			setSpannedText(currentText + s);
		}
		lastDigitIsNumeric = true;
	}
	
	private boolean checkLastCharIsClosingBracket() {
		if (currentText.length() == 0) return false;
		return currentText.charAt(currentText.length() - 1) == ')';
	}
	
	private void operateBackspace() {                                               // [BACKSPACE]
		if (resultIsEmpty) return;
		if (currentText.length() == 1) {
			resetBooleans();
			currentText = "0";
		} else if (currentText.length() > 1) {
			if (currentText.length() == 2 && checkLastNumberIsNegative(currentText)) {
				resetBooleans();
				currentText = "0";
			} else {
				if (currentText.endsWith(".")) {
					dotPresence = false;                 // remove dot
				}
				if (currentText.endsWith("(")) {
					bracketOpened = false;               // remove opening bracket
				}
				if (currentText.endsWith(")")) {
					bracketOpened = true;                // remove closing bracket
				}
				
				currentText = currentText.substring(0, currentText.length() - 1);   // just remove last digit
				
				if (currentText.equals("0")) {
					resetBooleans();
					currentText = "0";
				}
				
				lastDigitIsNumeric = checkLastCharIsNumeric(currentText);
			}
		}
		setSpannedText(currentText);
	}
	
	private void operateBrackets() {                                                // [BRACKETS ( )]
		if (resultIsEmpty) {
			resultIsEmpty = false;
			bracketOpened = true;
			lastDigitIsNumeric = false;
			setSpannedText("(");
			return;
		}
		
		if (bracketOpened) {
			if (currentText.endsWith("(")) {                    // case [(]+[)]
				int cutEnd = bracketWithSignAdded ? 2 : 1;      // number of digits to cut from end
				currentText = currentText.substring(0, currentText.length() - cutEnd);
				if (currentText.length() == 0) {
					resultIsEmpty = true;
					tv_resultDisplay.setText("0");
				} else {
					setSpannedText(currentText);
				}
				bracketWithSignAdded = false;
			} else if (!lastDigitIsNumeric) {                   // case [5]+[*]+[)]
				return;
			} else {                                            // normal case [(]+[3]+[*]+[2]+[)]
				setSpannedText(currentText + ")");
				bracketWithSignAdded = false;
				lastDigitIsNumeric = false;
			}
		} else {
			if (currentText.endsWith(".")) {
				currentText = currentText.substring(0, currentText.length() - 1);
			}
			if (currentText.endsWith(")") || lastDigitIsNumeric) {
				setSpannedText(currentText + "*(");
				bracketWithSignAdded = true;
			} else {
				setSpannedText(currentText + "(");
				bracketWithSignAdded = false;
			}
			lastDigitIsNumeric = false;
		}
		bracketOpened = !bracketOpened;
	}
	
	private void operateInvert() {                                                  // [INVERT +-]
		if (resultIsEmpty || !lastDigitIsNumeric) {
			return;
		}
		boolean isNegative = checkLastNumberIsNegative(currentText);
		String lastNumber = getLastNumber(currentText);
		int indexOfLastNumber = currentText.lastIndexOf(lastNumber);
		
		if (isNegative) {
			// remove minus from last number
			currentText = currentText.substring(0, indexOfLastNumber - 1);
			if (currentText.length() > 0 && checkLastCharIsNumeric(currentText)) {
				// if numeric before minus, do nothing
				return;
			}
			setSpannedText(currentText + lastNumber);
		} else {
			// append minus to last number
			currentText = currentText.substring(0, indexOfLastNumber);
			setSpannedText(currentText + "-" + lastNumber);
		}
	}
	
	private void operateClear() {                                                   // [CLEAR C]
		if (!currentText.equals("0")) {
			tv_historyDisplay.setText(tv_resultDisplay.getText().toString());
			tv_resultDisplay.setText("0");
		} else {
			tv_historyDisplay.setText("");
		}
		resetBooleans();
	}
	
	private void operateDot() {                                                     // [DOT .]
		if (dotPresence) {
			if (currentText.endsWith(".")) {
				dotPresence = false;
				int cutChars = zeroWasAdded ? 2 : 1;
				currentText = currentText.substring(0, currentText.length() - cutChars);
				zeroWasAdded = false;
				setSpannedText(currentText);
				if (currentText.equals("0")) {
					resultIsEmpty = true;
				}
			}
		} else {
			dotPresence = true;
			if (lastDigitIsNumeric || resultIsEmpty) {
				setSpannedText(currentText + ".");
			} else {
				zeroWasAdded = true;
				setSpannedText(currentText + "0.");        // allows dot after operational sign. [5]+[-]+[.] -> 5 - 0.
			}
			resultIsEmpty = false;
		}
	}
	
	private void operateMath(String mathSign) {                                     // [MATH + - * /]
		switch (mathSign) {
			case "+":
			case "*":
			case "/":
				if (bracketOpened && currentText.endsWith("(")) return;
		}
		currentText = removeDotIfInTheEnd(currentText);
		currentText = removeMathSignEnding(currentText);
		lastDigitIsNumeric = false;
		resultIsEmpty = false;
		dotPresence = false;
		setSpannedText(currentText + mathSign);
	}
	
	private void operateEqual() {                                                   // [EQUAL =]
		if (bracketOpened) {
			currentText = removeLastSpecifiedCharacter(currentText, '(');
		}
		currentText = removeDotIfInTheEnd(currentText);
		currentText = removeMathSignEnding(currentText);
		
		if (currentText.length() == 0) currentText = "0";
		
		tv_historyDisplay.setText(currentText);
		
		double result = CalculationHelper.evaluate(currentText);        // evaluate expression
		String resultString = removeFractionalPartIfInteger(result);    // cut floating zero, if result is integer
		String resultStringLC = resultString.toLowerCase();
		
		tv_resultDisplay.setText("0");
		resetBooleans();
		
		if (result == 0) {
			return;
		}
		
		if (resultStringLC.equals(getString(R.string.result_case_infinity)) || resultStringLC.equals(getString(R.string.result_case_nan))) {
			tv_historyDisplay.setText(R.string.show_result_infinity);
			return;
		}
		
		if (Math.abs(result) > MAX_NUMBER || resultStringLC.contains("e")) {
			tv_historyDisplay.setText(R.string.show_result_too_large);
			return;
		}
		
		// normal case
		resultIsEmpty = false;
		resultIsGiven = true;
		dotPresence = resultString.indexOf('.') != -1;
		
		if (dotPresence) {
			int integerSize = resultString.indexOf('.');
			int maxResultSize = String.valueOf(MAX_NUMBER).length();
			int decimalSize = maxResultSize - integerSize;
			result = roundDoubleToSpecifiedDecimalPlaces(result, decimalSize);
			resultString = removeFractionalPartIfInteger(result);
		}
		
		tv_resultDisplay.setText(resultString);
	}
	
	
	private void resetBooleans() {
		resultIsEmpty = true;
		resultIsGiven = false;
		dotPresence = false;
		bracketOpened = false;
		bracketWithSignAdded = false;
		lastDigitIsNumeric = true;
		zeroWasAdded = false;
	}
	
	
	private void setSpannedText(String str) {
		tv_resultDisplay.setText("");
		
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
				case '+':
				case '-':
				case '*':
				case '/':
					Spannable spacing = new SpannableString(NO_BREAK_SPACE);
					spacing.setSpan(new RelativeSizeSpan(0.5f), 0, spacing.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					tv_resultDisplay.append(spacing);
					
					Spannable coloredSign = new SpannableString(String.valueOf(str.charAt(i)));
					coloredSign.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.display_operation_sign)), 0, coloredSign.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					tv_resultDisplay.append(coloredSign);
					
					spacing.setSpan(new RelativeSizeSpan(0.5f), 0, spacing.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					tv_resultDisplay.append(spacing);
					break;
				default:
					tv_resultDisplay.append(String.valueOf(str.charAt(i)));
			}
		}
	}
}

// Possible improvements:
// * add little apostrophes 1'000'000
// * add backspace 'holded state' listener
// * separate [1] with a space, as in electronics