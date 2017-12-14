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
public class M2kC_PCA_PPA_INS_INSKNOBS extends Fragment {


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
        View subview = inflater.inflate(R.layout.fragment_m2kc_pca_ppa_ins_insknob, viewGroup);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment ppa = new M2kC_PPA();
        transaction.replace(R.id.ppa, ppa).commit();
        transaction = getChildFragmentManager().beginTransaction();
        Fragment pca = new M2kC_PCA();
        transaction.replace(R.id.pca, pca).commit();
        transaction = getChildFragmentManager().beginTransaction();
        Fragment ins = new M2kC_INS();
        transaction.replace(R.id.ins, ins).commit();
        transaction = getChildFragmentManager().beginTransaction();
        Fragment insknobs = new M2kC_INS_Knob();
        transaction.replace(R.id.insknobs, insknobs).commit();
    }
}
