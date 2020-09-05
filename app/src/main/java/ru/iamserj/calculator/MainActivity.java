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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ru.iamserj.calculator.helper.CalculationHelper;
import ru.iamserj.calculator.helper.ImageHelper;

public class MainActivity extends Activity implements View.OnTouchListener {
	
	private static final String TAG = "123456";
	
	private boolean resultIsEmpty = true;
	private boolean dotPresence = false;
	private boolean bracketOpened = false;
	private boolean bracketWithSignAdded = false;
	private boolean lastDigitIsNumeric = true;
	private boolean zeroWasAdded = false;
	
	private ImageView iv_mainBackground;
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
		iv_mainBackground = findViewById(R.id.iv_mainBackground);
		iv_mainBackground.setImageBitmap(bg);
		bg = null;
		
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
				/*if (resultIsEmpty || currentText) {
					// TODO: if result is empty (0) or INFINITY, don't copy it
				}*/
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("MyCalc Copied Result", currentText);
				clipboard.setPrimaryClip(clip);
				Toast.makeText(getApplicationContext(), "Result Copied", Toast.LENGTH_SHORT).show();
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
		final SquareButton currentFakeButton = (SquareButton) findViewById(currentFakeButtonID);
		
		if (action == MotionEvent.ACTION_DOWN) {
			currentFakeButton.setVisibility(View.VISIBLE);
			view.animate().scaleX(0.975f).setDuration(50).start();
			view.animate().scaleY(0.975f).setDuration(50).start();
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(50);
			buttonClickListener(view);
		} else if (action == MotionEvent.ACTION_UP) {
			view.performClick();
			//view.animate().cancel();
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
		currentText = currentText.replaceAll("\u00A0", "");     // remove no-break spaces
		
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
		
		if (viewID != R.id.bt_dot) {
			zeroWasAdded = false;
		}
		
		switch (viewID) {
			
			case R.id.bt_0:
				if (!resultIsEmpty) {
					setSpannedText(currentText + "0");
					lastDigitIsNumeric = true;
				}
				break;
			case R.id.bt_1:
				setSpannedText(currentText + "1");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_2:
				setSpannedText(currentText + "2");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_3:
				setSpannedText(currentText + "3");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_4:
				setSpannedText(currentText + "4");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_5:
				setSpannedText(currentText + "5");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_6:
				setSpannedText(currentText + "6");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_7:
				setSpannedText(currentText + "7");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_8:
				setSpannedText(currentText + "8");
				lastDigitIsNumeric = true;
				break;
			case R.id.bt_9:
				setSpannedText(currentText + "9");
				lastDigitIsNumeric = true;
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
				operateAdd();
				break;
			case R.id.bt_subtract:
				operateSubtract();
				break;
			case R.id.bt_multiply:
				operateMultiply();
				break;
			case R.id.bt_divide:
				operateDivide();
				break;
			
			case R.id.bt_equal:
				operateEqual();
				break;
		}
	}
	
	
	private void operateBackspace() {                                               // [BACKSPACE]
		if (resultIsEmpty) return;
		
		if (currentText.length() == 1) {
			resultIsEmpty = true;
			bracketOpened = false;
			dotPresence = false;
			currentText = getResources().getString(R.string.zero);                  // set 0
		} else if (currentText.length() > 1) {
			//String negativeSymbol = getResources().getString(R.string.sub);       // subtraction sign
			if (currentText.length() == 2 && checkNumberIsNegative()) {
				resultIsEmpty = true;
				currentText = getResources().getString(R.string.zero);              // set 0
			} else {
				if (currentText.endsWith("."))
					dotPresence = false;                 // remove dot
				if (currentText.endsWith("("))
					bracketOpened = false;               // remove open bracket
				if (currentText.endsWith(")"))
					bracketOpened = true;                // remove close bracket
				currentText = currentText.substring(0, currentText.length() - 1);   // just remove last digit
				if (Character.isDigit(currentText.charAt(currentText.length() - 1))) {
					lastDigitIsNumeric = true;
				}
			}
		}
		setSpannedText(currentText);
	}
	
