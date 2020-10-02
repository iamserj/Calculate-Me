package ru.iamserj.calculator.helper;

import android.content.res.Resources;
import android.graphics.*;
import android.view.Display;
import android.view.WindowManager;
import ru.iamserj.calculator.R;


public class ImageHelper {
	
	public static Bitmap CreateBitmapRoundCorner(WindowManager window, Resources resources) {
		// get display size
		Display display = window.getDefaultDisplay();
		Point screen = new Point();
		display.getSize(screen);
		
		// get resource image and scale it to fit screen X and Y
		Bitmap metalBgResource = BitmapFactory.decodeResource(resources, R.drawable.metal_bg);
		Bitmap metalBgScaled = Bitmap.createScaledBitmap(metalBgResource, screen.x, screen.y, false);
		
		// create new clean Bitmap and draw rect with rounded corners from scaled image
		Bitmap metalBgNew = Bitmap.createBitmap(metalBgScaled.getWidth(), metalBgScaled.getHeight(), metalBgScaled.getConfig());
		Canvas canvas = new Canvas(metalBgNew);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(new BitmapShader(metalBgScaled, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
		canvas.drawRoundRect((new RectF(0.0f, 0.0f, screen.x, screen.y)), 50, 50, paint);
		
		metalBgResource.recycle();
		metalBgScaled.recycle();
		
		return metalBgNew;
	}
	
}
