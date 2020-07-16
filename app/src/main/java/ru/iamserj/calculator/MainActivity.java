package ru.iamserj.calculator;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//public class MainActivity extends Activity implements OnClickListener, OnTouchListener {
public class MainActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "123456";
	
	private boolean clearedOnce = false;
	private boolean resultIsNegative = false;
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
	private int[] operatorButtons = {R.id.bt_add, R.id.bt_backspace, R.id.bt_brackets, R.id.bt_invert, R.id.bt_clear, R.id.bt_divide, R.id.bt_dot, R.id.bt_equal, R.id.bt_multiply, R.id.bt_subtract};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
		setContentView(R.layout.activity_main);
		
		Bitmap bg = ImageHelper.CreateBitmapRoundCorner(getWindowManager(), getResources());
		iv_mainBackground = findViewById(R.id.iv_mainBackground);
		iv_mainBackground.setImageBitmap(bg);
		bg = null;
		
		tv_resultDisplay = findViewById(R.id.tv_resultDisplay);
		tv_historyDisplay = findViewById(R.id.tv_historyDisplay);

		tv_resultDisplay.setOnClickListener(this);

		for (int id : numericButtons) {
			findViewById(id).setOnClickListener(this);
			//findViewById(id).setOnTouchListener(handleTouch);
		}
		for (int id : operatorButtons) {
			findViewById(id).setOnClickListener(this);
			//findViewById(id).setOnTouchListener(handleTouch);
		}

	}

	// TODO: history show only last actions, e.g. 7 + 5 / 2
	// TODO: buttons add border and shadow
	// TODO: display box shadow
	// TODO: background in metal

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
		currentText = tv_resultDisplay.getText().toString();
		currentText = currentText.replaceAll("\u00A0","");

		if (view.getId() == R.id.tv_resultDisplay) {
			// TODO: if result is empty (0) or INFINITY, don't copy it
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

		if (view.getId() != R.id.bt_dot) {
			zeroWasAdded = false;
		}

		switch (view.getId()) {

			case R.id.bt_0:
				if (resultIsEmpty) break;
				setSpannedText(currentText + "0");
				// TODO: don't allow 2 zeros in a row if it is a start of non-float number 0005 -> 5
				lastDigitIsNumeric = true;
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
			// TODO: if (last is closed bracket) add *: (5*8)9 -> (5*8) * 9
			// TODO: number length 10 digits maximum
			// TODO: add little colons 1,000,000


			// UTILS BUTTONS
			// <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-  <-
			case R.id.bt_backspace:
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
			case R.id.bt_brackets:
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
							tv_resultDisplay.setText(getResources().getString(R.string.zero));
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
			case R.id.bt_invert:
				// TODO: apply invert only to last number in expression
				if (resultIsNegative) {
					setSpannedText(currentText.substring(1));
				} else {
					setSpannedText("-" + currentText);
				}
				resultIsNegative = !resultIsNegative;
				break;

			// CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR  CLEAR
			case R.id.bt_clear:
				lastDigitIsNumeric = false;

				// TODO: clean conditions
				if (!clearedOnce) {
					clearedOnce = true;
					resultIsEmpty = true;
					dotPresence = false;
					resultIsNegative = false;
					tv_resultDisplay.setText(getResources().getString(R.string.zero));
				} else {
					clearedOnce = false;
					resultIsEmpty = true;
					dotPresence = false;
					resultIsNegative = false;
					tv_resultDisplay.setText(getResources().getString(R.string.zero));
					tv_historyDisplay.setText("");
				}
				break;

			// .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
			case R.id.bt_dot:

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
			case R.id.bt_add:
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
			case R.id.bt_subtract:
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
			case R.id.bt_multiply:
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
			case R.id.bt_divide:
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
			case R.id.bt_equal:
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
				tv_historyDisplay.setText(currentText);
				double result = Calculate.evaluate(currentText);	// evaluate expression
				String resFormatted = cutFloatIfInteger(result);    // cut floating zero from integer

				// save real result
				// cut showable result

				//setSpannedText(resFormatted);
				if (resFormatted.toLowerCase().equals("infinity")) {
					tv_resultDisplay.setText("DIVISION BY ZERO  :)");
				} else {
					tv_resultDisplay.setText(resFormatted);
				}
				
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
		switch (str.charAt(str.length() - 1)) {
			// FIXME: if result is ( and push = , app crashes
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