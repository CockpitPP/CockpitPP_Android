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

import fr.astazou.cockpitplusplus.R;

import static fr.astazou.cockpitplusplus.services.Konector.TAG;

/**
 * Created by JerkerD on 18.12.2017.
 */

public class A10C_VVI_View extends AppCompatImageView {


    private final static int POINT_RADIUS = 8;
    private final static int CROSS_LENGTH = 42;
    private final static int CROSS_WIDTH = 4;
    private Bitmap mLastBitmap = null;
    private Bitmap mVVINeedleBitmap = null;
    private Bitmap mVVIBackgroundBitmap = null;
    Matrix mMatrix = new Matrix();
    //private int mAngle = 0;

    private String mData = "0.05";


    public A10C_VVI_View(Context context) {
        super(context);
        mVVINeedleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_vvi_needle);
        mVVIBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_vvi_background);
        setImageBitmap(mVVIBackgroundBitmap);
    }

    public void updateLayoutSize() {
        invalidate();
    }


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
            finalWidth = canvas.getHeight() / 3;
        }else {
            //portrait
            finalWidth = canvas.getWidth() / 4;
        }
        //Log.d(TAG, "finalWidth " + finalWidth);
        //Log.d(TAG, "finalWidth " + finalWidth);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mVVINeedleBitmap, (int) finalWidth , 50, true);


        Matrix matrix = new Matrix();
        matrix.reset();
        float px = imageCenterX;
        float py = imageCenterY;
        matrix.postTranslate(-scaledBitmap.getWidth(), -scaledBitmap.getHeight()/2);
        matrix.postRotate(angle);
        matrix.postTranslate(px, py);
        canvas.drawBitmap(scaledBitmap, matrix, null);

        //Log.d(TAG, "onDraw: VVI 2");
    }


    public void setData(String pData) {
        mData = pData;
        Log.d(TAG, "setData(String pData)");
        invalidate();
    }
}