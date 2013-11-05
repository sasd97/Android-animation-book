package com.example.animationdemos;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.animationdemos.pojo.BitmapUtils;
import com.example.animationdemos.pojo.PictureData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageView;

public class WindowCustomAnimation extends Activity {
	
	private static final String PACKAGE = "com.example.android.activityanim";
	HashMap<ImageView, PictureData> mPictureData = new HashMap<ImageView, PictureData>();
	static float sAnimatorScale = 1;
	GridLayout mGridLayout;
	BitmapUtils mBitmapUtils = new BitmapUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_custom_animation);
		
		//将背景过滤成为灰色
		ColorMatrix grayMatrix = new ColorMatrix();
		grayMatrix.setSaturation(0);
		ColorMatrixColorFilter grayscaleFilter = new ColorMatrixColorFilter(grayMatrix);
		
		//每一行有三张图片
		mGridLayout = (GridLayout) findViewById(R.id.gridLayout);
		mGridLayout.setColumnCount(3);
		mGridLayout.setUseDefaultMargins(true);
		
		Resources resources = getResources();
		ArrayList<PictureData> pictures = mBitmapUtils.loadPhotos(resources);
		for(int i = 0; i < pictures.size(); i++) {
			PictureData pictureData = pictures.get(i);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), pictureData.getThumbnail());
			bitmapDrawable.setColorFilter(grayscaleFilter);
			ImageView imageView = new ImageView(this);
			imageView.setImageDrawable(bitmapDrawable);
			imageView.setOnClickListener(thumbnailClickListener);
			mPictureData.put(imageView, pictureData);
			mGridLayout.addView(imageView);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.window_custom_animation, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_slow) {
			sAnimatorScale = item.isChecked() ? 1 : 5;
			item.setChecked(!item.isChecked());
		}
		return super.onOptionsItemSelected(item);
	}


	private OnClickListener thumbnailClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int[] screenLocation = new int[2];
			v.getLocationOnScreen(screenLocation);
			PictureData info = mPictureData.get(v);
			Intent subActivity = new Intent(WindowCustomAnimation.this, PictureDetailsActivity.class);
			int orientation = getResources().getConfiguration().orientation;
			subActivity.
            putExtra(PACKAGE + ".orientation", orientation).
            putExtra(PACKAGE + ".resourceId", info.getResourceId()).
            putExtra(PACKAGE + ".left", screenLocation[0]).
            putExtra(PACKAGE + ".top", screenLocation[1]).
            putExtra(PACKAGE + ".width", v.getWidth()).
            putExtra(PACKAGE + ".height", v.getHeight()).
            putExtra(PACKAGE + ".description", info.getDescription());
			startActivity(subActivity);
			
			overridePendingTransition(0, 0);
		}
	};
}
