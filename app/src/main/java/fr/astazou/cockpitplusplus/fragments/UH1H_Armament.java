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
import android.util.Log;
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
import fr.astazou.cockpitplusplus.utils.UDPSender;
import fr.astazou.cockpitplusplus.utils.UH1H_Commands;

import static android.view.Gravity.CENTER;


public class UH1H_Armament extends Fragment {

    private ImageView mBackgroundView;
    private LinearLayout mContainer;

    private Button mCalSelector;
    private ImageView mCalSelectorMiddle;
    private ImageView mCalSelectorUp;

    private ImageView mKnobRktNumber;
    private final float OFFSSET_ROTATOR = 3;
    private Button mKnobRktNumberMinus;
    private Button mKnobRktNumberPlus;

    private ImageView mRktReset;
    private Button mRktResetBtn;

    private Button mJettiPlus;
    private Button mJettiMinus;
    private ImageView mJettiMiddle;
    private ImageView mJettiUp;

    private Button mSelector;
    private ImageView mSelectorAll;
    private ImageView mSelectorRight;

    private ImageView mGreenLight;
    private ImageView mRedLight;

    private Button mSelectorSafety;
    private ImageView mSelectorSafe;
    private ImageView mSelectorArmed;

    public UH1H_Armament() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.UH1H_ARMAMENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uh1h_armament, container, false);
        mBackgroundView = (ImageView) view.findViewById(R.id.layout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);

        mCalSelector = (Button) view.findViewById(R.id.armament_cal_selector);
        mCalSelectorMiddle = (ImageView) view.findViewById(R.id.armament_cal_selector_middle);
        mCalSelectorUp = (ImageView) view.findViewById(R.id.armament_cal_selector_up);
        mCalSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCalSelectorMiddle.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchCalib,"-1");
                    //sendCommand(UH1H_Commands.SwitchCalib,"1"); for now the 40 position doesn't work in DCS  :/, new coming weapons ? :D
                } else if(mCalSelectorUp.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchCalib,"-1");
                } else {
                    sendCommand(UH1H_Commands.SwitchCalib,"0");
                }
            }
        });

        mKnobRktNumber = (ImageView) view.findViewById(R.id.armament_rkt_number);
        mKnobRktNumberMinus = (Button) view.findViewById(R.id.armament_rkt_number_minus);
        mKnobRktNumberPlus = (Button) view.findViewById(R.id.armament_rkt_number_plus);
        mKnobRktNumberMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = mKnobRktNumber.getRotation() - 36f + OFFSSET_ROTATOR;
                if(direction<0) {
                    direction = 252;
                }
                sendCommand(UH1H_Commands.KnobRkt, String.valueOf(getValueKnob(direction)));
            }
        });
        mKnobRktNumberPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = mKnobRktNumber.getRotation() + 29.4f + OFFSSET_ROTATOR;
                if(direction>252) {
                    direction = 0;
                }
                sendCommand(UH1H_Commands.KnobRkt, String.valueOf(getValueKnob(direction)));
            }
        });

        mRktReset = (ImageView) view.findViewById(R.id.armament_rkt_reset);
        mRktResetBtn = (Button) view.findViewById(R.id.armament_rkt_reset_btn);
        mRktResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(UH1H_Commands.ResetRkt);
                mRktReset.setVisibility(View.VISIBLE);
            }
        });

        mJettiPlus = (Button) view.findViewById(R.id.armament_jettison_plus);
        mJettiMinus = (Button) view.findViewById(R.id.armament_jettison_minus);
        mJettiMiddle = (ImageView) view.findViewById(R.id.armament_jettison_middle);
        mJettiUp = (ImageView) view.findViewById(R.id.armament_jettison_up);
        mJettiPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mJettiMiddle.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchJettison);
                    mJettiUp.setVisibility(View.VISIBLE);
                } else if(mJettiUp.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchJettison);
                } else {
                    sendCommand(UH1H_Commands.JettisonCover,"1");
                }
            }
        });
        mJettiMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mJettiMiddle.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.JettisonCover,"0");
                } else if(mJettiUp.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.JettisonCover,"0");
                } else {
                    //Do nothing
                }
            }
        });

        mSelector = (Button) view.findViewById(R.id.armament_selector);
        mSelectorAll = (ImageView) view.findViewById(R.id.armament_selector_all);
        mSelectorRight = (ImageView) view.findViewById(R.id.armament_selector_right);
        mSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectorAll.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchGun,"1");
                } else if(mSelectorRight.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchGun,"-1");
                } else {
                    sendCommand(UH1H_Commands.SwitchGun,"0");
                }
            }
        });

        mSelectorSafety = (Button) view.findViewById(R.id.armament_selector_safety);
        mSelectorSafe = (ImageView) view.findViewById(R.id.armament_selector_safe);
        mSelectorArmed = (ImageView) view.findViewById(R.id.armament_selector_armed);
        mGreenLight = (ImageView) view.findViewById(R.id.green_light);
        mRedLight = (ImageView) view.findViewById(R.id.red_light);
        mSelectorSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectorSafe.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchArmament,"1");
                } else if(mSelectorArmed.getVisibility() == View.VISIBLE) {
                    sendCommand(UH1H_Commands.SwitchArmament,"-1");
                } else {
                    sendCommand(UH1H_Commands.SwitchArmament,"0");
                }
            }
        });

        return view;
    }

    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.UH1H_ARMAMENT)) {
                String armamentPanel = intent.getExtras().getString(BroadcastKeys.UH1H_ARMAMENT);
                if(armamentPanel != null && !armamentPanel.isEmpty()){
                    String[] panel_data = armamentPanel.split(";");

                    mSelectorSafe.setVisibility((panel_data[0].equals("0")?View.VISIBLE:View.INVISIBLE));
                    mSelectorArmed.setVisibility((panel_data[0].equals("1")?View.VISIBLE:View.INVISIBLE));
                    mGreenLight.setVisibility((panel_data[0].equals("0")?View.VISIBLE:View.INVISIBLE));
                    mRedLight.setVisibility((panel_data[0].equals("1")?View.VISIBLE:View.INVISIBLE));

                    mSelectorAll.setVisibility((panel_data[1].equals("0")?View.VISIBLE:View.INVISIBLE));
                    mSelectorRight.setVisibility((panel_data[1].equals("1")?View.VISIBLE:View.INVISIBLE));

                    mCalSelectorMiddle.setVisibility((panel_data[2].equals("0")?View.VISIBLE:View.INVISIBLE));
                    mCalSelectorUp.setVisibility((panel_data[2].equals("1")?View.VISIBLE:View.INVISIBLE));

                    mKnobRktNumber.setRotation(getDegreesKnob(Float.parseFloat(panel_data[3])) - OFFSSET_ROTATOR);

                    mRktReset.setVisibility((panel_data[4].equals("1")?View.VISIBLE:View.INVISIBLE));

                    mJettiUp.setVisibility((panel_data[6].equals("1")?View.VISIBLE:View.INVISIBLE));
                    mJettiMiddle.setVisibility((panel_data[5].equals("1")?View.VISIBLE:View.INVISIBLE));
                }
            }

        }
    };

    private float getDegreesKnob(float value) {
        //Log.v(getTag(),"value: " + value);
        if(value < 0.075) {
            return 0;
        } else if(value < 0.125) {
            return 36;
        } else if(value < 0.225) {
            return 72;
        } else if(value < 0.325) {
            return 108;
        } else if(value < 0.425) {
            return 144;
        } else if(value < 0.525) {
            return 180;
        } else if(value < 0.625) {
            return 216;
        } else {
            return 252;
        }
    }




    private float getValueKnob(float value) {
        Log.v(getTag(),"value: " + value);
        if(value < 10) {
            return 0;
        } else if(value < 40) {
            return 0.099998474121094f;
        } else if(value < 76) {
            return 0.19999694824219f;
        } else if(value < 110) {
            return 0.29999542236328f;
        } else if(value < 146) {
            return 0.39999389648438f;
        } else if(value < 185) {
            return 0.49999237060547f;
        } else if(value < 220) {
            return 0.59999084472656f;
        } else {
            return 0.69998931884766f;
        }
    }




    private void sendCommand(UH1H_Commands pCommand) {
        sendCommand(pCommand, "1");
    }

    private void sendCommand(UH1H_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(),pCommand.getDevice().getCode(),pCommand.getCode(), pValue,getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        resizeView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resizeView();
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadCastNewMessage);
    }
}
