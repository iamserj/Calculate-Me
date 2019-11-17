package ru.iamserj.calculator;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

//public class MainActivity extends Activity implements OnClickListener, OnTouchListener {
public class MainActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "123456";
	//DecimalFormat decimalFormat = new DecimalFormat("#.##########");
	
	private static final char CLEAR = 'C' ;
	private static final char ADDITION = '+';
	private static final char SUBTRACTION = '-';
	private static final char MULTIPLICATION = '*';
	private static final char DIVISION = '/';
	private static final char EMPTY = '0';
	private static final char DOT = '.';
	
	private double valueOne = 0; //Double.MIN_VALUE;
	private double valueTwo = 0; //Double.MIN_VALUE;
	
	private boolean resultIsNegative = false;
	private boolean resultIsEmpty = true;
	//private boolean finalResult = false;
	private boolean clearedOnce = false;
	private boolean dotPresence = false;
	private boolean bracketOpened = false;
	private boolean bracketWithSignAdded = false;
	private boolean lastDigitIsNumeric = true;
	private boolean zeroWasAdded = false;
	
	private TextView textDisplay, textHistory;
	private String currentText;
	
	//private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
	//private Button buttonBackspace, buttonBrackets, buttonInvert, buttonClear, buttonDot, buttonEqual;
	//private Button buttonAdd, buttonDivide, buttonMultiply, buttonSubtract;

	// IDs of all the numeric buttons
	private int[] numericButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
	
	// IDs of all the operator buttons
	private int[] operatorButtons = {R.id.buttonAdd, R.id.buttonBackspace, R.id.buttonBrackets, R.id.buttonInvert, R.id.buttonClear, R.id.buttonDivide, R.id.buttonDot, R.id.buttonEqual, R.id.buttonMultiply, R.id.buttonSubtract};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
		setContentView(R.layout.activity_main);
		
		textDisplay = findViewById(R.id.resultDisplay);
		textHistory = findViewById(R.id.historyDisplay);
		
		textDisplay.setOnClickListener(this);

		for (int id : numericButtons) {
			findViewById(id).setOnClickListener(this);
			//findViewById(id).setOnTouchListener(handleTouch);
		}
		for (int id : operatorButtons) {
			findViewById(id).setOnClickListener(this);
			//findViewById(id).setOnTouchListener(handleTouch);
		}
		
	}

	// TODO: add touch animation
	/*@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		
		if (action == MotionEvent.ACTION_DOWN) {
			view.animate().scaleXBy(100f).setDuration(5000).start();
			view.animate().scaleYBy(100f).setDuration(5000).start();
			return true;
		} else if (action == MotionEvent.ACTION_UP) {
			view.animate().cancel();
			view.animate().scaleX(1f).setDuration(1000).start();
			view.animate().scaleY(1f).setDuration(1000).start();
			return true;
		}

		return false;
	}*/
	
	@Override
	public void onClick(View view) {
		currentText = textDisplay.getText().toString();
		currentText = currentText.replaceAll("\u00A0","");
		
		if (view.getId() == R.id.resultDisplay) {
			ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("MyCalc Copied Result", currentText);
			clipboard.setPrimaryClip(clip);
			Toast.makeText(this, "Result copied", Toast.LENGTH_SHORT).show();
			return;
		} else {
			/*if (finalResult) {				// TODO: what is it, finalResult?
				finalResult = false;
				textHistory.setText(currentText);
				resultIsEmpty = true;
				currentText = "0";
			}*/
		}
		
		if (resultIsEmpty) {
			switch (view.getId()) {
				//case R.id.button0:
				case R.id.button1:
				case R.id.button2:
				case R.id.button3:
				case R.id.button4:
				case R.id.button5:
				case R.id.button6:
				case R.id.button7:
				case R.id.button8:
				case R.id.button9:
					resultIsEmpty = false;
					currentText = "";
					break;
			}
		}
		
		if (view.getId() != R.id.buttonDot) {
			zeroWasAdded = false;
		}

		switch (view.getId()) {
			
			case R.id.button0:
				if (resultIsEmpty) break;
				setSpannedText(currentText + "0");
				// TODO: don't allow 2 zeros in a row if it is a start of non-float number 0005 -> 5
				lastDigitIsNumeric = true;
				break;
			case R.id.button1:
				setSpannedText(currentText + "1");
				lastDigitIsNumeric = true;
				break;
			case R.id.button2:
				setSpannedText(currentText + "2");
				lastDigitIsNumeric = true;
				break;
			case R.id.button3:
				setSpannedText(currentText + "3");
				lastDigitIsNumeric = true;
				break;
			case R.id.button4:
				setSpannedText(currentText + "4");
				lastDigitIsNumeric = true;
				break;
			case R.id.button5:
				setSpannedText(currentText + "5");
				lastDigitIsNumeric = true;
				break;
			case R.id.button6:
				setSpannedText(currentText + "6");
				lastDigitIsNumeric = true;
				break;
			case R.id.button7:
				setSpannedText(currentText + "7");
				lastDigitIsNumeric = true;
				break;
			case R.id.button8:
				setSpannedText(currentText + "8");
				lastDigitIsNumeric = true;
				break;
			case R.id.button9:
				setSpannedText(currentText + "9");
				lastDigitIsNumeric = true;
				break;
			// TODO: if (last is closed bracket) add *: (5*8)9 -> (5*8) * 9
			// TODO: number length 10 digits maximum
			// TODO: add little colons 1,000,000
			

			// UTILS BUTTONS
			// <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-
			case R.id.buttonBackspace:
				if (resultIsEmpty) break;
				
				if (currentText.length() == 1) {
					resultIsEmpty = true;
					bracketOpened = false;
					dotPresence = false;
					currentText = getResources().getString(R.string.zero);                  // set 0
				} else if (currentText.length() > 1) {
					//String negativeSymbol = getResources().getString(R.string.sub);       // subtraction sign
					if (currentText.length() == 2 && resultIsNegative) {
						resultIsNegative = false;
						resultIsEmpty = true;
						currentText = getResources().getString(R.string.zero);              // set 0
					} else {
						if (currentText.endsWith(".")) dotPresence = false;                 // remove dot
						if (currentText.endsWith("(")) bracketOpened = false;               // remove open bracket
						if (currentText.endsWith(")")) bracketOpened = true;                // remove close bracket
						currentText = currentText.substring(0, currentText.length() - 1);   // just remove last digit
						if (Character.isDigit(currentText.charAt(currentText.length()-1))) {
							lastDigitIsNumeric = true;
						}
					}
				}
				setSpannedText(currentText);
				break;
				
			// ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )  ( )
			case R.id.buttonBrackets:
				if (resultIsEmpty) {
					resultIsEmpty = false;
					setSpannedText("(");
					bracketOpened = true;
					break;
				}
				
				if (bracketOpened) {
					if (currentText.endsWith("(")) {                    // case "()"
						int cutEnd = bracketWithSignAdded ? 2 : 1;      // number of digits to cut from end
						currentText = currentText.substring(0, currentText.length() - cutEnd);
						if (currentText.length() == 0) {
							resultIsEmpty = true;
							textDisplay.setText(getResources().getString(R.string.zero));
						} else {
							setSpannedText(currentText);
						}
						bracketWithSignAdded = false;
					} else if (!lastDigitIsNumeric) {                   // case (3+)
						break;
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
				
				break;
			
			// +-  	+-  +-  +-  +-  +-  +-  +-  +-  +-  +-  +-  +-  +-  +-  +-  +-
			case R.id.buttonInvert:
				// TODO: apply invert only to last number in expression
				if (resultIsNegative) {
					setSpannedText(currentText.substring(1));
				} else {
					setSpannedText("-" + currentText);
				}
				resultIsNegative = !resultIsNegative;
				break;
			
			// CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR
			case R.id.buttonClear:
				lastDigitIsNumeric = false;
				
				// TODO: clean conditions
				if (!clearedOnce) {
					clearedOnce = true;
					resultIsEmpty = true;
					dotPresence = false;
					resultIsNegative = false;
					textDisplay.setText(getResources().getString(R.string.zero));
				} else {
					clearedOnce = false;
					resultIsEmpty = true;
					dotPresence = false;
					resultIsNegative = false;
					textDisplay.setText(getResources().getString(R.string.zero));
					textHistory.setText("");
				}
				break;
				
			// .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
			case R.id.buttonDot:
				
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
						setSpannedText(currentText + "0.");		// allows dot after operational sign. 5+. -> 5+0.
					}
					resultIsEmpty = false;
				}
				// TODO: don't allow dot after result is given
				break;
				
			// OPERATION BUTTONS
			// +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +  +
			case R.id.buttonAdd:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				addOperationSign("+");
				break;
			
			// -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -
			case R.id.buttonSubtract:
				//if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				addOperationSign("-");
				break;
				
			// *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
			case R.id.buttonMultiply:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				addOperationSign("*");
				break;
				
			// /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /  /
			case R.id.buttonDivide:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				addOperationSign("/");
				break;
				
			
			// =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =
			case R.id.buttonEqual:
				if (resultIsEmpty) break;
				
				lastDigitIsNumeric = false;
				// TODO: if tap operationals after final result, count with final result, set booleans lastDigitIsNumeric, dotPresence, resultIsNegative
				// TODO: if tap numerics after final result, clean and start new expression
				dotPresence = false;
				resultIsNegative = false;
				//finalResult = true;

				if (bracketOpened) {
					int lastIndex = currentText.lastIndexOf("(");
        			if (lastIndex != -1) {
						String beginString = currentText.substring(0, lastIndex);
						String endString = currentText.substring(lastIndex + 1);
						currentText = beginString + endString;
					}
				}
				currentText = trimMathSignEnding(currentText);		// trim [+ - * /] endings
				double result = Calculate.evaluate(currentText);	// evaluate expression
				String resFormatted = cutFloatIfInteger(result);    // cut floating zero from integer
				
				// save real result
				// cut showable result

				setSpannedText(resFormatted);
				textHistory.setText(currentText);
				break;
			
			default:
				Toast.makeText(this, "wtf?", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void addOperationSign(String sign) {
		char lastSymbol = currentText.charAt(currentText.length()-1);
		
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
		textDisplay.setText("");
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
					textDisplay.append(spacing);
					
					Spannable coloredSign = new SpannableString(String.valueOf(str.charAt(i)));
					coloredSign.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.display_operation_sign)), 0, coloredSign.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					textDisplay.append(coloredSign);
					
					spacing.setSpan(new RelativeSizeSpan(0.5f), 0, spacing.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					textDisplay.append(spacing);
					break;
				default:
					textDisplay.append(String.valueOf(str.charAt(i)));
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

// TODO: move common buttons style to styles.xml Button.Numeric + other buttons
// TODO: add apostrophes
// TODO: lighten bg color or make all dark, lighten buttons color, bigger button numbers font size. Bigger display font size
// TODO: move utils function to UTILS class
// TODO: lessen result font if viewport is small?
// TODO: refactor all