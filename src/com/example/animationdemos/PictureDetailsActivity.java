package com.example.animationdemos;

import com.example.animationdemos.pojo.BitmapUtils;
import com.example.animationdemos.pojo.ShadowLayout;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PictureDetailsActivity extends Activity {

	private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
	private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
	private static final String PACKAGE_NAME = "com.example.android.activityanim";
	private static final int SDK = android.os.Build.VERSION.SDK_INT;
	private static final int ANIM_DURATION = 500;
	private ColorMatrix colorizerMatrix = new ColorMatrix();
	ColorDrawable mBackground;
	ImageView mImageView;
	FrameLayout mTopLevelView;
	ShadowLayout mShadowLayout;
	TextView mTextView;
	int mLeftDelta;
	int mTopDelta;
	float mWidthScale;
	float mHeightScale;
	private int mOriginalOrientation;
	private BitmapDrawable mBitmapDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_details);

		mImageView = (ImageView) findViewById(R.id.imageView);
		mTopLevelView = (FrameLayout) findViewById(R.id.topLevelLayout);
		mTextView = (TextView) findViewById(R.id.description);
		mShadowLayout = (ShadowLayout) findViewById(R.id.shadowLayout);

		Bundle bundle = getIntent().getExtras();
		Bitmap bitmap = BitmapUtils.getBitmap(getResources(),
				bundle.getInt(PACKAGE_NAME + ".resourceId"));
		String description = bundle.getString(PACKAGE_NAME + ".description");
		final int thumbnailTop = bundle.getInt(PACKAGE_NAME + ".top");
		final int thumbnailLeft = bundle.getInt(PACKAGE_NAME + ".left");
		final int thumbnailWidth = bundle.getInt(PACKAGE_NAME + ".width");
		final int thumbnailHeight = bundle.getInt(PACKAGE_NAME + ".height");
		mOriginalOrientation = bundle.getInt(PACKAGE_NAME + ".orientation");

		mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
		mImageView.setImageDrawable(mBitmapDrawable);
		mTextView.setText(description);

		mBackground = new ColorDrawable(Color.BLACK);
		if (SDK < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mTopLevelView.setBackgroundDrawable(mBackground);
		} else {
			mTopLevelView.setBackground(mBackground);
		}

		if (savedInstanceState == null) {
			ViewTreeObserver observer = mImageView.getViewTreeObserver();
			observer.addOnPreDrawListener(new OnPreDrawListener() {

				@Override
				public boolean onPreDraw() {
					mImageView.getViewTreeObserver().removeOnPreDrawListener(
							this);

					int[] screenLocation = new int[2];
					mImageView.getLocationOnScreen(screenLocation);
					mLeftDelta = thumbnailLeft - screenLocation[0];
					mTopDelta = thumbnailTop - screenLocation[1];

					mWidthScale = (float) thumbnailWidth
							/ mImageView.getWidth();
					mHeightScale = (float) thumbnailHeight
							/ mImageView.getHeight();

					runEnterAnimation();

					return true;
				}
			});
		}
	}

	private void runEnterAnimation() {
		final long duration = (long) (ANIM_DURATION * WindowCustomAnimation.sAnimatorScale);

		mImageView.setPivotX(0);
		mImageView.setPivotY(0);
		mImageView.setScaleX(mWidthScale);
		mImageView.setScaleY(mHeightScale);
		mImageView.setTranslationX(mLeftDelta);
		mImageView.setTranslationY(mTopDelta);

		mTextView.setAlpha(0);

		mImageView.animate().setDuration(duration).scaleX(1).scaleY(1)
				.translationX(0).translationY(0).setInterpolator(sDecelerator)
				.withEndAction(new Runnable() {

					@Override
					public void run() {
						mTextView.setTranslationY(-mTextView.getHeight());
						mTextView.animate().setDuration(duration / 2)
								.translationY(0).alpha(1)
								.setInterpolator(sDecelerator);
					}
				});
		
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
		bgAnim.setDuration(duration);
		bgAnim.start();
		
		ObjectAnimator colorize = ObjectAnimator.ofFloat(PictureDetailsActivity.this, "saturation", 0, 1);
		colorize.setDuration(duration);
		colorize.start();
		
		ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout, "shadowDepth", 0, 1);
		shadowAnim.setDuration(duration);
		shadowAnim.start();
	}
	
	public void runExitAnimation(final Runnable endAction) {
        final long duration = (long) (ANIM_DURATION * WindowCustomAnimation.sAnimatorScale);

        // No need to set initial values for the reverse animation; the image is at the
        // starting size/location that we want to start from. Just animate to the
        // thumbnail size/location that we retrieved earlier 
        
        // Caveat: configuration change invalidates thumbnail positions; just animate
        // the scale around the center. Also, fade it out since it won't match up with
        // whatever's actually in the center
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != mOriginalOrientation) {
            mImageView.setPivotX(mImageView.getWidth() / 2);
            mImageView.setPivotY(mImageView.getHeight() / 2);
            mLeftDelta = 0;
            mTopDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }   

        // First, slide/fade text out of the way
        mTextView.animate().translationY(-mTextView.getHeight()).alpha(0).
                setDuration(duration/2).setInterpolator(sAccelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        // Animate image back to thumbnail size/location
                        mImageView.animate().setDuration(duration).
                                scaleX(mWidthScale).scaleY(mHeightScale).
                                translationX(mLeftDelta).translationY(mTopDelta).
                                withEndAction(endAction);
                        if (fadeOut) {
                            mImageView.animate().alpha(0);
                        }
                        // Fade out background
                        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0);
                        bgAnim.setDuration(duration);
                        bgAnim.start();

                        // Animate the shadow of the image
                        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout,
                                "shadowDepth", 1, 0);
                        shadowAnim.setDuration(duration);
                        shadowAnim.start();

                        // Animate a color filter to take the image back to grayscale,
                        // in parallel with the image scaling and moving into place.
                        ObjectAnimator colorizer =
                                ObjectAnimator.ofFloat(PictureDetailsActivity.this,
                                "saturation", 1, 0);
                        colorizer.setDuration(duration);
                        colorizer.start();
                    }
                });

        
    }
	
	@Override
    public void onBackPressed() {
        runExitAnimation(new Runnable() {
            public void run() {
                // *Now* go ahead and exit the activity
                finish();
            }
        });
    }

    /**
     * This is called by the colorizing animator. It sets a saturation factor that is then
     * passed onto a filter on the picture's drawable.
     * @param value
     */
    public void setSaturation(float value) {
        colorizerMatrix.setSaturation(value);
        ColorMatrixColorFilter colorizerFilter = new ColorMatrixColorFilter(colorizerMatrix);
        mBitmapDrawable.setColorFilter(colorizerFilter);
    }
    
    @Override
    public void finish() {
        super.finish();
        
        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_details, menu);
		return true;
	}

}
