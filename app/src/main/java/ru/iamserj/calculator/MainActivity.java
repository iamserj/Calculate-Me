package ru.iamserj.calculator;


import android.app.Activity;
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
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	//public static final String SQUAREROOT = "√";
	//public static final String SQUARED = "x²";
	//public static final String INVERT = "1/x";
	//public static final String TOGGLESIGN = "+/-";
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
	private boolean clearedOnce = false;
	private boolean dotPresence = false;
	private boolean bracketOpened = false;
	private boolean bracketWithSignAdded = false;
	private boolean lastDigitIsNumeric = true;
	
	TextView textDisplay;
	
	Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
	Button buttonBackspace, buttonBrackets, buttonInvert, buttonClear, buttonDot, buttonEqual;
	Button buttonAdd, buttonDivide, buttonMultiply, buttonSubtract;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: save data on hide/restart activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
		setContentView(R.layout.activity_main);
		
		textDisplay = findViewById(R.id.txtDisplay);
		// TODO: copy result to clipboard by tapping display
		//textHistory = findViewById(R.id.txtHistory);
		
		button0 = findViewById(R.id.button0);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		button3 = findViewById(R.id.button3);
		button4 = findViewById(R.id.button4);
		button5 = findViewById(R.id.button5);
		button6 = findViewById(R.id.button6);
		button7 = findViewById(R.id.button7);
		button8 = findViewById(R.id.button8);
		button9 = findViewById(R.id.button9);
		buttonAdd       = findViewById(R.id.buttonAdd);
		buttonBackspace = findViewById(R.id.buttonBackspace);
		buttonBrackets = findViewById(R.id.buttonBrackets);
		buttonInvert  = findViewById(R.id.buttonInvert);
		buttonClear     = findViewById(R.id.buttonClear);
		buttonDivide    = findViewById(R.id.buttonDivide);
		buttonDot       = findViewById(R.id.buttonDot);
		buttonEqual     = findViewById(R.id.buttonEqual);
		buttonMultiply  = findViewById(R.id.buttonMultiply);
		buttonSubtract  = findViewById(R.id.buttonSubtract);
		
		button0.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		button7.setOnClickListener(this);
		button8.setOnClickListener(this);
		button9.setOnClickListener(this);
		buttonAdd.setOnClickListener(this);
		buttonBackspace.setOnClickListener(this);
		buttonBrackets.setOnClickListener(this);
		buttonInvert.setOnClickListener(this);
		buttonClear.setOnClickListener(this);
		buttonDivide.setOnClickListener(this);
		buttonDot.setOnClickListener(this);
		buttonEqual.setOnClickListener(this);
		buttonMultiply.setOnClickListener(this);
		buttonSubtract.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		String currentText = textDisplay.getText().toString();
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
					//textDisplay.setText("");
					currentText = "";
					break;
			}
		}
		switch (view.getId()) {
			
			case R.id.button0:
				if (resultIsEmpty) break;
				setSpannedText(currentText + "0");
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
			// TODO: number length 10 digits maximum
			// TODO: add little dots 1.000.000
				
			// UTILS BUTTONS
			case R.id.buttonBackspace:
				if (resultIsEmpty) break;
				
				// TODO: add lastDigitIsNumeric checker
				if (currentText.length() == 1) {
					resultIsEmpty = true;
					currentText = getResources().getString(R.string.zero);                  // set 0
				} else if (currentText.length() > 1) {
					//String negativeSymbol = getResources().getString(R.string.sub);       // subtraction sign
					if (currentText.length() == 2 && resultIsNegative) {
						resultIsNegative = false;
						resultIsEmpty = true;
						currentText = getResources().getString(R.string.zero);              // set 0
					} else {
						if (currentText.endsWith(".")) dotPresence = false;                 // remove dot
						currentText = currentText.substring(0, currentText.length() - 1);   // just remove last digit
					}
				}
				setSpannedText(currentText);
				break;
				
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
							textDisplay.setText("0");
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
				Log.d("---------", bracketOpened+"");
				break;
				
			case R.id.buttonInvert:
				// TODO: apply only to last number
				if (resultIsNegative) {
					setSpannedText(currentText.substring(1));
				} else {
					setSpannedText("-" + currentText);
				}
				resultIsNegative = !resultIsNegative;
				break;
				
			case R.id.buttonClear:
				lastDigitIsNumeric = false;
				
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
					//textHistory.setText("");
				}
				break;
				
			// ..............................
			case R.id.buttonDot:
				
				if (dotPresence) {
					if (currentText.endsWith(".")) {
						dotPresence = false;
						currentText = currentText.substring(0, currentText.length() - 1);
						setSpannedText(currentText);
					}
				} else {
					if (lastDigitIsNumeric) {
						dotPresence = true;
						setSpannedText(currentText + ".");
						resultIsEmpty = false;
					}
				}
				
				break;
				
			// TODO: don't allow two oper signs in a row
			// TODO: change color for operation buttons
			// OPERATION BUTTONS
			// TODO: ++++++++++++++++++++++++++++++
			case R.id.buttonAdd:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				setSpannedText(currentText + "+");
				break;
			
			// TODO: ------------------------------
			case R.id.buttonSubtract:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				setSpannedText(currentText + "-");
				
				break;
				
			// TODO: ******************************
			case R.id.buttonMultiply:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				setSpannedText(currentText + "*");
				break;
				
			// TODO: /////////////////////////////
			case R.id.buttonDivide:
				if (bracketOpened && currentText.endsWith("(")) break;
				if (dotPresence && currentText.endsWith(".")) currentText = currentText.substring(0, currentText.length() - 1);
				lastDigitIsNumeric = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				setSpannedText(currentText + "/");
				break;
				
			
			// TODO: ============================
			case R.id.buttonEqual:
				lastDigitIsNumeric = false;
				if (resultIsEmpty) break;
				
				// num1 = Double.parseDouble(etNum1.getText().toString());
				// num2 = Double.parseDouble(etNum2.getText().toString());
				// valueOne = Double.parseDouble(String.valueOf(textDisplay.getText()));
				
				/*if (Double.isNaN(valueOne)){
					valueOne = Double.parseDouble(String.valueOf(textDisplay.getText()));
				} else {
					valueTwo = Double.parseDouble(textDisplay.getText().toString());
					valueOne = valueOne + valueTwo;
				}
				
				if(!Double.isNaN(valueOne)) {
		            valueTwo = Double.parseDouble(binding.editText.getText().toString());
		            binding.editText.setText(null);
		
		            if(CURRENT_ACTION == ADDITION) valueOne = this.valueOne + valueTwo;
		            else if(CURRENT_ACTION == SUBTRACTION) valueOne = this.valueOne - valueTwo;
		            else if(CURRENT_ACTION == MULTIPLICATION) valueOne = this.valueOne * valueTwo;
		            else if(CURRENT_ACTION == DIVISION) valueOne = this.valueOne / valueTwo;
		        }
		        else {
		            try {
		                valueOne = Double.parseDouble(binding.editText.getText().toString());
		            }
		            catch (Exception e){}
		        }
				*/
				
				break;
				
			
			
			default:
				Toast.makeText(this, "wtf?", Toast.LENGTH_LONG).show();
		}
	}
	
	private void setSpannedText(String str) {
		textDisplay.setText("");
		str = str.replaceAll("\u00A0","");
		int count = 0;
		for(int i =0; i < str.length(); i++) {
			switch (str.charAt(i)) {
				case '+':
				case '-':
				case '*':
				case '/':
					Spannable spacing = new SpannableString("\u00A0");
					spacing.setSpan(new RelativeSizeSpan(0.5f), 0, spacing.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					textDisplay.append(spacing);
					
					Spannable coloredSign = new SpannableString(String.valueOf(str.charAt(i)));
					coloredSign.setSpan(new ForegroundColorSpan(Color.BLUE), 0, coloredSign.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					textDisplay.append(coloredSign);
					
					spacing.setSpan(new RelativeSizeSpan(0.5f), 0, spacing.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					textDisplay.append(spacing);
					break;
				default:
					textDisplay.append(String.valueOf(str.charAt(i)));
			}
		}
		
		
		
		
	}
	
}

// TODO: lessen result font if viewport is small
// TODO: landscape orientation
