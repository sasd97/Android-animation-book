package com.example.animationdemos.widget;

import com.example.animationdemos.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BackgroundContainer extends FrameLayout {
	
	Drawable	mBackground;
	boolean	mShowing = false;
	int mOpenAreaTop, mOpenAreaBottom, mOpenAreaHeight;
	boolean	mUpdateBounds = false;
	

	public BackgroundContainer(Context context) {
		super(context);
		init();
	}
	
	public BackgroundContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    	mBackground = getContext().getResources().getDrawable(R.drawable.shadowed_background);
    }
    
    public void showBackground(int top, int bottom) {
    	setWillNotDraw(false);
    	mShowing = true;
    	mOpenAreaTop = top;
    	mOpenAreaHeight = bottom;
    	mUpdateBounds = true;
    }
    
    public void hideBackground() {
    	setWillNotDraw(true);
    	mShowing = false;
    }
    
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(mShowing) {
			if(mUpdateBounds) {
				mBackground.setBounds(0, 0, getWidth(), mOpenAreaHeight);
			}
			canvas.save();
			canvas.translate(0, mOpenAreaTop);
			mBackground.draw(canvas);
			canvas.restore();
		}
	}
	
	

}
