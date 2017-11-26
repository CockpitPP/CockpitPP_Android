package fr.astazou.cockpitplusplus.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by astazou on 04/09/2016.
 */
public class UH1H_PagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[];

    public UH1H_PagerAdapter(FragmentManager fm, String pTabtitles[]) {
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
                fragment = new UH1H_Armament();
                break;
            default:
                fragment = new UH1H_Armament();

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}