package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;

import java.util.Locale;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by JerkerD on 15.06.2018.
 */

public class A10C_EMI_LEFT_View extends AppCompatImageView {


    private Matrix mMatrix = new Matrix();
    private Bitmap mEMILeftPanelBackgroundBitmap = null;
    private Bitmap mEMILeftBigNeedleBitmap = null;
    private Bitmap mEMILeftSmallNeedleBitmap = null;
    private Bitmap mScaledEMILeftPanelBackgroundBitmap = null;
    private Bitmap mScaledEMILeftBigNeedleBitmap = null;
    private Bitmap mScaledEMILeftSmallNeedleBitmap = null;

    private float mLeftEngCoreSpeedTenth = 0;
    private float mRightEngCoreSpeedTenth = 0;
    private float mLeftEngCoreSpeed = 0;
    private float mRightEngCoreSpeed = 0;
    private float mLeftEngOilPressure = 0;
    private float mRightEngOilPressure = 0;
    private float mLeftTempTenth = 0;
    private float mLeftTemp = 0;
    private float mRightTempTenth = 0;
    private float mRightTemp = 0;

    private boolean mDataHasBeenReceived = false;

    public A10C_EMI_LEFT_View(Context context) {
        super(context);
        mEMILeftPanelBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_emi_l_panel_16bit);
        mEMILeftBigNeedleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_emi_l_panel_needle);
        mEMILeftSmallNeedleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_emi_l_panel_small_needle);
        //= BitmapFactory.decodeResource(getResources(), R.drawable.);
        setImageBitmap(mEMILeftPanelBackgroundBitmap);
    }

    public void updateLayoutSize() {
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        mScaledEMILeftPanelBackgroundBitmap = null;
        mScaledEMILeftBigNeedleBitmap = null;
        mScaledEMILeftSmallNeedleBitmap = null;
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!mDataHasBeenReceived){
            //For testing on emulator
            mLeftEngCoreSpeedTenth = 0;
            mRightEngCoreSpeedTenth = 0;
            mLeftEngCoreSpeed = 5;
            mRightEngCoreSpeed = 0;
            mLeftEngOilPressure = 270;
            mRightEngOilPressure = 0;
            mLeftTemp = 0;
            mLeftTempTenth = 180;
            mRightTemp = 90;
            mRightTempTenth = 0;
        }
        Canvas localCanvas = new Canvas();

        Bitmap workPlateBitmap = mEMILeftPanelBackgroundBitmap.copy(mEMILeftPanelBackgroundBitmap.getConfig(), true);
        //android.graphics.Bitmap.Config.ARGB_8888
        localCanvas.setBitmap(workPlateBitmap);

        if (mScaledEMILeftSmallNeedleBitmap == null) {
            ScaleSmallNeedle(workPlateBitmap);
        }
        if (mScaledEMILeftBigNeedleBitmap == null) {
            ScaleBigNeedle(workPlateBitmap);
        }

        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftSmallNeedleBitmap.getWidth()/2f, -mScaledEMILeftSmallNeedleBitmap.getHeight()/2f);
        mMatrix.postRotate(mLeftTemp);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.391484942887f, workPlateBitmap.getHeight()*0.187009279086f);
        localCanvas.drawBitmap(mScaledEMILeftSmallNeedleBitmap, mMatrix, null);
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftBigNeedleBitmap.getWidth()/2f, -mScaledEMILeftBigNeedleBitmap.getHeight()+ (mScaledEMILeftBigNeedleBitmap.getHeight()-(mScaledEMILeftBigNeedleBitmap.getHeight()*0.87f)));
        mMatrix.postRotate(mLeftTempTenth);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.264797507788f, workPlateBitmap.getHeight()*0.182012847966f);
        localCanvas.drawBitmap(mScaledEMILeftBigNeedleBitmap, mMatrix, null);
        //Left Temp gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftSmallNeedleBitmap.getWidth()/2f, -mScaledEMILeftSmallNeedleBitmap.getHeight()/2f);
        mMatrix.postRotate(mRightTemp);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.86708203530633437175493250259605f, workPlateBitmap.getHeight()*0.1870092790863668807994289793005f);
        localCanvas.drawBitmap(mScaledEMILeftSmallNeedleBitmap, mMatrix, null);
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftBigNeedleBitmap.getWidth()/2f, -mScaledEMILeftBigNeedleBitmap.getHeight()+ (mScaledEMILeftBigNeedleBitmap.getHeight()-(mScaledEMILeftBigNeedleBitmap.getHeight()*0.87f)));
        mMatrix.postRotate(mRightTempTenth);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.74039460020768431983385254413292f, workPlateBitmap.getHeight()*0.18201284796573875802997858672377f);
        localCanvas.drawBitmap(mScaledEMILeftBigNeedleBitmap, mMatrix, null);
        //Right Temp gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftSmallNeedleBitmap.getWidth()/2f, -mScaledEMILeftSmallNeedleBitmap.getHeight()/2f);
        mMatrix.postRotate(mLeftEngCoreSpeed);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.1869158878504672897196261682243f, workPlateBitmap.getHeight()*0.45324768022840827980014275517488f);
        localCanvas.drawBitmap(mScaledEMILeftSmallNeedleBitmap, mMatrix, null);
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftBigNeedleBitmap.getWidth()/2f, -mScaledEMILeftBigNeedleBitmap.getHeight()+ (mScaledEMILeftBigNeedleBitmap.getHeight()-(mScaledEMILeftBigNeedleBitmap.getHeight()*0.87f)));
        mMatrix.postRotate(mLeftEngCoreSpeedTenth);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.26479750778816199376947040498442f, workPlateBitmap.getHeight()*0.50820842255531763026409707351892f);
        localCanvas.drawBitmap(mScaledEMILeftBigNeedleBitmap, mMatrix, null);
        //Left RPM gauge ready
        /************************************************************************************************
        ************************************************************************************************
        ************************************************************************************************/

        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftSmallNeedleBitmap.getWidth()/2f, -mScaledEMILeftSmallNeedleBitmap.getHeight()/2f);
        mMatrix.postRotate(mRightEngCoreSpeed);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.66251298026998961578400830737279f, workPlateBitmap.getHeight()*0.45324768022840827980014275517488f);
        localCanvas.drawBitmap(mScaledEMILeftSmallNeedleBitmap, mMatrix, null);
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftBigNeedleBitmap.getWidth()/2f, -mScaledEMILeftBigNeedleBitmap.getHeight()+ (mScaledEMILeftBigNeedleBitmap.getHeight()-(mScaledEMILeftBigNeedleBitmap.getHeight()*0.87f)));
        mMatrix.postRotate(mRightEngCoreSpeedTenth);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.74039460020768431983385254413292f, workPlateBitmap.getHeight()*0.50820842255531763026409707351892f);
        localCanvas.drawBitmap(mScaledEMILeftBigNeedleBitmap, mMatrix, null);
        //Right RPM gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftBigNeedleBitmap.getWidth()/2f, -mScaledEMILeftBigNeedleBitmap.getHeight()+ (mScaledEMILeftBigNeedleBitmap.getHeight()-(mScaledEMILeftBigNeedleBitmap.getHeight()*0.87f)));
        mMatrix.postRotate(mLeftEngOilPressure);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.26479750778816199376947040498442f, workPlateBitmap.getHeight()*0.83154889364739471805852962169879f);
        localCanvas.drawBitmap(mScaledEMILeftBigNeedleBitmap, mMatrix, null);
        //Left Oil pressure gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMILeftBigNeedleBitmap.getWidth()/2f, -mScaledEMILeftBigNeedleBitmap.getHeight()+ (mScaledEMILeftBigNeedleBitmap.getHeight()-(mScaledEMILeftBigNeedleBitmap.getHeight()*0.87f)));
        mMatrix.postRotate(mRightEngOilPressure);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.74039460020768431983385254413292f, workPlateBitmap.getHeight()*0.83154889364739471805852962169879f);
        localCanvas.drawBitmap(mScaledEMILeftBigNeedleBitmap, mMatrix, null);
        //Right Oil pressure gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        Bitmap finalImage = ScaleBitmap(workPlateBitmap, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(finalImage, canvas.getWidth() / 2f - finalImage.getWidth() / 2f, canvas.getHeight() / 2f - finalImage.getHeight() / 2f, null);
    }

    public static Bitmap ScaleBitmap(Bitmap bitmap, int width, int height) {
        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();

        final float scale = Math.min((float) width / (float) bitmapWidth,
                (float) height / (float) bitmapHeight);

        final int scaledWidth = (int) (bitmapWidth * scale);
        final int scaledHeight = (int) (bitmapHeight * scale);

        final Bitmap decoded = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
        //final Canvas canvas = new Canvas(decoded);

        return decoded;
    }

    private Bitmap RotateImage(Bitmap bitmap, int degrees) {
        // Rotation information
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(bitmap.getWidth() / 2f), Math.round(bitmap.getHeight() / 2f), true);
        scaledBitmap.setHasAlpha(true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    private void ScaleBigNeedle(Bitmap bitmap) {
        float scale = mEMILeftBigNeedleBitmap.getHeight() / (mEMILeftBigNeedleBitmap.getWidth()*1.01f);
        float finalWidth = Math.round(bitmap.getWidth() * 0.058f);
        mScaledEMILeftBigNeedleBitmap = Bitmap.createScaledBitmap(mEMILeftBigNeedleBitmap, Math.round(finalWidth), Math.round(finalWidth * scale), true);
    }

    private void ScaleSmallNeedle(Bitmap bitmap) {
        int finalWidth = (int) Math.round(bitmap.getWidth() * 0.01);
        int finalHeight = (int) Math.round(bitmap.getWidth() * 0.10);
        mScaledEMILeftSmallNeedleBitmap = Bitmap.createScaledBitmap(mEMILeftSmallNeedleBitmap, (int) finalWidth, (int) finalHeight, true);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setData(String pData) {

        mDataHasBeenReceived = true;
        String[] messageArray = pData.split(";");
        /*
        0.91231352090836;
        0.91123354434967;
        0.12313564866781;
        0.11233510822058;
        0.88754773139954;
        0.888592004776*/
        mLeftEngCoreSpeedTenth = 270* Float.parseFloat(messageArray[0]);
        mRightEngCoreSpeedTenth = 270* Float.parseFloat(messageArray[1]);
        mLeftEngCoreSpeed = 360 * Float.parseFloat(messageArray[2]);
        mRightEngCoreSpeed = 360 * Float.parseFloat(messageArray[3]);
        mLeftEngOilPressure = 270 * Float.parseFloat(messageArray[4]) + 135f;
        mRightEngOilPressure = 270 * Float.parseFloat(messageArray[5]) + 135f;
        if(messageArray.length > 6) {
            mLeftTempTenth = 255* Float.parseFloat(messageArray[6]) + 129f;
        }
        if(messageArray.length > 7) {
            mLeftTemp = 360 * Float.parseFloat(messageArray[7]);
        }

        if(messageArray.length > 8) {
            mRightTempTenth = 255* Float.parseFloat(messageArray[8]) + 129f;
        }

        if(messageArray.length > 9) {
            mRightTemp = 360 * Float.parseFloat(messageArray[9]);
        }

        /*
            EMI left panel gauges table :
            [0] = engine_left_core_speed_tenth
            [1] = engine_right_core_speed_tenth
            [2] = engine_left_core_speed
            [3] = engine_right_core_speed
            [4] = engine_left_oil_pressure
            [5] = engine_right_oil_pressure
            [6] = engine_left_temperature_tenth
            [7] = engine_left_temperature_units
            [8] = engine_right_temperature_tenth
            [9] = engine_right_temperature_units
		*/
        invalidate();
    }
/*
    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
*/

}