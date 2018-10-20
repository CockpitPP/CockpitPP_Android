package fr.astazou.cockpitplusplus.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.services.Konector;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Menu_Activity extends Activity {

    //Looger
    private String LOGGER = this.getClass().getSimpleName();

    //To know if the user pressed the button in the last 2 seconds
    private boolean mBackButtonAlreadyPressed;

    //Boolean to know if service to communicate with DCS is started
    private boolean mServiceKonectorStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind the view
        setContentView(R.layout.activity_menu);

        //Bind the buttons from the screen
        Button a10c = (Button) findViewById(R.id.a10c);
        Button ajs37 = (Button) findViewById(R.id.ajs37);
        Button av8bna = (Button) findViewById(R.id.av8bna);
        Button c101 = (Button) findViewById(R.id.c101);
        Button fa18c = (Button) findViewById(R.id.fa18c);
        Button f15c = (Button) findViewById(R.id.f15c);
        Button f5e = (Button) findViewById(R.id.f5e);
        Button huey = (Button) findViewById(R.id.huey);
        Button l39 = (Button) findViewById(R.id.l39);
        Button mi8 = (Button) findViewById(R.id.mi8);
        Button mig21 = (Button) findViewById(R.id.mig21);
        Button mig29 = (Button) findViewById(R.id.mig29);
        Button mirage = (Button) findViewById(R.id.mirage);
        Button sa342 = (Button) findViewById(R.id.sa342);
        Button su27 = (Button) findViewById(R.id.su27);
        Button su33 = (Button) findViewById(R.id.su33);
        Button su25 = (Button) findViewById(R.id.su25);
        Button guide = (Button) findViewById(R.id.guide);
        Button notworking = (Button) findViewById(R.id.not_working);
        Button config = (Button) findViewById(R.id.config);
        Button about = (Button) findViewById(R.id.about);
        Button faq = (Button) findViewById(R.id.faq);
        Button exit = (Button) findViewById(R.id.exit);

        //Set the actions on the buttons
        ajs37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        av8bna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, AV8BNA_Activity.class);
                startActivity(intent);
            }
        });
        f5e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        su27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        su25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        mig21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, MiG21Bis_Activity.class);
                startActivity(intent);
            }
        });
        mig29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        mi8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        l39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        c101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        fa18c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        su33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        sa342.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.not_available);
            }
        });
        mirage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(Menu_Activity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.mode_chooser);

                LinearLayout btn_tablet = (LinearLayout) dialog.findViewById(R.id.btn_tablet);
                btn_tablet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(M2kC_Activity_Tablet.class);
                    }
                });

                LinearLayout btn_phone = (LinearLayout) dialog.findViewById(R.id.btn_phone);
                btn_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(M2kC_Activity.class);
                    }
                });

                dialog.show();
            }
        });
        a10c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(Menu_Activity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.mode_chooser);

                LinearLayout btn_tablet = (LinearLayout) dialog.findViewById(R.id.btn_tablet);
                btn_tablet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(A10C_Activity_Tablet.class);
                    }
                });

                LinearLayout btn_phone = (LinearLayout) dialog.findViewById(R.id.btn_phone);
                btn_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(A10C_Activity.class);
                    }
                });

                dialog.show();
            }
        });
        /*a10c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, A10C_Activity.class);
                startActivity(intent);
            }
        });*/
        huey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, UH1H_Activity.class);
                startActivity(intent);
            }
        });
        f15c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, F15C_Activity.class);
                startActivity(intent);
            }
        });
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Config_Activity.class);
                startActivity(intent);
            }
        });
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Guide_Activity.class);
                startActivity(intent);
            }
        });
        notworking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Not_Working_Activity.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, About_Activity.class);
                startActivity(intent);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, FAQ_Activity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Check if the user has to install a new app
        checkNewVersion();
    }

    /**
     * Get the version of the last time the user used the app, if the version augmented after
     * an upgrade we show a popup to tell user to install the new Cockpit.lua
     */
    private void checkNewVersion() {
        //Get the last version used by the user
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float version = prefs.getFloat(getString(R.string.PREFERENCES_ANDROID_APP_VERSION), 1);

        //Get the current version of the app
        String versionName = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOGGER,e.getMessage(),e);
        }

        //If there is a new version since last launch, tell to the user in a popup to install the
        //new Cockpit++.lua
        if(version < Float.parseFloat(versionName)) {
            prefs.edit().putFloat(getString(R.string.PREFERENCES_ANDROID_APP_VERSION),Float.parseFloat(versionName)).apply();
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(Menu_Activity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(Menu_Activity.this);
            }
            builder.setTitle(getString(R.string.app_name))
                    .setMessage(getString(R.string.need_update_lua))
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    })
                    .setNegativeButton(R.string.changelog, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            showChangelog();
                        }
                    })
                    .setIcon(R.mipmap.mirage_icon)
                    .show();
        } else {
            //Nothing to do
        }
    }

    /**
     * After an update, user can see the changelog
     */
    private void showChangelog() {
        //Get the current version
        String versionName = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOGGER,e.getMessage(),e);
        }

        //Fill the Dialog
        final String version = versionName;
        final String changelog = getString(R.string.changelog_5) + "\n" +  getString(R.string.changelog_4) + "\n" +  getString(R.string.changelog_3) + "\n" + getString(R.string.changelog_2) + "\n" + getString(R.string.changelog_1);
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.changelog) + " " + version)
                .setMessage(changelog)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(R.mipmap.mirage_icon)
                .show();
    }

    /**
     * Need to start the service to communicate with DCS
     * @param tag
     */
    private void processStartService(final String tag) {
        Log.v(LOGGER,"processStartService: " + tag);
        mServiceKonectorStarted = true;
        Intent intent = new Intent(getApplicationContext(), Konector.class);
        intent.addCategory(tag);
        startService(intent);
    }

    /**
     * Need to stop the service
     * @param tag id
     */
    private void processStopService(final String tag) {
        Log.v(LOGGER,"processStopService: " + tag);
        mServiceKonectorStarted = false;
        Intent intent = new Intent(getApplicationContext(), Konector.class);
        intent.addCategory(tag);
        stopService(intent);
    }

    /**
     * Inject Calligraphy into Context Activity
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //If we come back from a module activity, we need to stop the service and launch it again (Restart doesn't do the job properly)
        if(mServiceKonectorStarted) {
            processStopService(Konector.TAG);
        }

        //Start the service to get the data from DCS and send actions to the simulator
        processStartService(Konector.TAG);
    }

    /**
     * Override the backButton to be sure the user wants to leave the app, to prevent a wrong action
     */
    @Override
    public void onBackPressed() {
        if (mBackButtonAlreadyPressed) {
            super.onBackPressed();
        } else {
            mBackButtonAlreadyPressed = true;
            Toast.makeText(getApplicationContext(), getString(R.string.quitAgain), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mBackButtonAlreadyPressed = false;
                }
            }, 2000);
        }
    }

    /**
     * Stop the service for broadcasting when leaving the app
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        processStopService(Konector.TAG);
    }

    /**
     * Just show a Toast
     * @param stringId the id of the message to show
     */
    public void showToast(int stringId){
        Toast.makeText(this,stringId,Toast.LENGTH_SHORT).show();
    }


    /**
     * Use to call activity from Dialog
     * @param pActivity
     */
    public void startActivity(Class pActivity) {
        startActivity(new Intent(Menu_Activity.this, pActivity));
    }
}

