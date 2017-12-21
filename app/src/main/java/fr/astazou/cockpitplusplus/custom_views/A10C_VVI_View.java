package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import fr.astazou.cockpitplusplus.R;

import static fr.astazou.cockpitplusplus.services.Konector.TAG;

/**
 * Created by JerkerD on 18.12.2017.
 */

public class A10C_VVI_View extends View {


    private final static int POINT_RADIUS = 8;
    private final static int CROSS_LENGTH = 42;
    private final static int CROSS_WIDTH = 4;

    private String mData;


    /*
            private Bitmap DrawSequenceVVI()
        {
            var finalVVIImage = new Bitmap(_vviBackgroundImage.Width, _vviBackgroundImage.Height);
            try
            {
                var angle = Convert.ToInt32((_a10CVviDataHolderClass.VVIValue - 32590.27206) / 191.6014706);
                using (var graphics = Graphics.FromImage(finalVVIImage))
                {
                    graphics.FillRectangle(Brushes.Black, 0, 0, finalVVIImage.Width, finalVVIImage.Height);
                    graphics.DrawImage(_vviBackgroundImage, 0, 0, _vviBackgroundImage.Width, _vviBackgroundImage.Height);
                    graphics.TranslateTransform(_vviBackgroundImage.Width / 2f, _vviBackgroundImage.Height / 2f);
                    graphics.RotateTransform(angle);
                    graphics.TranslateTransform(_vviNeedleImage.Width / -2f, _vviNeedleImage.Height / -2f);
                    var vviNeedleImageRectangle = new Rectangle(0, 0, _vviNeedleImage.Width, _vviNeedleImage.Height);
                    graphics.DrawImage(_vviNeedleImage, vviNeedleImageRectangle, 0, 0, _vviNeedleImage.Width, _vviNeedleImage.Height, GraphicsUnit.Pixel, _blackTransparency);
                }
            }
            catch (Exception ex)
            {
                Common.LogError(59132, ex, "Exception in FIPPanelA10C DrawSequenceVVI().");
            }
            return finalVVIImage;
        }


     */
    public A10C_VVI_View(Context context) {
        super(context);
    }

    public void updateLayoutSize() {
        invalidate();
    }


    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: 1");
        float posX = (float) canvas.getHeight();
        float posY = (float) canvas.getWidth();
        //Show the spot(s)
        //if(mData!=null && !mData.isEmpty()) {
          //  String[] spots = mData.split(";");
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a10c_vvi_needle);
            canvas.rotate(45, posX, posY);
            canvas.drawBitmap(bitmap, posX, posY, null);
            //canvas.restore();
            canvas.save();
        //}
        Log.d(TAG, "onDraw: 2");
    }

    public void setData(String pData) {
        mData = pData;
        invalidate();
    }
}