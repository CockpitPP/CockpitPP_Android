package fr.astazou.cockpitplusplus.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.fragments.M2kC_PagerAdapter;
import fr.astazou.cockpitplusplus.fragments.M2kC_PagerAdapter_Tablet;

public class M2kC_Activity_Tablet extends Module_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_m2kc_tablet);

        //Load the fragments in the viewPager
        M2kC_PagerAdapter_Tablet pagerAdapter = new M2kC_PagerAdapter_Tablet(getSupportFragmentManager(),new String[] { getString(R.string.m2kc_pca) + " " + getString(R.string.m2kc_ppa) + " " + getString(R.string.m2kc_ins) + " " + getString(R.string.m2kc_ins_knobs) + " "});
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
        setCurrentAircraft(getString(R.string.m2kc));//Tells to Module_Activity what module the user is using
    }
}