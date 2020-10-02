package ru.iamserj.calculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


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
	
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int size = Math.min(width, height);
		setMeasuredDimension(size, size); // make it square
	}
}
