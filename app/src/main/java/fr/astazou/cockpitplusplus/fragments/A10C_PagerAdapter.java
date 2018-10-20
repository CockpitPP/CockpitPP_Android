package fr.astazou.cockpitplusplus.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

/**
 * Created by jdahlblom on 04/09/2016.
 */
public class A10C_PagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[];

    public A10C_PagerAdapter(FragmentManager fm, String pTabtitles[]) {
        super(fm);
        tabtitles = pTabtitles;
    }

    @Override
    public int getCount() {
        return tabtitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new A10C_VVI();
                break;
            case 1:
                fragment = new A10C_HSI();
                break;
            case 2:
                fragment = new A10C_EMI_LEFT();
                break;
            case 3:
                fragment = new A10C_EMI_RIGHT();
                break;
            default:
                fragment = new A10C_HSI();
        }
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}