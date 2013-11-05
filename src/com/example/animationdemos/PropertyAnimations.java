package com.example.animationdemos;

import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class PropertyAnimations extends Activity {

	CheckBox mCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_animations);

		mCheckBox = (CheckBox) findViewById(R.id.checkBox);
		final Button alpha = (Button) findViewById(R.id.alphaButton);
		final Button translate = (Button) findViewById(R.id.translateButton);
		final Button rotate = (Button) findViewById(R.id.rotateButton);
		final Button scale = (Button) findViewById(R.id.scaleButton);
		final Button set = (Button) findViewById(R.id.setButton);

		// offloat view.alpha = 0 means transpreant
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(alpha,
				View.ALPHA, 0);
		alphaAnimator.setRepeatCount(1);
		// 当动画结束之后，反转动画(reverse)
		alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

		ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(translate,
				View.TRANSLATION_X, 800);
		translateAnimator.setRepeatCount(1);
		translateAnimator.setRepeatMode(ValueAnimator.REVERSE);

		ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(rotate,
				View.ROTATION, 360);
		rotateAnimator.setRepeatCount(1);
		rotateAnimator.setRepeatMode(ValueAnimator.REVERSE);
		
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);
		ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(scale, pvhX, pvhY);
		scaleAnimator.setRepeatCount(1);
		scaleAnimator.setRepeatMode(ValueAnimator.REVERSE);

		AnimatorSet setAnimator = new AnimatorSet();
		setAnimator.play(translateAnimator).after(alphaAnimator)
				.before(rotateAnimator);
		setAnimator.play(rotateAnimator).before(scaleAnimator);

		setupAnimation(alpha, alphaAnimator);
		setupAnimation(translate, translateAnimator);
		setupAnimation(rotate, rotateAnimator);
		setupAnimation(scale, scaleAnimator);
		setupAnimation(set, setAnimator);
	}
	
	private void setupAnimation(View view, final Animator animator) {

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCheckBox.isChecked()) {
					//animation.setTarget(v);
					animator.start();
				}
			}
		});
	}
}
