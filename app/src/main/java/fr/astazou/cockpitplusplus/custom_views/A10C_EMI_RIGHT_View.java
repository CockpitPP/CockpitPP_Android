package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.AppCompatImageView;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by JerkerD on 15.06.2018.
 */

public class A10C_EMI_RIGHT_View extends AppCompatImageView {


    private Matrix mMatrix = new Matrix();
    private Bitmap mEMIRightPanelBackgroundBitmap = null;
    private Bitmap mEMIRightBigNeedleBitmap = null;
    private Bitmap mEMIRightSmallNeedleBitmap = null;
    private Bitmap mScaledEMIRightPanelBackgroundBitmap = null;
    private Bitmap mScaledEMIRightBigNeedleBitmap = null;
    private Bitmap mScaledEMIRightSmallNeedleBitmap = null;

    private float mLeftEngFanSpeed = 0;
    private float mRightEngFanSpeed = 0;
    private float mLeftEngFuelFlow = 0;
    private float mRightEngFuelFlow = 0;
    private float mAPURPM = 0;
    private float mAPUTemp = 0;

    private boolean mDataHasBeenReceived = false;

    public A10C_EMI_RIGHT_View(Context context) {
        super(context);
        mEMIRightPanelBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_emi_right_panel);
        mEMIRightBigNeedleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_emi_l_panel_needle);
        mEMIRightSmallNeedleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_emi_l_panel_small_needle);
        //= BitmapFactory.decodeResource(getResources(), R.drawable.);
        setImageBitmap(mEMIRightPanelBackgroundBitmap);
    }

    public void updateLayoutSize() {
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        mScaledEMIRightPanelBackgroundBitmap = null;
        mScaledEMIRightBigNeedleBitmap = null;
        mScaledEMIRightSmallNeedleBitmap = null;
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mDataHasBeenReceived) {
            //For testing on emulator
            mLeftEngFanSpeed = 2;
            mRightEngFanSpeed = 17;
            mLeftEngFuelFlow = 90;
            mRightEngFuelFlow = 148;
            mAPURPM = 330;
            mAPUTemp = 340;
        }

        Canvas localCanvas = new Canvas();

        Bitmap workPlateBitmap = mEMIRightPanelBackgroundBitmap.copy(mEMIRightPanelBackgroundBitmap.getConfig(), true);
        //android.graphics.Bitmap.Config.ARGB_8888
        localCanvas.setBitmap(workPlateBitmap);

        if (mScaledEMIRightSmallNeedleBitmap == null) {
            ScaleSmallNeedle(workPlateBitmap);
        }
        if (mScaledEMIRightBigNeedleBitmap == null) {
            ScaleBigNeedle(workPlateBitmap);
        }

        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMIRightBigNeedleBitmap.getWidth() / 2f, -mScaledEMIRightBigNeedleBitmap.getHeight() + (mScaledEMIRightBigNeedleBitmap.getHeight() - (mScaledEMIRightBigNeedleBitmap.getHeight() * 0.87f)));
        mMatrix.postRotate(mLeftEngFanSpeed);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.26963350785340314136125654450262f, workPlateBitmap.getHeight()*0.18115942028985507246376811594203f);
        localCanvas.drawBitmap(mScaledEMIRightBigNeedleBitmap, mMatrix, null);
        //Left FAN speed gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMIRightBigNeedleBitmap.getWidth() / 2f, -mScaledEMIRightBigNeedleBitmap.getHeight() + (mScaledEMIRightBigNeedleBitmap.getHeight() - (mScaledEMIRightBigNeedleBitmap.getHeight() * 0.87f)));
        mMatrix.postRotate(mRightEngFanSpeed);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.74607329842931937172774869109948f, workPlateBitmap.getHeight()*0.18115942028985507246376811594203f);
        localCanvas.drawBitmap(mScaledEMIRightBigNeedleBitmap, mMatrix, null);
        //Right FAN speed gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMIRightBigNeedleBitmap.getWidth() / 2f, -mScaledEMIRightBigNeedleBitmap.getHeight() + (mScaledEMIRightBigNeedleBitmap.getHeight() - (mScaledEMIRightBigNeedleBitmap.getHeight() * 0.87f)));
        mMatrix.postRotate(mLeftEngFuelFlow);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.26178010471204188481675392670157f, workPlateBitmap.getHeight()*0.50271739130434782608695652173913f);
        localCanvas.drawBitmap(mScaledEMIRightBigNeedleBitmap, mMatrix, null);
        //Left Fuel flow gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMIRightBigNeedleBitmap.getWidth() / 2f, -mScaledEMIRightBigNeedleBitmap.getHeight() + (mScaledEMIRightBigNeedleBitmap.getHeight() - (mScaledEMIRightBigNeedleBitmap.getHeight() * 0.87f)));
        mMatrix.postRotate(mRightEngFuelFlow);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.73821989528795811518324607329843f, workPlateBitmap.getHeight()*0.50271739130434782608695652173913f);
        localCanvas.drawBitmap(mScaledEMIRightBigNeedleBitmap, mMatrix, null);
        //Right Fuel flow gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMIRightBigNeedleBitmap.getWidth() / 2f, -mScaledEMIRightBigNeedleBitmap.getHeight() + (mScaledEMIRightBigNeedleBitmap.getHeight() - (mScaledEMIRightBigNeedleBitmap.getHeight() * 0.87f)));
        mMatrix.postRotate(mAPURPM);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.26178010471204188481675392670157f, workPlateBitmap.getHeight()*0.81974637681159420289855072463768f);
        localCanvas.drawBitmap(mScaledEMIRightBigNeedleBitmap, mMatrix, null);
        //APU RPM (left) gauge ready
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        mMatrix.reset();
        //The swivel point of the needle
        mMatrix.postTranslate(-mScaledEMIRightBigNeedleBitmap.getWidth() / 2f, -mScaledEMIRightBigNeedleBitmap.getHeight() + (mScaledEMIRightBigNeedleBitmap.getHeight() - (mScaledEMIRightBigNeedleBitmap.getHeight() * 0.87f)));
        mMatrix.postRotate(mAPUTemp);
        //Where it should be drawn
        //Center of the gauge
        mMatrix.postTranslate(workPlateBitmap.getWidth()*0.73821989528795811518324607329843f, workPlateBitmap.getHeight()*0.81974637681159420289855072463768f);
        localCanvas.drawBitmap(mScaledEMIRightBigNeedleBitmap, mMatrix, null);
        //APU RPM (left) gauge ready
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
        float scale = mEMIRightBigNeedleBitmap.getHeight() / (mEMIRightBigNeedleBitmap.getWidth() * 1.01f);
        float finalWidth = Math.round(bitmap.getWidth() * 0.053f);
        mScaledEMIRightBigNeedleBitmap = Bitmap.createScaledBitmap(mEMIRightBigNeedleBitmap, Math.round(finalWidth), Math.round(finalWidth * scale), true);
    }

    private void ScaleSmallNeedle(Bitmap bitmap) {
        int finalWidth = (int) Math.round(bitmap.getWidth() * 0.01);
        int finalHeight = (int) Math.round(bitmap.getWidth() * 0.10);
        mScaledEMIRightSmallNeedleBitmap = Bitmap.createScaledBitmap(mEMIRightSmallNeedleBitmap, (int) finalWidth, (int) finalHeight, true);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setData(String pData) {

        mDataHasBeenReceived = true;
        String[] messageArray = pData.split(";");
        mLeftEngFanSpeed = 315 * Float.parseFloat(messageArray[0]) + 50f;
        mRightEngFanSpeed = 315 * Float.parseFloat(messageArray[1]) + 50f;
        mLeftEngFuelFlow = 302 * Float.parseFloat(messageArray[2]) + 148;
        mRightEngFuelFlow = 302 * Float.parseFloat(messageArray[3]) + 148;
        mAPURPM = 250 * Float.parseFloat(messageArray[4]) + 90f;
        mAPUTemp = 250 * Float.parseFloat(messageArray[5]) + 90f;
        /*
            EMI right panel gauges table :
            [0] = engine_left_fan_speed
            [1] = engine_right_fan_speed
            [2] = engine_left_fuel_flow
            [3] = engine_right_fuel_flow
            [4] = apu_rpm
            [5] = apu_temperature
		*/
        invalidate();
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


}