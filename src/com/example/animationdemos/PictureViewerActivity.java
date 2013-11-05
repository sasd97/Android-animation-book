package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class PictureViewerActivity extends Activity {

	int[] drawableIDs = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e };
	int mCurrentDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_viewer);

		final ImageView preImageView = (ImageView) findViewById(R.id.preimageview);
		final ImageView nextImageView = (ImageView) findViewById(R.id.nextimageview);
		preImageView.setBackgroundColor(Color.TRANSPARENT);
		nextImageView.setBackgroundColor(Color.TRANSPARENT);
		/*
		 * preImageView.animate().setDuration(1000);
		 * nextImageView.animate().setDuration(1000);
		 */

		final AlphaAnimation preImageDisappear = new AlphaAnimation(1, 0);
		preImageDisappear.setDuration(500);
		final AlphaAnimation preImageDisplay = new AlphaAnimation(0, 1);
		preImageDisplay.setDuration(500);

		final AlphaAnimation nextImageDisplay = new AlphaAnimation(0, 1);
		nextImageDisplay.setDuration(500);
		final AlphaAnimation nextImageDisappear = new AlphaAnimation(1, 0);
		nextImageDisappear.setDuration(500);
		
		final BitmapDrawable drawables[] = new BitmapDrawable[drawableIDs.length];
		for (int i = 0; i < drawables.length; ++i) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					drawableIDs[i]);
			drawables[i] = new BitmapDrawable(getResources(), bitmap);
		}
		preImageView.setImageDrawable(drawables[0]);
		nextImageView.setImageDrawable(drawables[1]);

		preImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextImageDisplay.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {						
						mCurrentDrawable = (mCurrentDrawable + 1) % drawables.length;
						int nextDrawableIndex =(mCurrentDrawable + 1) % drawables.length;
						preImageView.setImageDrawable(drawables[mCurrentDrawable]);
						nextImageView.setImageDrawable(drawables[nextDrawableIndex]);
						nextImageView.startAnimation(nextImageDisappear);
						preImageView.startAnimation(preImageDisplay);
					}
				});
				preImageView.startAnimation(preImageDisappear);
				nextImageView.startAnimation(nextImageDisplay);
				/*
				 * preImageView.animate().alpha(0).withLayer();
				 * nextImageView.animate().alpha(1).withLayer()
				 * .withEndAction(new Runnable() {
				 * 
				 * @Override public void run() { mCurrentDrawable =
				 * (mCurrentDrawable + 1) % drawables.length; int
				 * nextDrawableIndex = (mCurrentDrawable + 1) %
				 * drawables.length;
				 * preImageView.setImageDrawable(drawables[mCurrentDrawable]);
				 * nextImageView.setImageDrawable(drawables[nextDrawableIndex]);
				 * nextImageView.setAlpha(0f); preImageView.setAlpha(1f); } });
				 */
			}
		});
	}

}
