package ru.iamserj.calculator;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements OnClickListener {
	
	public static final String CLEAR = "C" ;
	//public static final String CLEARMEMORY = "MC";
	//public static final String ADDTOMEMORY = "M+";
	//public static final String SUBTRACTFROMMEMORY = "M-";
	//public static final String RECALLMEMORY = "MR";
	//public static final String SQUAREROOT = "√";
	//public static final String SQUARED = "x²";
	//public static final String INVERT = "1/x";
	//public static final String TOGGLESIGN = "+/-";
	//public static final String SINE = "sin";
	//public static final String COSINE = "cos";
	//public static final String TANGENT = "tan";
	
	private static final char ADDITION = '+';
	private static final char SUBTRACTION = '-';
	private static final char MULTIPLICATION = '*';
	private static final char DIVISION = '/';
	private static final char EMPTY = '0';
	private char CURRENT_ACTION;
	//DecimalFormat decimalFormat = new DecimalFormat("#.##########");
	
	
	private double valueOne = 0; //Double.MIN_VALUE;
	private double valueTwo = 0; //Double.MIN_VALUE;
	private boolean resultIsNegative = false;
	
	boolean resultIsEmpty = true;
	boolean clearedOnce = false;
	boolean dotPresence = false;
	
	TextView textDisplay, textHistory;
	
	Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
	Button buttonBackspace, buttonBrackets, buttonInvert, buttonClear, buttonDot, buttonEqual;
	Button buttonAdd, buttonDivide, buttonMultiply, buttonSubtract;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
		setContentView(R.layout.activity_main);
		
		textDisplay = findViewById(R.id.txtDisplay);
		textHistory = findViewById(R.id.txtHistory);
		
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
					textDisplay.setText("");
					break;
			}
		}
		switch (view.getId()) {
			
			case R.id.button0:
				if (resultIsEmpty) {
					break;
				}
				textDisplay.setText(textDisplay.getText() + "0");
				break;
			case R.id.button1:
				textDisplay.setText(textDisplay.getText() + "1");
				break;
			case R.id.button2:
				textDisplay.setText(textDisplay.getText() + "2");
				break;
			case R.id.button3:
				textDisplay.setText(textDisplay.getText() + "3");
				break;
			case R.id.button4:
				textDisplay.setText(textDisplay.getText() + "4");
				break;
			case R.id.button5:
				textDisplay.setText(textDisplay.getText() + "5");
				break;
			case R.id.button6:
				textDisplay.setText(textDisplay.getText() + "6");
				break;
			case R.id.button7:
				textDisplay.setText(textDisplay.getText() + "7");
				break;
			case R.id.button8:
				textDisplay.setText(textDisplay.getText() + "8");
				break;
			case R.id.button9:
				textDisplay.setText(textDisplay.getText() + "9");
				break;
				
				
			// UTILS BUTTONS
			case R.id.buttonBackspace:
				if (!resultIsEmpty) {
					String resData = textDisplay.getText().toString();
					if (resData.length() == 1) {
						resultIsEmpty = true;
						resData = getResources().getString(R.string.zero);      // set 0
					} else if (resData.length() > 1) {
						//String negativeSymbol = getResources().getString(R.string.sub); // subtraction sign
						if (resData.length() == 2 && resultIsNegative) {
							resultIsNegative = false;
							resultIsEmpty = true;
							resData = getResources().getString(R.string.zero);  // set 0
						} else {
							resData = resData.substring(0, resData.length() - 1);   // just remove last digit
						}
					}
					textDisplay.setText(resData);
				}
				break;
			case R.id.buttonBrackets:
				break;
			case R.id.buttonInvert:
				String resData = textDisplay.getText().toString();
				if (resultIsNegative) {
					textDisplay.setText(resData.substring(1));
				} else {
					textDisplay.setText("-" + resData);
				}
				resultIsNegative = !resultIsNegative;
				break;
			case R.id.buttonClear:
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
			case R.id.buttonDot:
				if (!dotPresence) {
					dotPresence = true;
					textDisplay.setText(textDisplay.getText() + ".");
					resultIsEmpty = false;
				}
				break;
				
				
			// OPERATION BUTTONS
			// ++++++++++++++++++++++++++++++
			case R.id.buttonAdd:
				//CURRENT_ACTION = ADDITION;
				//valueOne = Double.parseDouble(String.valueOf(textDisplay.getText()));
				//clearedOnce = false;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				textDisplay.setText(textDisplay.getText() + "+");
				//textDisplay.setText("");
				//textHistory.setText(valueOne + " + ");
				
				/*if (Double.isNaN(valueOne)){
					valueOne = Double.parseDouble(String.valueOf(textDisplay.getText()));
				} else {
					valueTwo = Double.parseDouble(textDisplay.getText().toString());
					valueOne = valueOne + valueTwo;
				}*/
				
				//textHistory.setText(valueOne + " + ");
				//textDisplay.setText(null);
				break;
			
			// ------------------------------
			case R.id.buttonSubtract:
				//CURRENT_ACTION = SUBTRACTION;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				textDisplay.setText(textDisplay.getText() + "-");
				
				break;
				
			// ******************************
			case R.id.buttonMultiply:
				//CURRENT_ACTION = MULTIPLICATION;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				textDisplay.setText(textDisplay.getText() + "*");
				break;
				
			// //////////////////////////////
			case R.id.buttonDivide:
				//CURRENT_ACTION = DIVISION;
				if (resultIsEmpty) {
					resultIsEmpty = false;
				}
				dotPresence = false;
				textDisplay.setText(textDisplay.getText() + "/");
				break;
				
			
			// ==============================
			case R.id.buttonEqual:
				if (resultIsEmpty) break;
				//if (action == +) textDisplay.setText(String.valueOf(valueOne + valueTwo));
				
				/*
				if(!Double.isNaN(valueOne)) {
		            valueTwo = Double.parseDouble(binding.editText.getText().toString());
		            binding.editText.setText(null);
		
		            if(CURRENT_ACTION == ADDITION)
		                valueOne = this.valueOne + valueTwo;
		            else if(CURRENT_ACTION == SUBTRACTION)
		                valueOne = this.valueOne - valueTwo;
		            else if(CURRENT_ACTION == MULTIPLICATION)
		                valueOne = this.valueOne * valueTwo;
		            else if(CURRENT_ACTION == DIVISION)
		                valueOne = this.valueOne / valueTwo;
		        }
		        else {
		            try {
		                valueOne = Double.parseDouble(binding.editText.getText().toString());
		            }
		            catch (Exception e){}
		        }
				*/
				
				/*
				num1 = Double.parseDouble(etNum1.getText().toString());
			    num2 = Double.parseDouble(etNum2.getText().toString());
			    */
				
				CURRENT_ACTION = EMPTY;
				break;
				
			
				
			
			
			
			default:
				Toast.makeText(this, "wtf?", Toast.LENGTH_LONG).show();
		}
	}
	
}
