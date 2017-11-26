package fr.astazou.cockpitplusplus.activities;

import android.os.Bundle;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by astazou on 29/04/2017.
 */

public class FAQ_Activity extends Module_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_faq);
    }
}
