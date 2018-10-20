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
import android.widget.Button;
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

    /**My member variables for the images**/
    private ImageView mButton1middle;
    private ImageView mButton1up;
    private ImageView mButton2middle;
    private ImageView mButton2up;
    private ImageView mButton3up;
    private ImageView mLight1;
    private ImageView mLight2;
    private ImageView mLight3;

    /**My member variables for the buttons**/
    private Button mButton1plus;
    private Button mButton1minus;
    private Button mButton2plus;
    private Button mButton2minus;
    private Button mButton3;

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
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.MIG21BIS_RADARPANEL));
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

        //Bind the buttons/views here and set actions of the buttons here to send actions to DCS ==>
        mLight1 = (ImageView) view.findViewById(R.id.mig21_radarpanel_light1);
        mLight2 = (ImageView) view.findViewById(R.id.mig21_radarpanel_light2);
        mLight3 = (ImageView) view.findViewById(R.id.mig21_radarpanel_light3);
        mButton1middle = (ImageView) view.findViewById(R.id.mig21_radarpanel_button1middle);
        mButton1up = (ImageView) view.findViewById(R.id.mig21_radarpanel_button1up);
        mButton2middle = (ImageView) view.findViewById(R.id.mig21_radarpanel_button2middle);
        mButton2up = (ImageView) view.findViewById(R.id.mig21_radarpanel_button2up);
        mButton3up = (ImageView) view.findViewById(R.id.mig21_radarpanel_button3up);

        mButton1plus = (Button) view.findViewById(R.id.mig21_radarpanel_button1plus);
        mButton1plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton1middle.getVisibility() == View.VISIBLE) {
                    //send command to put button to next step
                    sendCommand(MiG21Bis_Commands.RadarState,"1");
                } else if(mButton1up.getVisibility() == View.VISIBLE) {
                    //Do nothing, already up...
                } else {
                    //No image is visible, it means we are in default position, let's move to the middle position then! Sending command to put button to middle
                    sendCommand(MiG21Bis_Commands.RadarState,"0.5");
                }
            }
        });
        mButton1minus = (Button) view.findViewById(R.id.mig21_radarpanel_button1minus);
        mButton1minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton1middle.getVisibility() == View.VISIBLE) {
                    //send command to put button to lower step
                    sendCommand(MiG21Bis_Commands.RadarState,"0");
                } else if(mButton1up.getVisibility() == View.VISIBLE) {
                    //let's move to the middle position then! Sending command to put button to middle
                    sendCommand(MiG21Bis_Commands.RadarState,"0.5");
                } else {
                    //No image is visible, it means we are in default position, Do nothing, already down...
                }
            }
        });

        mButton2plus = (Button) view.findViewById(R.id.mig21_radarpanel_button2plus);
        mButton2plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton2middle.getVisibility() == View.VISIBLE) {
                    //send command to put button to next step
                    sendCommand(MiG21Bis_Commands.RadarLow,"1");
                } else if(mButton2up.getVisibility() == View.VISIBLE) {
                    //Do nothing, already up...
                } else {
                    //No image is visible, it means we are in default position, let's move to the middle position then! Sending command to put button to middle
                    sendCommand(MiG21Bis_Commands.RadarLow,"0.5");
                }
            }
        });
        mButton2minus = (Button) view.findViewById(R.id.mig21_radarpanel_button2minus);
        mButton2minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton2middle.getVisibility() == View.VISIBLE) {
                    //send command to put button to lower step
                    sendCommand(MiG21Bis_Commands.RadarLow,"0");
                } else if(mButton2up.getVisibility() == View.VISIBLE) {
                    //let's move to the middle position then! Sending command to put button to middle
                    sendCommand(MiG21Bis_Commands.RadarLow,"0.5");
                } else {
                    //No image is visible, it means we are in default position, Do nothing, already down...
                }
            }
        });

        mButton3 = (Button) view.findViewById(R.id.mig21_radarpanel_button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton3up.getVisibility() == View.VISIBLE) {
                    //let's move to the bottom position then! Sending command to put button to bottom
                    sendCommand(MiG21Bis_Commands.RadarBeam,"0");
                } else {
                    //No image is visible, it means we are in default position, let's move to top position :)
                    sendCommand(MiG21Bis_Commands.RadarBeam,"1");
                }
            }
        });

        return view;
    }

    /**
     * Every fragment has his own BroadcastReceiver, you just have to create/use the key of the module you are working on.
     */
    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.MIG21BIS_RADARPANEL)) {
                String armamentPanel = intent.getExtras().getString(BroadcastKeys.MIG21BIS_RADARPANEL);
                if(armamentPanel != null && !armamentPanel.isEmpty()){
                    String[] panel_data = armamentPanel.split(";");
                    mButton1up.setVisibility((panel_data[0].equals("1")?View.VISIBLE:View.INVISIBLE));
                    mButton1middle.setVisibility((panel_data[0].equals("0.5")?View.VISIBLE:View.INVISIBLE));

                    mButton2up.setVisibility((panel_data[1].equals("1")?View.VISIBLE:View.INVISIBLE));
                    mButton2middle.setVisibility((panel_data[1].equals("0.5")?View.VISIBLE:View.INVISIBLE));

                    mButton3up.setVisibility((panel_data[2].equals("1")?View.VISIBLE:View.INVISIBLE));

                    mLight1.setVisibility(panel_data[3].equals("1")?View.VISIBLE:View.INVISIBLE);

                    mLight2.setVisibility(panel_data[4].equals("1")?View.VISIBLE:View.INVISIBLE);

                    mLight3.setVisibility(panel_data[5].equals("1")?View.VISIBLE:View.INVISIBLE);
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
