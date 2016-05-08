package com.tedu.lagou.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

	public CircleImageView(Context context) {
		super(context);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private Bitmap getSourceBitmap(Drawable drawable) {
		if (drawable instanceof Drawable) {
			BitmapDrawable db = (BitmapDrawable) drawable;
			return db.getBitmap();
		} else {
			Bitmap bm = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas();
			drawable.draw(canvas);
			return bm;
		}
	}

	private Paint paint = new Paint();

	@Override
	protected void onDraw(Canvas canvas) {
		// 获取需要被绘制的图形的Bitmap
		Bitmap src = getSourceBitmap(getDrawable());
		// 图形渲染工具
		BitmapShader shader = new BitmapShader(src, TileMode.CLAMP,
				TileMode.CLAMP);
		// 将图形渲染工具应用到画笔
		paint.setShader(shader);
		// 确定绘制的图形的尺寸
		int size = src.getWidth() > src.getHeight() ? src.getHeight() : src
				.getWidth();
		// 绘制圆形
		canvas.drawCircle(size / 2, size / 2, size / 2, paint);
	}
}