	private void operateBrackets() {                                                // [BRACKETS]
		if (resultIsEmpty) {
			resultIsEmpty = false;
			setSpannedText("(");
			bracketOpened = true;
			return;
		}
		
		if (bracketOpened) {
			if (currentText.endsWith("(")) {                    // case "(" + ")"
				int cutEnd = bracketWithSignAdded ? 2 : 1;      // number of digits to cut from end
				currentText = currentText.substring(0, currentText.length() - cutEnd);
				if (currentText.length() == 0) {
					resultIsEmpty = true;
					tv_resultDisplay.setText(getResources().getString(R.string.zero));
				} else {
					setSpannedText(currentText);
				}
				bracketWithSignAdded = false;
			} else if (!lastDigitIsNumeric) {                   // case (3+)
				return;
			} else {                                            // normal case (3+2)
				setSpannedText(currentText + ")");
				bracketWithSignAdded = false;
				lastDigitIsNumeric = false;
			}
		} else {
			if (currentText.endsWith(")") || lastDigitIsNumeric) {
				setSpannedText(currentText + "*(");
				bracketWithSignAdded = true;
			} else {
				setSpannedText(currentText + "(");
				bracketWithSignAdded = false;
			}
		}
		bracketOpened = !bracketOpened;
		//Log.d("---------", bracketOpened+"");
	}
	
	private void operateInvert() {                                                  // [INVERT +-]
		if (resultIsEmpty) return;
		
		if (currentText.length() == 1 && lastDigitIsNumeric) {
			setSpannedText("-" + currentText);
			return;
		}
		
		boolean isNegative = false;
		
		if (lastDigitIsNumeric) {
			isNegative = checkNumberIsNegative();
			Log.d(TAG, "operateInvert isNegative: " + isNegative);
		}
		
		if (isNegative) {
			//currentText-getLastNumber-"-"+getLastNumber
			String lastNumber = getLastNumber();
			int indexOfLastNumber = currentText.indexOf(lastNumber);
			String currentTextWithoutLastNumberAndNegative = currentText.substring(0, indexOfLastNumber - 1);
			setSpannedText(currentTextWithoutLastNumberAndNegative + lastNumber);
		} else {
			//currentText-getLastNumber+"-"+getLastNumber
			String lastNumber = getLastNumber();
			Log.d(TAG, "operateInvert lastNumber: " + lastNumber);
			int indexOfLastNumber = currentText.indexOf(lastNumber);
			Log.d(TAG, "operateInvert indexOfLastNumber: " + indexOfLastNumber);
			String currentTextWithoutLastNumber = currentText.substring(0, indexOfLastNumber);
			Log.d(TAG, "operateInvert currentTextWithoutLastNumber: " + currentTextWithoutLastNumber);
			setSpannedText(currentTextWithoutLastNumber + "-" + lastNumber);
		}
		
	}
	
	private boolean checkNumberIsNegative() {
		if (!lastDigitIsNumeric) {
			Log.d(TAG, "checkNumberIsNegative: case 1");
			return false;
		}
		if (currentText.length() == 1) {
			Log.d(TAG, "checkNumberIsNegative: case 2");
			return false;
		}
		
		String lastNumber = getLastNumber();
		int lastNumberPosition = currentText.indexOf(lastNumber);
		
		if (lastNumberPosition == 0) {  // case "456", index 0, no negative sign
			Log.d(TAG, "checkNumberIsNegative: case 3");
			return false;
		}
		char previousSymbol = currentText.charAt(lastNumberPosition - 1);
		
		return previousSymbol == '-';
	}
	
