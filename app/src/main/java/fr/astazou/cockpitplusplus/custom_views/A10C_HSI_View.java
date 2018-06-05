package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import fr.astazou.cockpitplusplus.R;

import static fr.astazou.cockpitplusplus.services.Konector.TAG;

/**
 * Created by JerkerD on 18.12.2017.
 */

public class A10C_HSI_View extends AppCompatImageView {


    private final static int POINT_RADIUS = 8;
    private final static int CROSS_LENGTH = 42;
    private final static int CROSS_WIDTH = 4;
    private Bitmap mLastBitmap = null;
    private Bitmap mHSIPowerOffBackgroundBitmap = null;
    private Bitmap mHSIPowerOnBackgroundBitmap = null;
    private Bitmap mHSICompassCardBitmap = null;
    Matrix mMatrix = new Matrix();
    //private int mAngle = 0;

    private String mData = "0";


    public A10C_HSI_View(Context context) {
        super(context);
        mHSIPowerOffBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_background_power_off);
        mHSIPowerOnBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_background_power_on);
        mHSICompassCardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_compass_card);
        setImageBitmap(mHSIPowerOffBackgroundBitmap);
    }

    public void updateLayoutSize() {
        invalidate();
    }


    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: HSI 1");
        float imageCenterX = (float) canvas.getWidth()/2f;
        float imageCenterY = (float) ((canvas.getHeight()/2f) + 7);
        /*
            return GetDevice(0):get_argument_value(12)

            HSI_HDG #34# HSI Heading 1 -> 0 000 -> 360


            HSI_PWROFF_FLAG", 40, {0, 1}, "HSI", "HSI Poweroff Flag")
            HSI_RANGE_FLAG", 32, {0, 1}, "HSI", "HSI Range Flag")
            HSI_BEARING_FLAG", 46, {0, 1}, "HSI", "HSI Bearing Flag")
            HSI_BEARING1", 33, {0, 1}, "HSI", "HSI Bearing Pointer 1")
            HSI_BEARING2", 35, {0, 1}, "HSI", "HSI Bearing Pointer 2")
            HSI_HDG_BUG", 36, {0, 1}, "HSI", "HSI Heading Bug")    -> Rectangular box with slit
            HSI_CRS", 47, {0, 1}, "HSI", "HSI Course")              -> Course needle
            HSI_CC_A", 37, {0, 1}, "HSI", "HSI Course Counter A")
            HSI_CC_B", 39, {0, 1}, "HSI", "HSI Course Counter B")
            HSI_RC_A", 28, {0, 1}, "HSI", "HSI Range Counter A")
            HSI_RC_B", 29, {0, 1}, "HSI", "HSI Range Counter B")
            HSI_RC_C", 30, {0, 1}, "HSI", "HSI Range Counter C")
            HSI_RC_D", 31, {0, 1}, "HSI", "HSI Range Counter D")
            HSI_DEVIATION", 41, {-1, 1}, "HSI", "HSI Deviation")
            HSI_TOFROM1", 42, {0, 1}, "HSI", "HSI TO/FROM 1")
            HSI_TOFROM2", 43, {0, 1}, "HSI", "HSI TO/FROM 2")
        */
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
            finalWidth = canvas.getWidth() / 2;
        }
        //Log.d(TAG, "finalWidth " + finalWidth);
        //Log.d(TAG, "finalWidth " + finalWidth);
        Bitmap scaledCompassCardBitmap = Bitmap.createScaledBitmap(mHSICompassCardBitmap, (int) finalWidth , (int) finalWidth, true);


        float tmpAngle = -45;
        Matrix matrix = new Matrix();
        matrix.reset();
        float px = imageCenterX;
        float py = imageCenterY;
        matrix.postTranslate(-scaledCompassCardBitmap.getWidth()/2, -scaledCompassCardBitmap.getHeight()/2);
        matrix.postRotate(tmpAngle);
        matrix.postTranslate(px, py);
        canvas.drawBitmap(scaledCompassCardBitmap, matrix, null);

        Log.d(TAG, "onDraw: HSI 2");
    }

    //private float GetFinalHeight
    public void setData(String pData) {
        mData = pData;
        Log.d(TAG, "setData(String pData)");
        invalidate();
    }
}