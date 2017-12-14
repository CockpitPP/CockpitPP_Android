package fr.astazou.cockpitplusplus.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by astazou on 04/09/2016.
 *
 */
public class M2kC_PagerAdapter_Tablet extends FragmentPagerAdapter {

    private String tabtitles[];

    public M2kC_PagerAdapter_Tablet(FragmentManager fm, String pTabtitles[]) {
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
                fragment = new M2kC_PCA_PPA_INS_INSKNOBS();
                break;
            default:
                fragment = new M2kC_PCA_PPA_INS_INSKNOBS();

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}