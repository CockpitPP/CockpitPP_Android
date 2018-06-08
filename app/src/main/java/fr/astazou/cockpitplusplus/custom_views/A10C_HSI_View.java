package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import java.util.Locale;

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
    private boolean mLastBackPlateDrawnIsPowerOff = false;
    private Bitmap mHSICompassCardBitmap = null;
    private Matrix mMatrix = new Matrix();
    private float mFinalWidthCompassCard = -1;
    private Bitmap mScaledCompassCardBitmap = null;
    private Bitmap mBlankCourseArrowBitmap = null;
    private Bitmap mCourseArrowFromStationBitmap = null;
    private Bitmap mCourseArrowFromStationOffCourseBitmap = null;
    private Bitmap mCourseArrowToStationBitmap = null;
    private Bitmap mCourseArrowToStationOffCourseBitmap = null;
    private Bitmap mDeviationLineBitmap = null;
    private Bitmap mNumberOneArrowBitmap = null;
    private Bitmap mNumberTwoArrowBitmap = null;
    private Bitmap mAirplaneSymbolBitmap = null;
    private Bitmap mScaledAirplaneSymbolBitmap = null;
    private Bitmap mCourseNeedleUsedBitmap = null;
    private Bitmap mHeadingBugBitmap = null;

    private int mHSICourseNeedle = 0;
    private int mHSIHeading = 0;
    private boolean mHSIPowerIsOff = false;
    private boolean mHSIRangeFlagIsOn = false;
    private boolean mHSIOffCourseWarningFlagIsOn = false;
    private int mHSIBearing1 = 0;
    private int mHSIBearing2 = 0;
    private int mHSIHeadingBug = 0;
    private int mHSIRangeDigitA = 0;
    private int mHSIRangeDigitB = 0;
    private int mHSIRangeDigitC = 0;
    private int mHSIRangeDigitD = 0;
    private int mHSICourseCounterA = 0;
    private int mHSICourseCounterB = 0;
    private int mHSICourseDeviation = 0;
    private boolean mHSIFlyingTowardsStation = false;
    private boolean mHSIFlyingFromStation = false;

    //private String mData = "0";


    public A10C_HSI_View(Context context) {
        super(context);
        mHSIPowerOffBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_backplate_power_off);
        mHSIPowerOnBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_backplate);
        mHSICompassCardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_compass_card);
        mBlankCourseArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_course_arrow_blank);
        mCourseArrowFromStationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_course_arrow_from_station);
        mCourseArrowFromStationOffCourseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_course_arrow_from_station_off_course);
        mCourseArrowToStationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_course_arrow_to_station);
        mCourseArrowToStationOffCourseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_course_arrow_to_station_off_course);
        mDeviationLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_deviation_line_3);
        mNumberOneArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_number_1_arrow);
        mNumberTwoArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_number_2_arrow);
        mAirplaneSymbolBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_plane_symbol);
        mHeadingBugBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_heading_bug);
         //= BitmapFactory.decodeResource(getResources(), R.drawable.);
        setImageBitmap(mHSIPowerOffBackgroundBitmap);
        mLastBackPlateDrawnIsPowerOff = true;
    }

    public void updateLayoutSize() {
        invalidate();
    }


    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(!mHSIPowerIsOff && mLastBackPlateDrawnIsPowerOff){
            mLastBackPlateDrawnIsPowerOff = false;
            setImageBitmap(mHSIPowerOnBackgroundBitmap);
        }
        else if(mHSIPowerIsOff && !mLastBackPlateDrawnIsPowerOff){
            mLastBackPlateDrawnIsPowerOff = true;
            setImageBitmap(mHSIPowerOffBackgroundBitmap);
        }
        float imageCenterX = (float) canvas.getWidth()/2f;
        float imageCenterY = (float) ((canvas.getHeight()/2f) + 7);

        float finalWidthCompassCard = 0;
        if(canvas.getWidth() > canvas.getHeight()){
            //landscape
            finalWidthCompassCard = Math.round(canvas.getHeight() / 1.4);
        }else {
            //portrait
            finalWidthCompassCard =  Math.round(canvas.getWidth() / 1.9);
        }
        //Log.d(TAG, "finalWidth " + finalWidth);
        //Log.d(TAG, "finalWidth " + finalWidth);
        if(finalWidthCompassCard != mFinalWidthCompassCard || mScaledCompassCardBitmap == null){
            ScaleCompassCard(finalWidthCompassCard);
        }

        mMatrix.reset();
        mMatrix.postTranslate(-mScaledCompassCardBitmap.getWidth()/2f, -mScaledCompassCardBitmap.getHeight()/2f);
        mMatrix.postRotate(mHSIHeading);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        canvas.drawBitmap(mScaledCompassCardBitmap, mMatrix, null);
        //Compass card etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        if(!mHSIFlyingTowardsStation && !mHSIFlyingFromStation){
            if(mCourseNeedleUsedBitmap == null || !mCourseNeedleUsedBitmap.equals(mBlankCourseArrowBitmap)){
                mCourseNeedleUsedBitmap = mBlankCourseArrowBitmap;
            }
        }
        else if(mHSIFlyingFromStation){
            if(mHSIOffCourseWarningFlagIsOn){

                if(mCourseNeedleUsedBitmap == null || !mCourseNeedleUsedBitmap.equals(mCourseArrowFromStationOffCourseBitmap)){
                    mCourseNeedleUsedBitmap = mCourseArrowFromStationOffCourseBitmap;
                }
            }else{

                if(mCourseNeedleUsedBitmap == null || !mCourseNeedleUsedBitmap.equals(mCourseArrowFromStationBitmap)){
                    mCourseNeedleUsedBitmap = mCourseArrowFromStationBitmap;
                }
            }
        }
        else if(mHSIFlyingTowardsStation){
            if(mHSIOffCourseWarningFlagIsOn){

                if(mCourseNeedleUsedBitmap == null || !mCourseNeedleUsedBitmap.equals(mCourseArrowToStationOffCourseBitmap)){
                    mCourseNeedleUsedBitmap = mCourseArrowToStationOffCourseBitmap;
                }
            }else{

                if(mCourseNeedleUsedBitmap == null || !mCourseNeedleUsedBitmap.equals(mCourseArrowToStationBitmap)){
                    mCourseNeedleUsedBitmap = mCourseArrowToStationBitmap;
                }
            }
        }
        if(Math.round(mFinalWidthCompassCard * 0.98) < mCourseNeedleUsedBitmap.getWidth()){
            ScaleUsedCourseArrow(mFinalWidthCompassCard);
        }
        mMatrix.reset();
        mMatrix.postTranslate(-mCourseNeedleUsedBitmap.getWidth()/2f, -mCourseNeedleUsedBitmap.getHeight()/2f);
        mMatrix.postRotate(mHSICourseNeedle);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        canvas.drawBitmap(mCourseNeedleUsedBitmap, mMatrix, null);
        //Course needle etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        int absoluteCourse = 360 - mHSIHeading + mHSICourseNeedle;
        if(absoluteCourse > 360){
            absoluteCourse = absoluteCourse - 360;
        }
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(true);
        paint.setTextSize(canvas.getHeight() / 16);
        paint.setDither(true);
        String courseString = String.format(Locale.ENGLISH, "%03d", absoluteCourse);
        canvas.drawText(courseString, Math.round(canvas.getWidth() /1.46), Math.round(canvas.getHeight() /4), paint);
        //Course number etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/


        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(true);
        paint.setTextSize(canvas.getHeight() / 16);
        paint.setDither(true);
        String rangeString = String.format(Locale.ENGLISH, "%04d", mHSIRangeDigitA*1000 + mHSIRangeDigitB * 100 + mHSIRangeDigitC * 10 + mHSIRangeDigitD);
        canvas.drawText(rangeString, Math.round(canvas.getWidth() /3.95), Math.round(canvas.getHeight() /4), paint);
        //Range number etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        if(mScaledAirplaneSymbolBitmap == null){
            ScaleAirplaneSymbol(finalWidthCompassCard);
        }
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledAirplaneSymbolBitmap.getWidth()/2f, -mScaledAirplaneSymbolBitmap.getHeight()/2f);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        canvas.drawBitmap(mScaledAirplaneSymbolBitmap, mMatrix, null);
        //Course needle etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        /*float x = 0;
        float y = 0;
        mMatrix.reset();
        Bitmap rotatedHeadingBug = mHeadingBugBitmap;// RotateHeadingBugImage();
        //mMatrix.setTranslate(mScaledCompassCardBitmap.getWidth() / 2f, mScaledCompassCardBitmap.getHeight() / 2f);
        if (mHSIHeadingBug >= 0 || mHSIHeadingBug < 90)
        {
            x = (float)(Math.asin(Math.PI / 180 * mHSIHeadingBug) * 5);
            y = (float)(Math.acos(Math.PI / 180 * mHSIHeadingBug) * 5) * -1;
        }

        //mMatrix.postTranslate(x - rotatedHeadingBug.getWidth() / 2f, y - rotatedHeadingBug.getHeight() / 2f);
        mMatrix.postTranslate(rotatedHeadingBug.getWidth() / 2f, rotatedHeadingBug.getHeight() / 2f);
        mMatrix.postRotate(mHSIHeadingBug);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        canvas.drawBitmap(rotatedHeadingBug, mMatrix, null);

        /*graphics.TranslateTransform(hsiBackPlateImage.Width / 2f, hsiBackPlateImage.Height / 2f + 3);
        graphics.RotateTransform(_a10CHsiDataHolderClass.HsiBearing2);
        graphics.TranslateTransform(_hsiBearingTwoArrowImage.Width / -2f, _hsiBearingTwoArrowImage.Height / -2f);
        var hsiBearingTwoArrowImageRectangle = new Rectangle(0, 0, _hsiBearingTwoArrowImage.Width, _hsiBearingTwoArrowImage.Height);
        graphics.DrawImage(_hsiBearingTwoArrowImage, hsiBearingTwoArrowImageRectangle, 0, 0, _hsiBearingTwoArrowImage.Width, _hsiBearingTwoArrowImage.Height, GraphicsUnit.Pixel, _blackTransparency);*/
        //Heading bug etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        //Log.d(TAG, "onDraw: HSI 2X");
    }

    private Bitmap RotateHeadingBugImage(){
        // Rotation information
        Matrix matrix = new Matrix();
        matrix.postRotate(mHSIHeadingBug);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mHeadingBugBitmap,Math.round(mHeadingBugBitmap.getWidth()/2f),Math.round(mHeadingBugBitmap.getHeight()/2f),true);
        scaledBitmap.setHasAlpha(true);
        return Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
    }

    private void ScaleAirplaneSymbol(float finalWidth){
        mScaledAirplaneSymbolBitmap = Bitmap.createScaledBitmap(mAirplaneSymbolBitmap, (int) Math.round(finalWidth/1.1) , (int) Math.round(finalWidth/1.1), true);
    }

    private void ScaleCompassCard(float finalWidth){
        Bitmap scaledCompassCardBitmap = Bitmap.createScaledBitmap(mHSICompassCardBitmap, (int) finalWidth , (int) finalWidth, true);
        mFinalWidthCompassCard = finalWidth;
        mScaledCompassCardBitmap = scaledCompassCardBitmap;
    }

    private void ScaleUsedCourseArrow(float CompassCardWidth){
        mCourseNeedleUsedBitmap = Bitmap.createScaledBitmap(mCourseNeedleUsedBitmap, (int) Math.round(CompassCardWidth * 0.98) , (int) Math.round(CompassCardWidth * 0.98), true);
    }

    public void setData(String pData) {

        String[] messageArray = pData.split(";");
        mHSICourseNeedle =  Math.round(360*Float.parseFloat(messageArray[0]));
        mHSIPowerIsOff =  Float.parseFloat(messageArray[1]) > 0;
        mHSIRangeFlagIsOn =  Float.parseFloat(messageArray[2]) > 0;
        mHSIOffCourseWarningFlagIsOn =  Float.parseFloat(messageArray[3]) > 0;
        mHSIHeading =  Math.round(360*Float.parseFloat(messageArray[4]));
        mHSIBearing1 =  Math.round(360*Float.parseFloat(messageArray[5]));
        mHSIBearing2 =  Math.round(360*Float.parseFloat(messageArray[6]));
        mHSIHeadingBug =  Math.round(360*Float.parseFloat(messageArray[7]));
        //mHSICourseCounterA
        //mHSICourseCounterB
        mHSIRangeDigitA  = Math.round(10*Float.parseFloat(messageArray[10]));
        mHSIRangeDigitB  = Math.round(10*Float.parseFloat(messageArray[11]));
        mHSIRangeDigitC  = Math.round(10*Float.parseFloat(messageArray[12]));
        mHSIRangeDigitD  = Math.round(10*Float.parseFloat(messageArray[13]));
        mHSICourseDeviation = Math.round(10*Float.parseFloat(messageArray[14])) - 5;
        mHSIFlyingTowardsStation = Float.parseFloat(messageArray[15]) > 0;
        mHSIFlyingFromStation = Float.parseFloat(messageArray[16]) > 0;
        /*
				[0]     hsi_course = MainPanel:get_argument_value(47)
				[1]     hsi_power_off_flag =  MainPanel:get_argument_value(40)
				[2]     hsi_range_flag =  MainPanel:get_argument_value(32)
				[3]     hsi_bearing_flag =  MainPanel:get_argument_value(46)
				[4]     hsi_hdg =  MainPanel:get_argument_value(34)
				[5]     hsi_bearing1 =  MainPanel:get_argument_value(33)
				[6]     hsi_bearing2 =  MainPanel:get_argument_value(35)
				[7]     hsi_hdg_bug =  MainPanel:get_argument_value(36)
				[8]     hsi_cc_a =  MainPanel:get_argument_value(37)
				[9]     hsi_cc_b =  MainPanel:get_argument_value(39)
				[10]     hsi_rc_a =  MainPanel:get_argument_value(28)
				[11]     hsi_rc_b =  MainPanel:get_argument_value(29)
				[12]     hsi_rc_c =  MainPanel:get_argument_value(30)
				[13]     hsi_rc_d =  MainPanel:get_argument_value(31)
				[14]     hsi_deviation =  MainPanel:get_argument_value(41)
				[15]     hsi_tofrom1 =  MainPanel:get_argument_value(42)
				[16]     hsi_tofrom2 =  MainPanel:get_argument_value(43)
        */
        invalidate();
    }
}