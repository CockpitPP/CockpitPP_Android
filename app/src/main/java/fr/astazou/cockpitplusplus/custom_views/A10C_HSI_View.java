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
    private Bitmap mHSICurrentBackgroundBitmap = null;
    private boolean mLastBackPlateDrawnIsPowerOff = false;
    private Bitmap mHSICompassCardBitmap = null;
    private Matrix mMatrix = new Matrix();
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
    private Bitmap mScaledCourseNeedleBitmap = null;
    private Bitmap mHeadingBugBitmap = null;

    private int mHSICourseNeedle = 0;
    private int mHSIHeading = 0;
    private boolean mHSIPowerIsOff = false;
    private boolean mHSIRangeFlagIsOn = false;
    private boolean mHSIOffCourseWarningFlagIsOn = false;
    private boolean mCourseArrowHasBeenScaled = false;
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
    private Paint paintHSIText = new Paint();

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
        paintHSIText.setColor(Color.WHITE);
        paintHSIText.setFakeBoldText(true);
        paintHSIText.setTextSize(60);
        paintHSIText.setDither(true);
    }

    public void updateLayoutSize() {
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        mScaledCompassCardBitmap = null;
        mHSICurrentBackgroundBitmap = null;
        mScaledCourseNeedleBitmap = null;
        mCourseArrowHasBeenScaled = false;
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Canvas localCanvas = new Canvas();

        if (!mHSIPowerIsOff && mLastBackPlateDrawnIsPowerOff || mHSICurrentBackgroundBitmap == null) {
            mLastBackPlateDrawnIsPowerOff = false;
            mHSICurrentBackgroundBitmap = mHSIPowerOnBackgroundBitmap;
            //setImageBitmap(mHSIPowerOnBackgroundBitmap);
        } else if (mHSIPowerIsOff && !mLastBackPlateDrawnIsPowerOff || mHSICurrentBackgroundBitmap == null) {
            mLastBackPlateDrawnIsPowerOff = true;
            mHSICurrentBackgroundBitmap = mHSIPowerOffBackgroundBitmap;
            //setImageBitmap(mHSIPowerOffBackgroundBitmap);
        }
        Bitmap workPlateBitmap = mHSICurrentBackgroundBitmap.copy(mHSICurrentBackgroundBitmap.getConfig(), true);
        //android.graphics.Bitmap.Config.ARGB_8888
        localCanvas.setBitmap(workPlateBitmap);
        float imageCenterX = (float) workPlateBitmap.getWidth() / 2f;
        float imageCenterY = (float) workPlateBitmap.getHeight() / 2f;

        if (mScaledCompassCardBitmap == null) {
            ScaleCompassCard(workPlateBitmap);
        }

        mMatrix.reset();
        mMatrix.postTranslate(-mScaledCompassCardBitmap.getWidth() / 2f, -mScaledCompassCardBitmap.getHeight() / 2f);
        mMatrix.postRotate(mHSIHeading);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledCompassCardBitmap, mMatrix, null);
        //Compass card etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        if (!mCourseArrowHasBeenScaled) {
            Bitmap arrowToUseBitmap = null;
            if (!mHSIFlyingTowardsStation && !mHSIFlyingFromStation) {
                if (mScaledCourseNeedleBitmap == null || !mScaledCourseNeedleBitmap.equals(mBlankCourseArrowBitmap)) {
                    arrowToUseBitmap = mBlankCourseArrowBitmap;
                }
            } else if (mHSIFlyingFromStation) {
                if (mHSIOffCourseWarningFlagIsOn) {

                    if (mScaledCourseNeedleBitmap == null || !mScaledCourseNeedleBitmap.equals(mCourseArrowFromStationOffCourseBitmap)) {
                        arrowToUseBitmap = mCourseArrowFromStationOffCourseBitmap;
                    }
                } else {

                    if (mScaledCourseNeedleBitmap == null || !mScaledCourseNeedleBitmap.equals(mCourseArrowFromStationBitmap)) {
                        arrowToUseBitmap = mCourseArrowFromStationBitmap;
                    }
                }
            } else if (mHSIFlyingTowardsStation) {
                if (mHSIOffCourseWarningFlagIsOn) {

                    if (mScaledCourseNeedleBitmap == null || !mScaledCourseNeedleBitmap.equals(mCourseArrowToStationOffCourseBitmap)) {
                        arrowToUseBitmap = mCourseArrowToStationOffCourseBitmap;
                    }
                } else {

                    if (mScaledCourseNeedleBitmap == null || !mScaledCourseNeedleBitmap.equals(mCourseArrowToStationBitmap)) {
                        arrowToUseBitmap = mCourseArrowToStationBitmap;
                    }
                }
            }
            mCourseArrowHasBeenScaled = true;
            ScaleUsedCourseArrow(mScaledCompassCardBitmap, arrowToUseBitmap);
        }
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledCourseNeedleBitmap.getWidth() / 2f, -mScaledCourseNeedleBitmap.getHeight() / 2f);
        mMatrix.postRotate(mHSICourseNeedle);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledCourseNeedleBitmap, mMatrix, null);
        //Course needle etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        String rangeString = String.format(Locale.ENGLISH, "%04d", mHSIRangeDigitA * 1000 + mHSIRangeDigitB * 100 + mHSIRangeDigitC * 10 + mHSIRangeDigitD);
        localCanvas.drawText(rangeString, 90, 245, paintHSIText);
        //Range number etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        int absoluteCourse = 360 - mHSIHeading + mHSICourseNeedle;
        if (absoluteCourse >= 360) {
            absoluteCourse = absoluteCourse - 360;
        }
        String courseString = String.format(Locale.ENGLISH, "%03d", absoluteCourse);
        localCanvas.drawText(courseString, 1000, 245, paintHSIText);
        //Course number etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        if (mScaledAirplaneSymbolBitmap == null) {
            ScaleAirplaneSymbol(mScaledCompassCardBitmap.getWidth());
        }
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledAirplaneSymbolBitmap.getWidth() / 2f, -mScaledAirplaneSymbolBitmap.getHeight() / 2f);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledAirplaneSymbolBitmap, mMatrix, null);
        /*Paint paintTmp = new Paint();
        paintTmp.setColor(Color.MAGENTA);
        paintTmp.setStyle(Paint.Style.STROKE);
        paintTmp.setStrokeWidth(3.0f);
        int x1 = 50;
        int y1 = 792;
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paintTmp);
        canvas.drawRect(x1, y1, x1 + 100, y1 + 100, paintTmp);
        canvas.drawText(String.valueOf(canvas.getWidth()) + "x" + String.valueOf(canvas.getHeight()) + " :  " + string1, 100, 500, paint);
        */
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


        Bitmap finalImage = ScaleBitmap(workPlateBitmap, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(finalImage, canvas.getWidth() / 2 - finalImage.getWidth() / 2, canvas.getHeight() / 2 - finalImage.getHeight() / 2, null);
    }

    public static Bitmap ScaleBitmap(Bitmap bitmap, int width, int height) {
        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();

        final float scale = Math.min((float) width / (float) bitmapWidth,
                (float) height / (float) bitmapHeight);

        final int scaledWidth = (int) (bitmapWidth * scale);
        final int scaledHeight = (int) (bitmapHeight * scale);

        final Bitmap decoded = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
        final Canvas canvas = new Canvas(decoded);

        return decoded;
    }

    private Bitmap RotateHeadingBugImage() {
        // Rotation information
        Matrix matrix = new Matrix();
        matrix.postRotate(mHSIHeadingBug);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mHeadingBugBitmap, Math.round(mHeadingBugBitmap.getWidth() / 2f), Math.round(mHeadingBugBitmap.getHeight() / 2f), true);
        scaledBitmap.setHasAlpha(true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    private void ScaleAirplaneSymbol(float finalWidth) {
        mScaledAirplaneSymbolBitmap = Bitmap.createScaledBitmap(mAirplaneSymbolBitmap, (int) Math.round(finalWidth / 1.1), (int) Math.round(finalWidth / 1.1), true);
    }

    private void ScaleCompassCard(Bitmap bitmap) {
        int finalWidth = (int) Math.round(bitmap.getWidth() * 0.6);
        Bitmap scaledCompassCardBitmap = Bitmap.createScaledBitmap(mHSICompassCardBitmap, (int) finalWidth, (int) finalWidth, true);
        mScaledCompassCardBitmap = scaledCompassCardBitmap;
    }

    private void ScaleUsedCourseArrow(Bitmap compassCardBitmap, Bitmap courseArrowToUseBitmap) {
        int finalWidth = (int) Math.round(compassCardBitmap.getWidth() * 0.98);
        Bitmap tmp = Bitmap.createScaledBitmap(courseArrowToUseBitmap, (int) finalWidth, (int) finalWidth, true);
        mScaledCourseNeedleBitmap = tmp;
    }

    public void setData(String pData) {

        String[] messageArray = pData.split(";");
        mHSICourseNeedle = Math.round(360 * Float.parseFloat(messageArray[0]));
        mHSIPowerIsOff = Float.parseFloat(messageArray[1]) > 0;
        mHSIRangeFlagIsOn = Float.parseFloat(messageArray[2]) > 0;
        mHSIOffCourseWarningFlagIsOn = Float.parseFloat(messageArray[3]) > 0;
        mHSIHeading = Math.round(360 * Float.parseFloat(messageArray[4]));
        mHSIBearing1 = Math.round(360 * Float.parseFloat(messageArray[5]));
        mHSIBearing2 = Math.round(360 * Float.parseFloat(messageArray[6]));
        mHSIHeadingBug = Math.round(360 * Float.parseFloat(messageArray[7]));
        //mHSICourseCounterA
        //mHSICourseCounterB
        mHSIRangeDigitA = Math.round(10 * Float.parseFloat(messageArray[10]));
        mHSIRangeDigitB = Math.round(10 * Float.parseFloat(messageArray[11]));
        mHSIRangeDigitC = Math.round(10 * Float.parseFloat(messageArray[12]));
        mHSIRangeDigitD = Math.round(10 * Float.parseFloat(messageArray[13]));
        mHSICourseDeviation = Math.round(10 * Float.parseFloat(messageArray[14])) - 5;
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