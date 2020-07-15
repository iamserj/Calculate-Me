package ru.iamserj.calculator;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @author iamserj
 * 25.01.2019 23:42
 */

public class SquareButton extends Button {
	
	public SquareButton(Context context) {
		super(context);
	}
	
	public SquareButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	/*public SquareButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}*/
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int size = Math.min(width, height);
		setMeasuredDimension(size, size); // make it square
	}
}
