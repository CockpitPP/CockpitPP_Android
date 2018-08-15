package fr.astazou.cockpitplusplus.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * reated by astazou on 10/06/2018.
 */
public class MiG21Bis_PagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[];

    public MiG21Bis_PagerAdapter(FragmentManager fm, String pTabtitles[]) {
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
                fragment = new MiG21Bis_RadarControl();
                break;
            default:
                fragment = new MiG21Bis_RadarControl();

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}