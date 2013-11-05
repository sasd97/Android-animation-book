package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class KeyframeAnimation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyframe_animation);
		
		ImageView imageview = (ImageView) findViewById(R.id.imageview);
		
		final AnimationDrawable animationDrawable = new AnimationDrawable();
		for(int i = 0; i < 10; ++i) {
			animationDrawable.addFrame(getDrawableForFrameNumber(i), 300);
		}
		animationDrawable.setOneShot(false);
		imageview.setImageDrawable(animationDrawable);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(animationDrawable.isRunning()) {
					animationDrawable.stop();
				} else {
					animationDrawable.start();
				}
			}
		});
	}
	
	private BitmapDrawable getDrawableForFrameNumber(int frameNumber) {
		Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.GRAY);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(80);
		paint.setColor(Color.BLACK);
		canvas.drawText("Frame " + frameNumber, 40, 220, paint);
		return new BitmapDrawable(getResources(), bitmap);
	}
}
