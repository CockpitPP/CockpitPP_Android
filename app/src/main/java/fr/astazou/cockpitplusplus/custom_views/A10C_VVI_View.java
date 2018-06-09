package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;

import fr.astazou.cockpitplusplus.R;

import static fr.astazou.cockpitplusplus.services.Konector.TAG;

/**
 * Created by JerkerD on 18.12.2017.
 */

public class A10C_VVI_View extends AppCompatImageView {

    private Bitmap mVVINeedleBitmap = null;
    private Bitmap mScaledVVINeedleBitmap = null;
    //private Bitmap mVVIBackgroundBitmap = null;
    private Matrix mMatrix = new Matrix();
    private float mFinalWidth = -1;

    private String mData = "0.05";


    public A10C_VVI_View(Context context) {
        super(context);
        mVVINeedleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_vvi_needle);
        //mVVIBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_vvi_background);
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
    {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public void updateLayoutSize() {invalidate();}

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw: VVI 1");
        float imageCenterX = (float) canvas.getWidth()/2f;
        float imageCenterY = (float) ((canvas.getHeight()/2f) + 7);

        float angle = Float.parseFloat(mData);
        if(angle > 1){
            angle = 1;
        }
        if(angle < -1){
            angle = -1;
        }
        angle = angle * 170;
        float finalWidth = 0;
        /*Log.d(TAG, "canvas.getHeight() " + canvas.getHeight());
        Log.d(TAG, "canvas.getWidth() " + canvas.getWidth());
        Log.d(TAG, "mNeedleBitmap.getWidth() " + mVVINeedleBitmap.getWidth());*/
        if(canvas.getWidth() > canvas.getHeight()){
            //landscape
            finalWidth = Math.round(canvas.getHeight() / 1.9);
        }else {
            //portrait
            finalWidth = Math.round(canvas.getWidth() / 1.9);
        }
        if(finalWidth != mFinalWidth || mScaledVVINeedleBitmap == null){
            mScaledVVINeedleBitmap = ScaleNeedleBitmap(finalWidth);
        }
        //Log.d(TAG, "finalWidth " + finalWidth);
        //Log.d(TAG, "finalWidth " + finalWidth);

        mMatrix.reset();
        mMatrix.postTranslate(-mScaledVVINeedleBitmap.getWidth(), -mScaledVVINeedleBitmap.getHeight()/2);
        mMatrix.postRotate(angle);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        canvas.drawBitmap(mScaledVVINeedleBitmap, mMatrix, null);

        //Log.d(TAG, "onDraw: VVI 2");
    }

    private Bitmap ScaleNeedleBitmap(float finalWidth){
        //Log.d("SCALING","SCALING BITMAP");
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mVVINeedleBitmap, (int) finalWidth , 50, true);
        mFinalWidth = finalWidth;
        return scaledBitmap;
    }

    public void setData(String pData) {
        mData = pData;
        //Log.d(TAG, "setData(String pData)");
        invalidate();
    }
}