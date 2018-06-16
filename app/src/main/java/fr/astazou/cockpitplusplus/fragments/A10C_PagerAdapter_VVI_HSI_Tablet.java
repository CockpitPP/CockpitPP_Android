package fr.astazou.cockpitplusplus.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by JerkerD on 04/09/2016.
 *
 */
public class A10C_PagerAdapter_VVI_HSI_Tablet extends FragmentPagerAdapter {

    private String tabtitles[];

    public A10C_PagerAdapter_VVI_HSI_Tablet(FragmentManager fm, String pTabtitles[]) {
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
                fragment = new A10C_VVI_HSI_COMBO();
                break;
            case 1:
                fragment = new A10C_EMI_LEFT();
                break;
            case 2:
                fragment = new A10C_EMI_RIGHT();
                break;
            default:
                fragment = new A10C_VVI_HSI_COMBO();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}