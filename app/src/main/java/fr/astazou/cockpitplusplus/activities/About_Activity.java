package fr.astazou.cockpitplusplus.activities;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by astazou on 29/04/2017.
 * About screen
 */
public class About_Activity extends Module_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_about);

        //Get the version of the app
        String versionName = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("",e.getMessage(),e);
        }

        //Display the current version of the app
        TextView textViewVersionInfo = (TextView) findViewById(R.id.version);
        String versionDisplayed = getString(R.string.version) + " " + versionName + " " + getString(R.string.beta);
        textViewVersionInfo.setText(versionDisplayed);

        //Fill the changelog in the dialog if the user click on the changelog's button
        final String version = versionName;
        final String changelog = getString(R.string.changelog_3) + "\n" + getString(R.string.changelog_2) + "\n" + getString(R.string.changelog_1);
        Button changelogButton = (Button) findViewById(R.id.changelog);
        changelogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(About_Activity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(About_Activity.this);
                }
                builder.setTitle(getString(R.string.changelog) + " " + version)
                        .setMessage(changelog)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Close the popup
                            }
                        })
                        .setIcon(R.mipmap.mirage_icon)
                        .show();
            }
        });
    }
}
