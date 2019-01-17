package ru.iamserj.calculator;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	double input1 = 0, input2 = 0;
	boolean isEmpty = true;
	//boolean Addition, Subtract, Multiplication, Division, mRemainder, decimal;
	
	TextView textDisplay, textHistory;
	
	Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
	Button buttonBackspace, buttonBrakClose, buttonBrakOpen, buttonClear, buttonDot, buttonEqual;
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
		// set text => String.valueOf(intValue)
		
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
		buttonBrakClose = findViewById(R.id.buttonBracketClose);
		buttonBrakOpen  = findViewById(R.id.buttonBracketOpen);
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
		buttonBrakClose.setOnClickListener(this);
		buttonBrakOpen.setOnClickListener(this);
		buttonClear.setOnClickListener(this);
		buttonDivide.setOnClickListener(this);
		buttonDot.setOnClickListener(this);
		buttonEqual.setOnClickListener(this);
		buttonMultiply.setOnClickListener(this);
		buttonSubtract.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		
		if (isEmpty) {
			switch (view.getId()) {
				case R.id.button0:
				case R.id.button1:
				case R.id.button2:
				case R.id.button3:
				case R.id.button4:
				case R.id.button5:
				case R.id.button6:
				case R.id.button7:
				case R.id.button8:
				case R.id.button9:
					isEmpty = false;
					textDisplay.setText("");
					break;
			}
		}
		switch (view.getId()) {
			
			case R.id.button0:
				Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "0");
				break;
			case R.id.button1:
				Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "1");
				break;
			case R.id.button2:
				Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "2");
				break;
			case R.id.button3:
				Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "3");
				break;
			case R.id.button4:
				Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "4");
				break;
			case R.id.button5:
				Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "5");
				break;
			case R.id.button6:
				Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "6");
				break;
			case R.id.button7:
				Toast.makeText(this, "7", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "7");
				break;
			case R.id.button8:
				Toast.makeText(this, "8", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "8");
				break;
			case R.id.button9:
				Toast.makeText(this, "9", Toast.LENGTH_SHORT).show();
				textDisplay.setText(textDisplay.getText() + "9");
				break;
			
			
			case R.id.buttonAdd:
				Toast.makeText(this, "+", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonBackspace:
				Toast.makeText(this, "B-S", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonBracketClose:
				Toast.makeText(this, ")", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonBracketOpen:
				Toast.makeText(this, "(", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonClear:
				Toast.makeText(this, "CLR", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonDivide:
				Toast.makeText(this, "/", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonDot:
				Toast.makeText(this, ".", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonEqual:
				Toast.makeText(this, "=", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonMultiply:
				Toast.makeText(this, "*", Toast.LENGTH_SHORT).show();
				break;
			case R.id.buttonSubtract:
				Toast.makeText(this, "-", Toast.LENGTH_SHORT).show();
				break;
			
			
			default:
				Toast.makeText(this, "wtf?", Toast.LENGTH_LONG).show();
		}
	}
	
}
