package fr.astazou.cockpitplusplus.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by archi on 24/09/2017.
 * MotherActivity over every modules activities to do some some actions after some checks (versions and correct plane)
 */
public class Module_Activity extends FragmentActivity {

    //Looger
    private String LOGGER = this.getClass().getSimpleName();

    //Current_aircraft selected by the user
    private String current_aircraft;

    //Check if we already left the activity to avoid to show several time the same Toast
    private boolean alreadyExited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Used ot keep the screen on if the user doesn't touch the screen for a while
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        registerReceiver(mBroadCastNewMessage, new IntentFilter(BroadcastKeys.OPEN_PLAYSTORE));
        registerReceiver(mBroadCastNewMessage, new IntentFilter(BroadcastKeys.UPDATE_LUA));
        registerReceiver(mBroadCastNewMessage, new IntentFilter(BroadcastKeys.ONLINE));
    }

    /**
     * Inject Calligraphy into Context Activity
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Unregister the broadcast received leaving the activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadCastNewMessage);
    }

    /**
     * If the user installed the new lua but didn't update the application, we redirect him to the store
     */
    public void openStore() {
        Toast.makeText(Module_Activity.this, R.string.wrong_app_version,Toast.LENGTH_LONG).show();
        alreadyExited = true;
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        //Launch the playStore to update the app
        try {
            final Uri marketUri = Uri.parse("market://details?id=" + appPackageName);
            startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
            finish();
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            finish();
        }
    }

    /**
     * If the user installed the new application but didn't update the Cockpit.lua, we tell him to install the new LUA
     */
    public void updateLUA() {
        Log.w(LOGGER, "openStore");
        alreadyExited = true;
        Toast.makeText(getApplicationContext(),R.string.wrong_lua_version,Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * Leave the activity if it's the wrong module
     */
    public void wrongModule(String pWrongModule) {
        Log.w(LOGGER, "wrongModule");
        alreadyExited = true;

        switch (pWrongModule) {
            case "F-15C":
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2bis) ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Module_Activity.this, F15C_Activity.class));
                break;
            case "M-2000C":
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2bis) ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Module_Activity.this, M2kC_Activity.class));
                break;
            case "UH-1H":
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2bis) ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Module_Activity.this, UH1H_Activity.class));
                break;
            case "AV8BNA":
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2bis) ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Module_Activity.this, AV8BNA_Activity.class));
                break;
            case "A-10C":
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2bis) ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Module_Activity.this, A10C_Activity.class));
                break;
            case "MiG-21Bis":
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2bis) ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Module_Activity.this, MiG21Bis_Activity.class));
                break;
            default:
                Toast.makeText(this, getString(R.string.wrong_module_1) + " " + pWrongModule + getString(R.string.wrong_module_2) ,Toast.LENGTH_LONG).show();
                break;
        }
        finish();
    }

    /**
     * Aircraft(Module) selected by the user
     * @param pCurrentAircraft module's name received from DCS
     */
    protected void setCurrentAircraft(String pCurrentAircraft) {
        current_aircraft = pCurrentAircraft;
    }

    /**
     * Broadcast receiver to apply actions after getting messages from DCS
     */
    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.OPEN_PLAYSTORE)) {
                if(!alreadyExited) {
                    openStore();
                }
            } else if(intent.getAction().contains(BroadcastKeys.UPDATE_LUA)) {
                if(!alreadyExited) {
                    updateLUA();
                }
            } else if(intent.getAction().contains(BroadcastKeys.ONLINE)) {
                if(!alreadyExited) {
                    String currentAircraft = intent.getExtras().getString(BroadcastKeys.CURRENT_AIRCRAFT);
                    if(current_aircraft != null && currentAircraft != null && !currentAircraft.contains(current_aircraft)){
                        if(!alreadyExited) {
                            wrongModule(currentAircraft);
                        }
                    }
                }
            }
        }
    };

}
