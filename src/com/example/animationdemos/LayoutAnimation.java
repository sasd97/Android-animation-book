package com.example.animationdemos;

import android.os.Bundle;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class LayoutAnimation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_animation);

		final Button addBT = (Button) findViewById(R.id.addBT);
		final Button removeBT = (Button) findViewById(R.id.removeBT);
		final LinearLayout container = (LinearLayout) findViewById(R.id.container);
		final Context context = this;

		for (int i = 0; i < 2; ++i) {
			container.addView(new ColorView(context));
		}

		addBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				container.addView(new ColorView(context), 1);
			}
		});

		removeBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (container.getChildCount() > 0) {
					container.removeViewAt(Math.min(1,
							container.getChildCount() - 1));
				}
			}
		});
		
		LayoutTransition transition = container.getLayoutTransition();
		transition.enableTransitionType(LayoutTransition.CHANGING);
	}

	private static class ColorView extends View {
		private boolean mExpanded = false;
		private LayoutParams mCompressedParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 50);
		private LayoutParams mExpandedParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 200);

		private ColorView(Context context) {
			super(context);
			int red = (int) (Math.random() * 128 + 127);
			int green = (int) (Math.random() * 128 + 127);
			int blue = (int) (Math.random() * 128 + 127);
			int color = 0xff << 24 | (red << 16) | (green << 8) | blue;
			setBackgroundColor(color);
			setLayoutParams(mCompressedParams);
			setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setLayoutParams(mExpanded ? mCompressedParams
							: mExpandedParams);
					mExpanded = !mExpanded;
					requestLayout();
				}
			});
		}

	}

}
