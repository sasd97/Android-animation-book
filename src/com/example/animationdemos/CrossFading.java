package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CrossFading extends Activity {
	
	int mCurrentDrawable = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cross_fading);
		
		ImageView imageview = (ImageView) findViewById(R.id.crossfading);
		Bitmap bitmap0 = Bitmap.createBitmap(500, 500, Config.ARGB_8888);
		Bitmap bitmap1 = Bitmap.createBitmap(500, 500, Config.ARGB_8888);
		Canvas canvas0 = new Canvas(bitmap0);
		canvas0.drawColor(Color.RED);
		Canvas canvas1 = new Canvas(bitmap1);
		canvas1.drawColor(Color.BLUE);
		
		BitmapDrawable drawables[] = new BitmapDrawable[2];
		drawables[0] = new BitmapDrawable(getResources(), bitmap0);
		drawables[1] = new BitmapDrawable(getResources(), bitmap1);
		
		final TransitionDrawable crossFading = new TransitionDrawable(drawables);
		imageview.setImageDrawable(crossFading);
		
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCurrentDrawable == 0) {
					crossFading.startTransition(500);
					mCurrentDrawable = 1;
				} else {
					crossFading.reverseTransition(500);
					mCurrentDrawable = 0;	
				}
			}
		});
	}
}
