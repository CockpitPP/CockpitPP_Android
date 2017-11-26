package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.F15C_RWR_Code;

/**
 * Created by astazou on 06/05/2017.
 */

public class F15C_RWR_View extends View{

    private final static int POINT_RADIUS = 8;
    private final static int CROSS_LENGTH = 42;
    private final static int CROSS_WIDTH = 4;

    private String mData;

    private Paint mGreenFill;
    private Paint mGreenText;
    private Paint mGreenStroke;

    public F15C_RWR_View(Context context) {
        super(context);
    }

    public void updateLayoutSize() {
        invalidate();
    }


    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(mGreenFill == null) {
            mGreenFill = new Paint();
            mGreenFill.setStyle(Paint.Style.FILL);
            mGreenFill.setColor(getResources().getColor(R.color.f15CRWRColor));
            mGreenFill.setAntiAlias(true);
        }
        if(mGreenText == null) {
            mGreenText = new Paint();
            mGreenText.setColor(getResources().getColor(R.color.f15CRWRColor));
            mGreenText.setAntiAlias(true);
            mGreenText.setTypeface(Typeface.DEFAULT_BOLD);
            mGreenText.setTextAlign(Paint.Align.CENTER);
            mGreenText.setTextSize(50);
        }
        if(mGreenStroke == null) {
            mGreenStroke = new Paint();
            mGreenStroke.setStyle(Paint.Style.STROKE);
            mGreenStroke.setColor(getResources().getColor(R.color.f15CRWRColor));
            mGreenStroke.setAntiAlias(true);
            mGreenStroke.setStrokeWidth(8);
        }

        int maxRadius = canvas.getHeight()/2;
        int innerBorder = maxRadius/32;

        //Show the 12 points all around the screen
        for(int i = 0; i<12; i++) {
            canvas.drawCircle((float) Math.cos(Math.PI*i/6)*(maxRadius-innerBorder) + maxRadius, (float) Math.sin(Math.PI*i/6)*(maxRadius-innerBorder) + maxRadius, POINT_RADIUS, mGreenFill);
        }

        //Show the centered cross
        canvas.drawRect(maxRadius-CROSS_LENGTH, maxRadius-CROSS_WIDTH, maxRadius+CROSS_LENGTH, maxRadius+CROSS_WIDTH, mGreenFill);
        canvas.drawRect(maxRadius-CROSS_WIDTH, maxRadius-CROSS_LENGTH, maxRadius+CROSS_WIDTH, maxRadius+CROSS_LENGTH, mGreenFill);

        //Show the spot(s)
        if(mData!=null && !mData.isEmpty()) {
            String[] spots = mData.split(";");

            //Fin the highest priority
            int idPriotity = 999;
            float highestPriotity = 0;
            for(int i = 0; i < spots.length; i++) {
                String[] spotData = spots[i].split(":");
                if(spotData.length>8) {
                    if(Float.parseFloat(spotData[3])>highestPriotity) {
                        highestPriotity = Float.parseFloat(spotData[3]);
                        idPriotity = i;
                    }
                }
            }

            //Show each spot
            for(int i = 0; i < spots.length; i++) {
                spots[i] = spots[i].replace("−", "-");
                String[] spotData = spots[i].split(":");
                if(spotData.length>8) {
                    String name = spotData[0];
                    Float power = Float.parseFloat(spotData[1]);
                    double azimuth = Double.parseDouble(spotData[2]);
                    String type = spotData[4];
                    int level1 = Integer.parseInt(spotData[5]);//means airborne (1), land(2), ship(3)
                    //String level2 = spotData[6]; //probably useless here
                    //String level3 = spotData[7]; //probably useless here
                    //String level4 = spotData[8]; //probably useless here

                    float posX = (float) Math.cos(azimuth-Math.PI/2)*(maxRadius-innerBorder*14)*power + maxRadius;
                    float posY = (float) Math.sin(azimuth-Math.PI/2)*(maxRadius-innerBorder*14)*power + maxRadius;

                    //Show the highest threat/priority
                    if(i == idPriotity) {
                        canvas.save();
                        canvas.rotate(45, posX, posY);
                        canvas.drawRect(posX-34, posY-34, posX+34, posY+34, mGreenStroke);
                        canvas.restore();
                    }

                    //show semi top circle for the last detected spot (I guess last spot of the table is the last detected)
                    if(i == 1) {
                        canvas.drawLine(posX-50,posY+2,posX-44,posY-26,mGreenStroke);
                        canvas.drawLine(posX-45,posY-25,posX-23,posY-46,mGreenStroke);
                        canvas.drawLine(posX-25,posY-45,posX+2,posY-52,mGreenStroke);
                        canvas.drawLine(posX+25,posY-45,posX-2,posY-52,mGreenStroke);
                        canvas.drawLine(posX+45,posY-25,posX+23,posY-46,mGreenStroke);
                        canvas.drawLine(posX+50,posY+2,posX+44,posY-26,mGreenStroke);
                    }

                    //show the kind of spot
                    switch (level1) {
                        case(1)://airborne
                            canvas.drawLine(posX-30,posY-12,posX,posY-30,mGreenStroke);
                            canvas.drawLine(posX+30,posY-12,posX,posY-30,mGreenStroke);
                            canvas.drawCircle(posX, posY-30, POINT_RADIUS/2, mGreenFill);
                            break;
                        case(2)://land

                            break;
                        case(3)://ship

                            break;
                        default:

                            break;
                    }

                    //Show the name
                    canvas.drawText(F15C_RWR_Code.getCode(name), posX, posY + 20, mGreenText);

                    //Show the type of spot
                    switch (type) {
                        case ("scan"):
                            break;
                        case ("lock"):
                            //show circle
                            canvas.drawCircle(posX,posY,50, mGreenStroke);
                            break;
                        case ("missile_radio_guided"):
                            break;
                        case ("track_while_scan"):
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void setData(String pData) {
        mData = pData;
        invalidate();
    }
}
