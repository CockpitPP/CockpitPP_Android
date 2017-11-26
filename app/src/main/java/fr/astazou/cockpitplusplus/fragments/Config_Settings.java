package fr.astazou.cockpitplusplus.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.preference.CheckBoxPreference;
import android.text.format.Formatter;
import android.widget.Toast;

import com.takisoft.fix.support.v7.preference.EditTextPreference;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;

import static android.content.Context.WIFI_SERVICE;

public class Config_Settings extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private CheckBoxPreference mCheckBoxPreference;
    private EditTextPreference mIpAddress;
    private EditTextPreference mDcsPort;
    private EditTextPreference mAndroidPort;

    private BroadcastReceiver mBroadCastNewMessage;

    private Handler mHandler;
    private Runnable mRunnable;

    public Config_Settings() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Manage the checkBox to see if the app is connected or not to DCS
        mCheckBoxPreference = (CheckBoxPreference) findPreference(getString(R.string.PREFERENCES_CONNECTED));
        mBroadCastNewMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().contains(BroadcastKeys.ONLINE)) {
                    connectionState(true);
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.postDelayed(mRunnable, 2000);
                }
            }
        };
        mHandler = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                connectionState(false);
            }
        };
        getActivity().registerReceiver(mBroadCastNewMessage, new IntentFilter(BroadcastKeys.ONLINE));
        connectionState(false);

        //Initiate the preferences and manage if user save an empty value
        mIpAddress = (EditTextPreference) findPreference(getString(R.string.PREFERENCES_IPADDRESS));
        mDcsPort = (EditTextPreference) findPreference(getString(R.string.PREFERENCES_DCSPORT));
        mAndroidPort = (EditTextPreference) findPreference(getString(R.string.PREFERENCES_ANDROIDPORT));


        //Show the IP on the Android
        EditTextPreference myAndroidIP = (EditTextPreference) findPreference(getString(R.string.PREFERENCES_ANDROID_IPADDRESS));
        WifiManager wifiMgr = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        if(ipAddress.contains(getString(R.string.noIP))) {
            Toast.makeText(getActivity(), getString(R.string.needWifi), Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            myAndroidIP.setTitle(getString(R.string.ipAddressResult) + ipAddress);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 2000);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        refreshSummaries();
    }

    /**
     * Changa the value of the connection
     * @param pState value
     */
    public void connectionState(boolean pState) {
        if(pState) {
            mCheckBoxPreference.setChecked(true);
            mCheckBoxPreference.setTitle(getString(R.string.connected));
        } else {
            mCheckBoxPreference.setChecked(false);
            mCheckBoxPreference.setTitle(R.string.notConnected);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadCastNewMessage);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        refreshSummaries();
    }

    /**
     * Preview of what the user typed when he entered values
     */
    public void refreshSummaries() {
        mIpAddress.setSummary((mIpAddress.getText() == null || mIpAddress.getText().isEmpty())?getString(R.string.exIpAddress):mIpAddress.getText());
        mDcsPort.setSummary((mDcsPort.getText() == null || mDcsPort.getText().isEmpty())?getString(R.string.exDCSPort):mDcsPort.getText());
        mAndroidPort.setSummary((mAndroidPort.getText() == null || mAndroidPort.getText().isEmpty())?getString(R.string.exAndroidPort):mAndroidPort.getText());
    }
}
