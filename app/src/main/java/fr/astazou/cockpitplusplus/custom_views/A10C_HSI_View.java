package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import java.util.Locale;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by JerkerD on 18.12.2017.
 */

public class A10C_HSI_View extends AppCompatImageView {


    private Bitmap mHSIPowerOffBackgroundBitmap = null;
    private Bitmap mHSIPowerOnBackgroundBitmap = null;
    private Bitmap mHSICurrentBackgroundBitmap = null;
    private boolean mLastBackPlateDrawnIsPowerOff = false;
    private Bitmap mHSICompassCardBitmap = null;
    private Matrix mMatrix = new Matrix();
    private Bitmap mScaledCompassCardBitmap = null;
    private Bitmap mScaledHeadingBugBitmap = null;
    private Bitmap mBlankCourseArrowBitmap = null;
    private Bitmap mCourseArrowFromStationBitmap = null;
    private Bitmap mCourseArrowFromStationOffCourseBitmap = null;
    private Bitmap mCourseArrowToStationBitmap = null;
    private Bitmap mCourseArrowToStationOffCourseBitmap = null;
    private Bitmap mNumberOneArrowBitmap = null;
    private Bitmap mNumberTwoArrowBitmap = null;
    private Bitmap mScaledNumberOneArrowBitmap = null;
    private Bitmap mScaledNumberTwoArrowBitmap = null;
    private Bitmap mAirplaneSymbolBitmap = null;
    private Bitmap mScaledAirplaneSymbolBitmap = null;
    private Bitmap mScaledCourseNeedleBitmap = null;
    private Bitmap mHeadingBugBitmap = null;

