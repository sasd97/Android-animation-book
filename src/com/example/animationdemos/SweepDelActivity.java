package com.example.animationdemos;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.animationdemos.pojo.Cheeses;
import com.example.animationdemos.widget.BackgroundContainer;
import com.example.animationdemos.widget.SweepStableArrayAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class SweepDelActivity extends Activity {
	ListView mListView;
	SweepStableArrayAdapter mAdapter;
	BackgroundContainer mBackgroundContainer;
	private boolean mPressed = false;
	private boolean mSwiping = false;
	private HashMap<Long, Integer> mItemIdTop = new HashMap<Long, Integer>();

	private static final int SWIPE_DURATION = 250;
	private static final int MOVE_DURATION = 150;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sweep_del);

		mListView = (ListView) findViewById(R.id.dellistview);
		mBackgroundContainer = (BackgroundContainer) findViewById(R.id.background);
		final ArrayList<String> mCheeses = new ArrayList<String>();
		for (int i = 0; i < Cheeses.sCheeseStrings.length; i++) {
			mCheeses.add(Cheeses.sCheeseStrings[i]);
		}
		mAdapter = new SweepStableArrayAdapter(this, R.layout.opaque, mCheeses,
				mTouchListener);
		mListView.setAdapter(mAdapter);
		Log.d("item is 4:", mAdapter.getItem(4));
	}

	View.OnTouchListener mTouchListener = new OnTouchListener() {
		float mDownX;
		private int mSwipeSlop = -1;

		@Override
		public boolean onTouch(final View v, MotionEvent event) {
			if (mSwipeSlop < 0) {
				// 触碰屏幕，没有移动，这个时候屏幕认为手指触碰偏差范围
				mSwipeSlop = ViewConfiguration.get(SweepDelActivity.this)
						.getScaledTouchSlop();
			}
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mPressed) {
					return false;
				}
				mPressed = true;
				mDownX = event.getX();
				break;
			case MotionEvent.ACTION_CANCEL:
				v.setAlpha(1);
				v.setTranslationX(0);
				mPressed = false;
				break;
			case MotionEvent.ACTION_MOVE: {
				// 第一手点到屏幕 +　用手按压移动距离
				float x = event.getX() + v.getTranslationX();
				float deltaX = x - mDownX;
				float deltaXAbs = Math.abs(deltaX);
				// 只为判断这个动作是不是往外扫的动作
				if (!mSwiping) {
					if (deltaXAbs > mSwipeSlop) {
						mSwiping = true;
						mListView.requestDisallowInterceptTouchEvent(true);
						mBackgroundContainer.showBackground(v.getTop(),
								v.getHeight());
					}
				}
				if (mSwiping) {
					v.setTranslationX((x - mDownX));
					v.setAlpha(1 - deltaXAbs / v.getWidth());
				}
			}
				break;
			case MotionEvent.ACTION_UP: {
				if (mSwiping) {
					float x = event.getX() + v.getTranslationX();
					float deltaX = x - mDownX;
					float deltaXAbs = Math.abs(deltaX);
					float fractionCovered;
					float endX;
					float endAlpha;
					final boolean remove;
					if (deltaXAbs > v.getWidth() / 4) {
						fractionCovered = deltaXAbs / v.getWidth();
						endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
						endAlpha = 0;
						remove = true;
					} else {
						fractionCovered = deltaXAbs / v.getWidth();
						endX = 0;
						endAlpha = 1;
						remove = false;
					}
					long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
					mListView.setEnabled(true);
					v.animate().setDuration(duration).alpha(endAlpha)
							.translationX(endX).withEndAction(new Runnable() {

								@Override
								public void run() {
									v.setAlpha(1);
									v.setTranslationX(0);
									if (remove) {
										animateRemoval(mListView, v);
									} else {
										mBackgroundContainer.hideBackground();
										mSwiping = false;
										mListView.setEnabled(true);
									}
								}
							});
				}
			}
				mPressed = false;
				break;
			default:
				return false;
			}
			return true;
		}
	};

	private void animateRemoval(final ListView listView, View viewToRemove) {
		int firstVisiblePostion = mListView.getFirstVisiblePosition();
		for (int i = 0; i < mListView.getChildCount(); i++) {
			View child = mListView.getChildAt(i);
			if (child != viewToRemove) {
				int position = firstVisiblePostion + i;
				long itemId = mAdapter.getItemId(position);
				Log.d("ssssss",  ((Long)itemId).toString());
				Log.d("ssssss", "Top is " + child.getTop());
				// long itemId = mListView.getItemIdAtPosition(position);
				mItemIdTop.put(itemId, child.getTop());
			}
		}
		int position = mListView.getPositionForView(viewToRemove);
		// mListView.removeViewAt(position);
		mAdapter.remove(mAdapter.getItem(position));

		final ViewTreeObserver observer = mListView.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				observer.removeOnPreDrawListener(this);
				boolean firstAnimation = true;
				int firstVisiblePosition = mListView.getFirstVisiblePosition();
				for (int i = 0; i < mListView.getChildCount(); i++) {
					View child = mListView.getChildAt(i);
					int top = child.getTop();

					int position = firstVisiblePosition + i;
					long itemId = mAdapter.getItemId(position);
					Integer startTop = mItemIdTop.get(itemId);

					if (startTop != null) {
						if (startTop != top) {
							int delta = startTop - top;
							child.setTranslationY(delta);
							child.animate().setDuration(MOVE_DURATION)
									.translationY(0);
							if (firstAnimation) {
								child.animate().withEndAction(new Runnable() {

									@Override
									public void run() {
										mSwiping = false;
										mBackgroundContainer.hideBackground();
										mListView.setEnabled(true);
									}
								});
								firstAnimation = false;
							}
						}
					} /*else {
						int childHeight = child.getHeight()
								+ mListView.getDividerHeight();
						startTop = top + (i > 0 ? childHeight : -childHeight);
						int delta = startTop - top;
						child.setTranslationY(delta);
						child.animate().setDuration(MOVE_DURATION)
								.translationY(0);
						if (firstAnimation) {
							child.animate().withEndAction(new Runnable() {

								@Override
								public void run() {
									mSwiping = false;
									mBackgroundContainer.hideBackground();
									mListView.setEnabled(true);
								}
							});
							firstAnimation = false;
						}
					}*/
				}
				mItemIdTop.clear();
				return true;
			}
		});
	}

}
