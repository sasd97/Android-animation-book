package com.example.animationdemos;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JimmyandHurry on 13-10-22.
 */
public class ValueActivity extends Activity {

    MyView v;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(v);
    }

    static class MyView extends View {

        //The bitmap is the small pic that will move from top to bottom.
        Bitmap mBitmap;
        Paint mPaint = new Paint();
        int mShapeX, mShapeY;
        int mShapeW, mShapeH;

        public MyView(Context context, AttributeSet attrs, int  defStyle) {
            super(context, attrs, defStyle);
            setupShape();
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setupShape();
        }

        public MyView(Context context) {
            super(context);
            setupShape();
        }

        private void setupShape() {
            //set up the pic that will be animated
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.annie);
            mShapeW = mBitmap.getWidth();
            mShapeH = mBitmap.getHeight();
            //set up the listener,click the pic the animation will begin.
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAnimation();
                }
            });
        }

        public void setShapex(int shapeX) {
            //The x value that last time animator has been triggered and specified.
            int minX = mShapeX;
            //The x might be zero,so we choose the max rage number mShapeX + mShapeW.
            int maxX = mShapeX + mShapeW;
            mShapeX = shapeX;
            minX = Math.min(mShapeX, minX);
            maxX = Math.max(mShapeX + mShapeW, maxX);
            //invalidate the x axis rage which is smaller than other.
            invalidate(minX, mShapeY, maxX, mShapeY + mShapeH);
        }

        public void setShapeY(int shapeY) {
            int minY = mShapeY;
            int maxY = mShapeY + mShapeH;
            mShapeY = shapeY;
            minY = Math.min(minY, mShapeY);
            maxY = Math.max(maxY, mShapeY + mShapeH);
            invalidate(mShapeX, minY, mShapeX + mShapeW, maxY);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            //Make the bitmap on the center of x axis,forget about the y axis.
            mShapeX = (w - mBitmap.getWidth()) / 2;
            mShapeY = 0;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(mBitmap, mShapeX, mShapeY, mPaint);
        }

        void startAnimation() {
            ValueAnimator anim = getValueAnimation();
            anim.start();
        }

        ValueAnimator getValueAnimation() {
            ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setShapeY((int) (animation.getAnimatedFraction() * (getHeight() - mShapeH)));
                }
            });
            return anim;
        }

    }
}
