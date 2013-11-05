package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class BitmapAllocation extends Activity {
	int mCurrentIndex = 0;
	Bitmap mCurrentBitmap = null;
	BitmapFactory.Options mBitmapOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitmap_allocation);

		final int[] imgIDs = { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		final CheckBox checkBox = (CheckBox) findViewById(R.id.bmaCB);
		final TextView notifyTV = (TextView) findViewById(R.id.notifyTV);
		final ImageView bmaImageView = (ImageView) findViewById(R.id.bmaIV);

		mBitmapOptions = new BitmapFactory.Options();
		/*
		 * inJustDecodeBounds = true:Just decode the bitmap's bounds(width,
		 * height), not the whole bitmap,so the caller do not need to allocate
		 * the memory for the whole bitmap.
		 */
		mBitmapOptions.inJustDecodeBounds = true;
		/* The caller decode the bitmap's outwidth and outheight,returns null. */
		BitmapFactory.decodeResource(getResources(), R.drawable.a,
				mBitmapOptions);
		/* Creating a new bitmap with specified width and height which will be planed
		 * to become a reuseful bitmap. */
		mCurrentBitmap = Bitmap.createBitmap(mBitmapOptions.outWidth,
				mBitmapOptions.outHeight, Config.ARGB_8888);
		/* Shut down the inJustDecodeBounds */
		mBitmapOptions.inJustDecodeBounds = false;
		/*
		 * If set inBitmap, decode methods that take the Options object will
		 * attempt to reuse this bitmap when loading content.
		 */
		mBitmapOptions.inBitmap = mCurrentBitmap;
		/* The same size of the sample. */
		mBitmapOptions.inSampleSize = 1;
		Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.a,
				mBitmapOptions);
		bmaImageView.setImageBitmap(bit);

		bmaImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % imgIDs.length;
				BitmapFactory.Options bitmapOptions = null;
				if (checkBox.isChecked()) {
					bitmapOptions = mBitmapOptions;
					bitmapOptions.inBitmap = mCurrentBitmap;
				}
				long startTime = System.currentTimeMillis();
				mCurrentBitmap = BitmapFactory.decodeResource(getResources(),
						imgIDs[mCurrentIndex], mBitmapOptions);
				bmaImageView.setImageBitmap(mCurrentBitmap);
				notifyTV.setText("Load took " + (System.currentTimeMillis() - startTime));
			}
		});
	}
}