    private float mHSICourseNeedle = 0;
    private float mHSIHeading = 0;
    private boolean mHSIPowerIsOff = false;
    private boolean mHSIRangeFlagIsOn = false;
    private boolean mHSIOffCourseWarningFlagIsOn = false;
    private float mHSIBearing1 = 0;
    private float mHSIBearing2 = 0;
    private float mHSIHeadingBug = 0;
    //private int mHSIRangeDigitA = 0;
    private int mHSIRangeDigitB = 0;
    private int mHSIRangeDigitC = 0;
    private int mHSIRangeDigitD = 0;
    //private int mHSICourseCounterA = 0;
    //private int mHSICourseCounterB = 0;
    private float mHSICourseDeviation = 0;
    private boolean mHSIFlyingTowardsStation = false;
    private boolean mHSIFlyingFromStation = false;
    private Paint paintHSIText = new Paint();
    private Paint paintWhiteLine = new Paint();

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
        mNumberOneArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_number_1_arrow);
        mNumberTwoArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_number_2_arrow);
        mAirplaneSymbolBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_plane_symbol);
        mHeadingBugBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_hsi_heading_bug_offset);
        //= BitmapFactory.decodeResource(getResources(), R.drawable.);
        setImageBitmap(mHSIPowerOffBackgroundBitmap);
        mLastBackPlateDrawnIsPowerOff = true;
        paintHSIText.setColor(Color.WHITE);
        paintHSIText.setFakeBoldText(true);
        paintHSIText.setDither(true);
        paintWhiteLine.setColor(Color.WHITE);
        paintWhiteLine.setDither(true);
    }

    public void updateLayoutSize() {
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        mScaledCompassCardBitmap = null;
        mHSICurrentBackgroundBitmap = null;
        mScaledCourseNeedleBitmap = null;
        mScaledNumberOneArrowBitmap = null;
        mScaledNumberTwoArrowBitmap = null;
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

        ScaleUsedCourseArrow(mScaledCompassCardBitmap, arrowToUseBitmap);
        Canvas devLineCanvas = new Canvas();
        devLineCanvas.setBitmap(mScaledCourseNeedleBitmap);
        float x1 = devLineCanvas.getWidth() / 2f - devLineCanvas.getWidth() * 0.01075268817204301075268817204301f + mHSICourseDeviation * devLineCanvas.getWidth()*0.03072196620583717357910906298003f;
        float x2 = devLineCanvas.getWidth() / 2f + devLineCanvas.getWidth() * 0.01075268817204301075268817204301f + mHSICourseDeviation * devLineCanvas.getWidth()*0.03072196620583717357910906298003f;
        devLineCanvas.drawRect(x1, devLineCanvas.getHeight() / 2f - 150f, x2, devLineCanvas.getHeight() / 2f + 150f, paintWhiteLine);
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledCourseNeedleBitmap.getWidth() / 2f, -mScaledCourseNeedleBitmap.getHeight() / 2f);
        mMatrix.postRotate(mHSICourseNeedle);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledCourseNeedleBitmap, mMatrix, null);
        //Course needle etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        paintHSIText.setTextSize(workPlateBitmap.getHeight() * 0.06048387096774193548387096774194f);
        if (!mHSIPowerIsOff) {
            String rangeString = String.format(Locale.ENGLISH, "%03d", mHSIRangeDigitB * 100 + mHSIRangeDigitC * 10 + mHSIRangeDigitD);
            //localCanvas.drawText(rangeString, pxFromDp(getContext(), 63), pxFromDp(getContext(), 122), paintHSIText);
            localCanvas.drawText(rangeString, workPlateBitmap.getWidth() * 0.10347682119205298013245033112583f, workPlateBitmap.getHeight() * 0.2469758064516129032258064516129f, paintHSIText);
        }
        //Range number etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/
        if (!mHSIPowerIsOff) {
            //Log.d("Heading + Needle", String.valueOf(360-mHSIHeading) + " " + String.valueOf(mHSICourseNeedle));
            int absoluteCourse = 360 - Math.round(mHSIHeading) + Math.round(mHSICourseNeedle);
            if (absoluteCourse >= 360) {
                absoluteCourse = absoluteCourse - 360;
            }
            if (absoluteCourse < 0) {
                absoluteCourse = absoluteCourse + 360;
            }
            //Log.d("absoluteCourse", String.valueOf(absoluteCourse));
            String courseString = String.format(Locale.ENGLISH, "%03d", absoluteCourse);
            localCanvas.drawText(courseString, workPlateBitmap.getWidth() * 0.82781456953642384105960264900662f, workPlateBitmap.getHeight() * 0.2469758064516129032258064516129f, paintHSIText);
            //localCanvas.drawText(courseString, pxFromDp(getContext(), 505), pxFromDp(getContext(), 122), paintHSIText);
        }
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
        //Course needle etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        if (mScaledHeadingBugBitmap == null) {
            ScaleHeadingBug(mScaledCompassCardBitmap);
        }
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledHeadingBugBitmap.getWidth() / 2f, -mScaledHeadingBugBitmap.getHeight() / 2f);
        mMatrix.postRotate(mHSIHeadingBug);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledHeadingBugBitmap, mMatrix, null);
        //Heading bug etched
        /************************************************************************************************
         ************************************************************************************************
         ************************************************************************************************/

        if (mScaledNumberOneArrowBitmap == null) {
            mScaledNumberOneArrowBitmap = ScaleBearingNumberLine(mNumberOneArrowBitmap, mScaledCompassCardBitmap);
        }
        if (mScaledNumberTwoArrowBitmap == null) {
            mScaledNumberTwoArrowBitmap = ScaleBearingNumberLine(mNumberTwoArrowBitmap, mScaledCompassCardBitmap);
        }
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledNumberOneArrowBitmap.getWidth() / 2f, -mScaledNumberOneArrowBitmap.getHeight() / 2f);
        mMatrix.postRotate(mHSIBearing1);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledNumberOneArrowBitmap, mMatrix, null);
        mMatrix.reset();
        mMatrix.postTranslate(-mScaledNumberTwoArrowBitmap.getWidth() / 2f, -mScaledNumberTwoArrowBitmap.getHeight() / 2f);
        mMatrix.postRotate(mHSIBearing2);
        mMatrix.postTranslate(imageCenterX, imageCenterY);
        localCanvas.drawBitmap(mScaledNumberTwoArrowBitmap, mMatrix, null);
        //Bearing marker 1 & 2 etched
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

    private void ScaleAirplaneSymbol(float finalWidth) {
        mScaledAirplaneSymbolBitmap = Bitmap.createScaledBitmap(mAirplaneSymbolBitmap, (int) Math.round(finalWidth / 1.1), (int) Math.round(finalWidth / 1.1), true);
    }

    private void ScaleCompassCard(Bitmap bitmap) {
        int finalWidth = (int) Math.round(bitmap.getWidth() * 0.55);
        mScaledCompassCardBitmap = Bitmap.createScaledBitmap(mHSICompassCardBitmap, (int) finalWidth, (int) finalWidth, true);
    }

    private void ScaleUsedCourseArrow(Bitmap compassCardBitmap, Bitmap courseArrowToUseBitmap) {
        int finalWidth = (int) Math.round(compassCardBitmap.getWidth() * 0.98);
        mScaledCourseNeedleBitmap = Bitmap.createScaledBitmap(courseArrowToUseBitmap, (int) finalWidth, (int) finalWidth, true);
    }

    private void ScaleHeadingBug(Bitmap compassCardBitmap) {
        int finalWidth = (int) Math.round(compassCardBitmap.getWidth() * 1.05);
        mScaledHeadingBugBitmap = Bitmap.createScaledBitmap(mHeadingBugBitmap, (int) 70, (int) finalWidth, true);
    }

    private Bitmap ScaleBearingNumberLine(Bitmap bitmapToScale, Bitmap compassCardBitmap) {
        int finalWidth = (int) Math.round(compassCardBitmap.getWidth() * 1.2);
        return Bitmap.createScaledBitmap(bitmapToScale, (int) finalWidth, (int) finalWidth, true);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setData(String pData) {

        String[] messageArray = pData.split(";");
        mHSICourseNeedle = 360 * Float.parseFloat(messageArray[0]);
        mHSIPowerIsOff = Float.parseFloat(messageArray[1]) > 0;
        mHSIRangeFlagIsOn = Float.parseFloat(messageArray[2]) > 0;
        mHSIOffCourseWarningFlagIsOn = Float.parseFloat(messageArray[3]) > 0;
        mHSIHeading = 360 * Float.parseFloat(messageArray[4]);
        mHSIBearing1 = 360 * Float.parseFloat(messageArray[5]) - 360;
        mHSIBearing2 = 360 * Float.parseFloat(messageArray[6]) - 360;
        mHSIHeadingBug = 360 * Float.parseFloat(messageArray[7]);
        //mHSICourseCounterA
        //mHSICourseCounterB
        //mHSIRangeDigitA = Math.round(10 * Float.parseFloat(messageArray[10]));
        mHSIRangeDigitB = Math.round(10 * Float.parseFloat(messageArray[11]));
        mHSIRangeDigitC = Math.round(10 * Float.parseFloat(messageArray[12]));
        mHSIRangeDigitD = Math.round(10 * Float.parseFloat(messageArray[13]));
        mHSICourseDeviation = Math.round(10 * Float.parseFloat(messageArray[14]));
        //Log.d("mHSIBearing1", String.valueOf(mHSIBearing2));
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
/*
    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
*/

}