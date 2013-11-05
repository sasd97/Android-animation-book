package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class WindowAnimations extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_animations);

		Button defaultAnim = (Button) findViewById(R.id.defaultanim);
		Button transAnim = (Button) findViewById(R.id.transanim);
		Button scaleAnim = (Button) findViewById(R.id.scaleanim);
		final ImageView imageview = (ImageView) findViewById(R.id.activtyimg);

		defaultAnim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(WindowAnimations.this, SubActivity.class);
				startActivity(i);
			}
		});

		transAnim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(WindowAnimations.this, SubActivity.class);
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(
						WindowAnimations.this, R.animator.slide_in_right,
						R.animator.slide_out_left).toBundle();
				startActivity(i, translateBundle);
			}
		});

		scaleAnim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(WindowAnimations.this, SubActivity.class);
				Bundle scaleBundle = ActivityOptions.makeScaleUpAnimation(v, 0,
						0, v.getWidth(), v.getHeight()).toBundle();
				startActivity(i, scaleBundle);
			}
		});

		imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BitmapDrawable drawable = (BitmapDrawable) imageview
						.getDrawable();
				Bitmap bitmap = drawable.getBitmap();
				Intent i = new Intent(WindowAnimations.this, SubActivity.class);
				Bundle imageBundle = ActivityOptions
						.makeThumbnailScaleUpAnimation(v, bitmap, 0, 0)
						.toBundle();
				startActivity(i, imageBundle);
			}
		});
	}
}
