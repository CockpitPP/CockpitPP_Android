package fr.astazou.cockpitplusplus.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by astazou on 04/09/2016.
 *
 */
public class M2kC_PagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[];

    public M2kC_PagerAdapter(FragmentManager fm, String pTabtitles[]) {
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
                fragment = new M2kC_PCA();
                break;
            case 1:
                fragment = new M2kC_PPA();
                break;
            case 2:
                fragment = new M2kC_INS();
                break;
            case 3:
                fragment = new M2kC_INS_Knob();
                break;
            default:
                fragment = new M2kC_PCA();

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}