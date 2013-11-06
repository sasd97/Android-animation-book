package com.example.animationdemos;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewAnimator;

public class PropertyAnimHolder extends Activity {
	
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_anim_holder);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		
		btn1.setOnClickListener(new OnClickButton());
		btn2.setOnClickListener(new OnClickButton());
		btn3.setOnClickListener(new OnClickButton());
		btn4.setOnClickListener(new OnClickButton());
	}
	
	private class OnClickButton implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case	R.id.button1:
				runValueAnimator(btn1);
				break;
			case	R.id.button2:
				runViewAnimator(btn2);
				break;
			case	R.id.button3:
				runObjectAnimator(btn3);
				break;
			case	R.id.button4:
				runObjectAnimatorHolder(btn4);
				break;
			default:
				return;
			}
		}
	}
	
	public void runValueAnimator(final View view) {
		ValueAnimator valueAnim = ValueAnimator.ofFloat(0, 400);
		final float destX = view.getX() * 3;
		final float destY = view.getY() * 3;
		valueAnim.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float fraction = animation.getAnimatedFraction();
				view.setTranslationX(view.getX() + fraction * destX);
				view.setTranslationY(view.getY() + fraction * destY);
			}
		});
		valueAnim.start();
	}
	
	public void runViewAnimator(final View view) {
		view.animate().translationX(view.getX() * 3).translationY(view.getY() * 3);
	}
	
	public void runObjectAnimator(final View view) {
		ObjectAnimator.ofFloat(view, View.TRANSLATION_X, view.getX() * 3).start();
		ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.getY() * 3).start();
	}
	
	public void runObjectAnimatorHolder(final View view) {
		PropertyValuesHolder valueX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, view.getX() * 3);
		PropertyValuesHolder valueY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, view.getY() * 3);
		ObjectAnimator.ofPropertyValuesHolder(view, valueX, valueY).start();
	}
}
