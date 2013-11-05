package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class AnimationButtons extends Activity {

		CheckBox mCheckBox;
		LinearLayout mLayout;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_animation_buttons);

			mCheckBox = (CheckBox) findViewById(R.id.checkBox);
			final Button alpha = (Button) findViewById(R.id.alphaButton);
			final Button translate = (Button) findViewById(R.id.translateButton);
			final Button rotate = (Button) findViewById(R.id.rotateButton);
			final Button scale = (Button) findViewById(R.id.scaleButton);
			final Button set = (Button) findViewById(R.id.setButton);
			mLayout = (LinearLayout) findViewById(R.id.layout);

			final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
			alphaAnimation.setDuration(1000);

			final TranslateAnimation translateAnimation = new TranslateAnimation(
					Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_PARENT, 1,
					Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 100);
			translateAnimation.setDuration(1000);

			final RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
					Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
					.5f);
			rotateAnimation.setDuration(1000);

			final ScaleAnimation scaleAnimation = new ScaleAnimation(1, 7, 1, 7);
			scaleAnimation.setDuration(1000);
			
			final AnimationSet setAnimation = new AnimationSet(true);
			setAnimation.addAnimation(alphaAnimation);
			//setAnimation.addAnimation(translateAnimation);
			//setAnimation.addAnimation(rotateAnimation);
			setAnimation.addAnimation(scaleAnimation);
			
			setupAnimation(alpha, alphaAnimation);
			setupAnimation(translate, translateAnimation);
			setupAnimation(rotate, rotateAnimation);
			setupAnimation(scale, scaleAnimation);
			setupAnimation(set, setAnimation);
	}

		private void setupAnimation(View view, final Animation animation) {
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					v.setAnimation(animation);
					if(mCheckBox.isChecked()) {
						v.startAnimation(animation);
					}
				}
			});
		}

}
