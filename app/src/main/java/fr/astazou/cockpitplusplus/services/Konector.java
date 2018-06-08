package fr.astazou.cockpitplusplus.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fr.astazou.cockpitplusplus.MyApp;
import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import fr.astazou.cockpitplusplus.utils.UDPSender;

public class Konector extends Service {

    //Looger
    public static final String TAG = "KonectorTAG";

    //Looger
    private String LOGGER = this.getClass().getSimpleName();

    //Close the broadcast when service is stoppeds
    private boolean mServiceLaunched;

    //Small timer to avoid too much data
    private final int mPeriodUpdate = 500;

    private int mStartMode;

    /**
     * Opening listening socket
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        mServiceLaunched = true;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    new AsyncTaskReceiver().execute();
                } catch (Exception e) {
                    Log.e(LOGGER, "Receiver error", e);
                }

            }
        }, mPeriodUpdate);

        return mStartMode;
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        mServiceLaunched = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class AsyncTaskReceiver extends AsyncTask<SharedPreferences.Editor, Void, Void> {

        protected Void doInBackground(SharedPreferences.Editor... editors) {
            try {
                String androidPort = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.PREFERENCES_ANDROIDPORT), getString(R.string.PREFERENCES_ANDROIDPORT_default_value));
                if (androidPort.isEmpty()) {
                    androidPort = getString(R.string.PREFERENCES_ANDROIDPORT_default_value);
                }
                int hostPort = Integer.valueOf(androidPort);
                Log.d(LOGGER, "Opening listening socket on port " + hostPort + "...");
                DatagramSocket socket = new DatagramSocket(hostPort);
                socket.setBroadcast(true);
                socket.setReuseAddress(true);
                while (mServiceLaunched) {
                    //Listening on socket
                    byte[] buf = new byte[2048];

                    DatagramPacket datagramPacket = UDPSender.getInstance().messageToSend();
                    if( datagramPacket != null) {
                        socket.send(datagramPacket);
                        Log.d(LOGGER, "Sent!");
                    } else {
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        socket.receive(packet);
                        String messageReceived = new String(packet.getData()).trim();
                        //Log.d("UDP", "Received: '" + messageReceived + "'");
                        String[] messageArray = messageReceived.split(",");//Split the data received on the Android

                        if (messageArray[0].equals(getString(R.string.HeadKey))) {//Check if it is from Cockpit++.lua
                            if(MyApp.LUA_VERSION.equals(messageArray[1])) {//Check the version of the Cockpit++.lua, to know if the versions are good of if the user needs to update the Application or the Lua
                                sendBroadcast(new Intent().setAction(BroadcastKeys.ONLINE).putExtra(BroadcastKeys.CURRENT_AIRCRAFT,messageArray[2])); //Used to show in the settings if the application is connected

                                //Check what is the module currently is use and broadcast the data for the panels
                                if(messageArray[2].equals(getString(R.string.m2kc)) && messageArray.length > 11) {
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_UR).putExtra(BroadcastKeys.M2KC_UR,messageArray[3]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_BR).putExtra(BroadcastKeys.M2KC_BR,messageArray[4]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_PCABTN).putExtra(BroadcastKeys.M2KC_PCABTN,messageArray[5]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_PPA).putExtra(BroadcastKeys.M2KC_PPA,messageArray[6]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_PPABTN).putExtra(BroadcastKeys.M2KC_PPABTN,messageArray[7]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_INS_UR).putExtra(BroadcastKeys.M2KC_INS_UR,messageArray[8]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_INS_BR).putExtra(BroadcastKeys.M2KC_INS_BR,messageArray[9]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_INS_DATA).putExtra(BroadcastKeys.M2KC_INS_DATA,messageArray[10]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.M2KC_INS_KNOBS).putExtra(BroadcastKeys.M2KC_INS_KNOBS,messageArray[11]));
                                } else if (messageArray[2].equals(getString(R.string.f15c)) && messageArray.length > 4) {
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.F15C_RWR).putExtra(BroadcastKeys.F15C_RWR,messageArray[4]));
                                } else if (messageArray[2].equals(getString(R.string.uh1h)) && messageArray.length > 3) {
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.UH1H_ARMAMENT).putExtra(BroadcastKeys.UH1H_ARMAMENT,messageArray[3]));
                                } else if (messageArray[2].equals(getString(R.string.av8bna)) && messageArray.length > 3) {
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.AV8BNA_NOZZLE).putExtra(BroadcastKeys.AV8BNA_NOZZLE,messageArray[3]));
                                }  else if (messageArray[2].equals(getString(R.string.a10c)) && messageArray.length > 3) {
                                    /*
                                        D/KonectorTAG: A10C MessageArray =
                                        [0] Cockpit++,
                                        [1] 3,
                                        [2] A-10C,
                                        [3] -0.05029670894146,
                                        [4] 0.46585440635681;0;0;0;0.46585440635681;0.73500490188599;0.73500490188599;0.46585440635681;0;0;0;0;0;0.34873324632645;0.84212499856949;0;1

                                    */
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.A10C_VVI).putExtra(BroadcastKeys.A10C_VVI,messageArray[3]));
                                    sendBroadcast(new Intent().setAction(BroadcastKeys.A10C_HSI).putExtra(BroadcastKeys.A10C_HSI,messageArray[4]));
                                    //Log.d(TAG, "A10C MessageArray[4] = " + messageArray[4]);
                                }
                            } else if(Integer.valueOf(MyApp.LUA_VERSION) > Integer.valueOf(messageArray[1])){
                                //Log.w(LOGGER, "Oh oh, the LUA is not up to date");
                                sendBroadcast(new Intent().setAction(BroadcastKeys.UPDATE_LUA));//Launch call to show to message the lua must be updated
                            } else {
                                //Log.w(LOGGER, "Oh oh, the application is not up to date");
                                sendBroadcast(new Intent().setAction(BroadcastKeys.OPEN_PLAYSTORE));//Launch call to open the playstore from the activity
                            }
                        }
                    }
                }
                socket.close();

                return null;
            } catch (Exception e) {
                Log.e("UDP", e.getLocalizedMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(Void feed) {
            //Do nothing
        }
    }
}

