package fr.astazou.cockpitplusplus.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import fr.astazou.cockpitplusplus.utils.MiG21Bis_Commands;
import fr.astazou.cockpitplusplus.utils.UDPSender;

import static android.view.Gravity.CENTER;


public class MiG21Bis_RadarControl extends Fragment {

    /**mBackgroundView and mConstainer are used to mange the rotation properly according to the image you will have in background**/
    private ImageView mBackgroundView;
    private LinearLayout mContainer;

    public MiG21Bis_RadarControl() {
        // Required empty public constructor
    }


    /**
     * If you want to get data from the class which receives the data from DCS, you need to register your broadcast.
     * Then you will be able to receive data to manage your fragment.
     * @param savedInstanceState default
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.MIG21BIS));
    }

    /**
     * Here you manage the binding with your XML and this fragment class. This is also here you add listeners for your buttons
     * @param inflater default
     * @param container default
     * @param savedInstanceState default
     * @return a View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mig21bis_radarcontrol, container, false);
        mBackgroundView = (ImageView) view.findViewById(R.id.layout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        //TODO Bind the buttons/views here and set actions of the buttons here to send actions to DCS
        return view;
    }

    /**
     * Every fragment has his own BroadcastReceiver, you just have to create/use the key of the module you are working on.
     */
    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.MIG21BIS)) {
                String armamentPanel = intent.getExtras().getString(BroadcastKeys.MIG21BIS);
                if(armamentPanel != null && !armamentPanel.isEmpty()){
                    String[] panel_data = armamentPanel.split(";");
                    //TODO animate the view with the data we get
                }
            }

        }
    };


    /**
     * Mostly used for simple button
     * @param pCommand ID if the button in DCS, 3091 for example
     */
    private void sendCommand(MiG21Bis_Commands pCommand) {
        sendCommand(pCommand, "1");
    }

    /**
     * Used for button which can have more than 2 positions, or rotary buttons (knobs)
     * @param pCommand ID if the button in DCS, 3091 for example
     * @param pValue Depends of your panel, can be -1, 0, 1, 0.25, ...
     */
    private void sendCommand(MiG21Bis_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(),pCommand.getDevice().getCode(),pCommand.getCode(), pValue,getContext());
    }

    /**
     * Call resizeView to draw correctly the buttons/images over the background when arriving on the fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        resizeView();
    }

    /**
     * Used to detect rotation of the phone to make the buttons/images zesized according to the background image
     * @param newConfig default
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resizeView();
    }

    /**
     * ResizeView is made to draw correctly the buttons/images over the background.
     */
    private void resizeView() {
        mBackgroundView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mBackgroundView.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        } else {
                            mBackgroundView.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        }
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mBackgroundView.getLayoutParams().width, mBackgroundView.getLayoutParams().height);
                        layoutParams.gravity = CENTER;
                        mContainer.setLayoutParams(layoutParams);
                        mContainer.getLayoutParams().height = mBackgroundView.getHeight();
                        mContainer.getLayoutParams().width = mBackgroundView.getWidth();
                    }
                });
    }

    /**
     * If you don't unregister you can have weird behaviour coming back on this fragment
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadCastNewMessage);
    }
}
