package fr.astazou.cockpitplusplus.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.fragments.A10C_PagerAdapter;
import fr.astazou.cockpitplusplus.fragments.A10C_PagerAdapter_VVI_HSI_Tablet;

public class A10C_Activity_Tablet extends Module_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_a10c);

        //Load the fragments in the viewPager
        A10C_PagerAdapter_VVI_HSI_Tablet pagerAdapter = new A10C_PagerAdapter_VVI_HSI_Tablet(getSupportFragmentManager(),new String[] { getString(R.string.a10c_flight_gauges), getString(R.string.a10c_emi_left), getString(R.string.a10c_emi_right)});
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        //Add the font in the titles of each panel
        Typeface fontTypeFace= Typeface.createFromAsset(getAssets(),"fonts/hemi_head_bd_it.ttf");
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        for (int i = 0; i < pagerTabStrip.getChildCount(); ++i) {
            View nextChild = pagerTabStrip.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(fontTypeFace);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentAircraft(getString(R.string.a10c));//Tells to Module_Activity what module the user is using
    }
}