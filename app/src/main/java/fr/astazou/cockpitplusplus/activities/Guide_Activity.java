package fr.astazou.cockpitplusplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.astazou.cockpitplusplus.R;

public class Guide_Activity extends Module_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_guide);

        TextView noWorking = (TextView) findViewById(R.id.noWorking);
        noWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guide_Activity.this, Not_Working_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
