package fr.astazou.cockpitplusplus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by astazou on 18/09/2016.
 *
 */
public class UDPSender {

    //Looger
    private String LOGGER = this.getClass().getSimpleName();

    private static UDPSender mInstance;

    private String mHeadKey = "Cockpit++";

    private boolean mMessageToSend = false;

    private DatagramPacket mDatagramPacket;


    private UDPSender() {

    }

    public static UDPSender getInstance() {
        if(mInstance == null) {
            mInstance = new UDPSender();
        }
        return mInstance;
    }


    public void sendToDCS(int pTybeBtn, int pDevice, int pCode, String pValue, Context pContext) {
        //Get connection info from sharedPref
        Log.d(LOGGER, "sending: " + pTybeBtn + " | "  + pDevice + " | " + pCode + " | " + pValue);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(pContext);
        final String hostAddress = (sharedPref.getString(pContext.getString(R.string.PREFERENCES_IPADDRESS),pContext.getString(R.string.PREFERENCES_IPADDRESS_default_value)).equals("")|| sharedPref.getString(pContext.getString(R.string.PREFERENCES_IPADDRESS),pContext.getString(R.string.PREFERENCES_IPADDRESS_default_value)).isEmpty())?pContext.getString(R.string.PREFERENCES_IPADDRESS_default_value):sharedPref.getString(pContext.getString(R.string.PREFERENCES_IPADDRESS),pContext.getString(R.string.PREFERENCES_IPADDRESS_default_value));
        final String hostPort = (sharedPref.getString(pContext.getString(R.string.PREFERENCES_DCSPORT),pContext.getString(R.string.PREFERENCES_DCSPORT_default_value)).equals("")|| sharedPref.getString(pContext.getString(R.string.PREFERENCES_DCSPORT),pContext.getString(R.string.PREFERENCES_DCSPORT_default_value)).isEmpty())?pContext.getString(R.string.PREFERENCES_DCSPORT_default_value):sharedPref.getString(pContext.getString(R.string.PREFERENCES_DCSPORT),pContext.getString(R.string.PREFERENCES_DCSPORT_default_value));
        final String data = ","  + pTybeBtn + ","  + pDevice + "," + pCode + "," + pValue;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //Preparing the socket
                    InetAddress serverAddr = InetAddress.getByName(hostAddress);

                    //Preparing the packet
                    byte[] buf = (mHeadKey + data).getBytes();
                    mDatagramPacket = new DatagramPacket(buf, buf.length, serverAddr, Integer.valueOf(hostPort));
                    mMessageToSend = true;

                } catch (UnknownHostException e) {
                    Log.e(LOGGER, e.getLocalizedMessage(), e);
                }
            }
        });

        thread.start();
    }

    public DatagramPacket messageToSend() {
        if(mMessageToSend) {
            mMessageToSend = false;
            return mDatagramPacket;
        } else {
            return null;
        }
    }

}
