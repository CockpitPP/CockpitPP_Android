package fr.astazou.cockpitplusplus.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import fr.astazou.cockpitplusplus.R;

/**
 *
 */
public class A10C_VVI_HSI extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = new FrameLayout(getActivity());
        populateViewForOrientation(inflater, frameLayout);
        return frameLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Reload current fragment
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_a10_c_vvi_hsi, viewGroup);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment vvi = new A10C_VVI();
        transaction.replace(R.id.a10_vvi_combo, vvi).commit();
        transaction = getChildFragmentManager().beginTransaction();
        Fragment hsi = new A10C_HSI();
        transaction.replace(R.id.a10_hsi_combo, hsi).commit();

    }
}
