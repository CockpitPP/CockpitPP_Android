package fr.astazou.cockpitplusplus.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.fragments.F15C_PagerAdapter;

public class F15C_Activity extends Module_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_f15c);

        //Load the fragments in the viewPager
        F15C_PagerAdapter pagerAdapter = new F15C_PagerAdapter(getSupportFragmentManager(),new String[] { getString(R.string.f15c_rwr) + " "});
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
        setCurrentAircraft(getString(R.string.f15c));//Tells to Module_Activity what module the user is using
    }
}