	private String getLastNumber() {
		
		String[] n = currentText.split("");     // array of strings
		StringBuffer f = new StringBuffer();    // buffer to store numbers
		for (int i = n.length - 1; i >= 0; i--) {
			if ((n[i].matches("[0-9]+"))) {     // validating numbers
				f.insert(0, n[i]);              // add character
			} else {
				return f.toString();
			}
		}
		return "0";
	}
	
	private void operateClear() {                                                   // [CLEAR C]
		lastDigitIsNumeric = false;
		resultIsEmpty = true;
		dotPresence = false;
		// TODO: clean all conditions
		//Log.d(TAG, "operateClear: " + currentText);
		if (!currentText.equals("0")) {
			tv_resultDisplay.setText(getResources().getString(R.string.zero));
		} else {
			tv_historyDisplay.setText("");
		}
		
	}
	
	private void operateDot() {                                                     // [DOT .]
		// TODO: check for dot presence in last number. E.g. after backspace use
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
				setSpannedText(currentText + "0.");        // allows dot after operational sign. 5+. -> 5+0.
			}
			resultIsEmpty = false;
		}
		// TODO: don't allow dot after result is given
	}
	
	
	private void operateAdd() {                                                     // [ADD +]
		if (bracketOpened && currentText.endsWith("(")) return;
		if (dotPresence && currentText.endsWith("."))
			currentText = currentText.substring(0, currentText.length() - 1);
		lastDigitIsNumeric = false;
		
		if (resultIsEmpty) {
			resultIsEmpty = false;
		}
		dotPresence = false;
		addOperationSign("+");
	}
	
	private void operateSubtract() {                                                // [SUBTRACT -]
		//if (bracketOpened && currentText.endsWith("(")) break;
		if (dotPresence && currentText.endsWith("."))
			currentText = currentText.substring(0, currentText.length() - 1);
		lastDigitIsNumeric = false;
		if (resultIsEmpty) {
			resultIsEmpty = false;
		}
		dotPresence = false;
		addOperationSign("-");
	}
	
	private void operateMultiply() {                                                // [MULTIPLY *]
		if (bracketOpened && currentText.endsWith("(")) return;
		if (dotPresence && currentText.endsWith("."))
			currentText = currentText.substring(0, currentText.length() - 1);
		lastDigitIsNumeric = false;
		if (resultIsEmpty) {
			resultIsEmpty = false;
		}
		dotPresence = false;
		addOperationSign("*");
	}
	
	private void operateDivide() {                                                  // [DIVIDE /]
		if (bracketOpened && currentText.endsWith("(")) return;
		if (dotPresence && currentText.endsWith("."))
			currentText = currentText.substring(0, currentText.length() - 1);
		lastDigitIsNumeric = false;
		if (resultIsEmpty) {
			resultIsEmpty = false;
		}
		dotPresence = false;
		addOperationSign("/");
	}
	
	private void operateEqual() {                                                   // [EQUAL =]
		// if bracket is not closed, cut it out
		if (bracketOpened) {
			Log.d(TAG, "onClick: Opened");
			int lastIndex = currentText.lastIndexOf("(");
			if (lastIndex != -1) {
				Log.d(TAG, "onClick: Opened1 " + lastIndex);
				String beginString = currentText.substring(0, lastIndex);
				String endString = currentText.substring(lastIndex + 1);
				currentText = beginString + endString;
				Log.d(TAG, "onClick: Opened2 " + currentText);
			}
		}
		currentText = trimMathSignEnding(currentText);      // trim [+ - * /] endings
		tv_historyDisplay.setText(currentText);
		double result = CalculationHelper.evaluate(currentText);    // evaluate expression
		String resFormatted = cutFloatIfInteger(result);    // cut floating zero from integer
		
		// save real result
		// cut showable result
		
		//setSpannedText(resFormatted);
		if (resFormatted.toLowerCase().equals("infinity")) {
			tv_resultDisplay.setText(R.string.divide_by_zero);
			resetBooleans();
			// TODO: dont work with this text as a result
			// TODO: if number is very big (123E4) dont work with this text as a result
			// TODO: set this text as a history and clear result and booleans
		} else {
			tv_resultDisplay.setText(resFormatted);
		}
		
		// Reset booleans
		lastDigitIsNumeric = false;
		// TODO: if tap operationals after final result, count with final result, reset booleans lastDigitIsNumeric, dotPresence, resultIsNegative
		// TODO: if tap numerics after final result, clean and start new expression
		dotPresence = false;
		
	}
	
	private void resetBooleans() {
		resultIsEmpty = true;
		dotPresence = false;
		bracketOpened = false;
		bracketWithSignAdded = false;
		lastDigitIsNumeric = true;
		zeroWasAdded = false;
	}
	
	
	private void addOperationSign(String sign) {
		char lastSymbol = currentText.charAt(currentText.length() - 1);
		
		switch (lastSymbol) {
			case '+':
			case '-':
			case '*':
			case '/':
				currentText = currentText.substring(0, currentText.length() - 1);
				break;
		}
		setSpannedText(currentText + sign);
	}
	
	private void setSpannedText(String str) {
		tv_resultDisplay.setText("");
		
		//str = str.replaceAll("\u00A0","");
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
				case '+':
				case '-':
				case '*':
				case '/':
					Spannable spacing = new SpannableString("\u00A0");
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
		
		// TODO:
		// if result is very long, trim it to less digits + E
		// if entered number is very long, don't allow to enter (if trying to enter new number, return operation)
		
		// On entry set max digits = 10 symb
		
		
		// If you just want to use AWT, then use Graphics.getFontMetrics (optionally specifying the font, for a non-default one) to get a FontMetrics
		// then FontMetrics.stringWidth to find the width for the specified string.
		
		// 1) get currentText width
/*		Graphics g;
		String s = textDisplay.getText().toString();
		int textWidth = g.getFontMetrics().stringWidth(s);

		int textDisplayWidth;

		// 2) get result view width
		LinearLayout layout = (LinearLayout)findViewById(R.id.YOUD VIEW ID);
		ViewTreeObserver vto = layout.getViewTreeObserver();
		//if (viewTreeObserver.isAlive()) {
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		//vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					this.layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					this.layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				// textDisplayWidth = layout.getWidth();
				textDisplayWidth = layout.getMeasuredWidth();
			}
		});
		//}
*/

		/*

		yourView.postDelayed(new Runnable() {
			@Override
			public void run() {
				yourView.invalidate();
				System.out.println("Width = " + yourView.getWidth());
			}
		}, 1);

		*/


	/*	if (textWidth > textDisplayWidth) {				// text doesn't fit
			//			a) align text to left side
			//			b) cut tail

		} else {
			//			a) align text to right side
			//			b) block exceed user entry

		}

	*/
	}
	
	
	private String cutFloatIfInteger(double result) {
		// cut floating zero from integer
		int j = (int) result;
		return (result == j) ? String.valueOf(j) : String.valueOf(result);
		
		
	}
	
	private String trimMathSignEnding(String str) {
		if (str.length() == 0) {
			resultIsEmpty = true;
			tv_resultDisplay.setText(getResources().getString(R.string.zero));
			return str;
		}
		switch (str.charAt(str.length() - 1)) {
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

// TODO: when typing numbers after result is shown, don't concatenate, clear result text and booleans
// TODO: if (last is closed bracket) add *: (5*8)9 -> (5*8) * 9
// TODO: if text is 0. don't allow to add ( or remove .
// TODO: number length 10 digits maximum. Try android:ems="10" (10 letters "M")
// TODO: add little colons 1,000,000 or apostrophes
// TODO: add backspace 'holded' listener
// TODO: history show only last actions, e.g. 7 + 5 / 2
// TODO: move utils function to UTILS class
// TODO: lessen result font if viewport is small?
// TODO: отделить единицу пробелом, как в электронике
// TODO: refactor all
// TODO: clean comments, Logs