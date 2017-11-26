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
import fr.astazou.cockpitplusplus.utils.M2KC_Commands;
import fr.astazou.cockpitplusplus.utils.UDPSender;

import static android.view.Gravity.CENTER;


public class M2kC_INS_Knob extends Fragment {


    private ImageView mBackgroundView;
    private LinearLayout mContainer;

    private ImageView mRotatorLeft;
    private ImageView mRotatorRight;

    private Button mKnobLeftMinus;
    private Button mKnobLeftPlus;
    private Button mKnobRightMinus;
    private Button mKnobRightPlus;

    private final float OFFSSET_ROTATOR_LEFT = 0;
    private final float OFFSSET_ROTATOR_RIGHT = 5;


    public M2kC_INS_Knob() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_INS_KNOBS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m2kc_ins_knobs, container, false);
        mBackgroundView = (ImageView)view.findViewById(R.id.layout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);

        mRotatorRight = (ImageView) view.findViewById(R.id.ins_knob_right_img);
        mRotatorLeft = (ImageView) view.findViewById(R.id.ins_knob_left_img);

        mKnobRightMinus = (Button) view.findViewById(R.id.ins_knob_right_minus);
        mKnobRightPlus = (Button) view.findViewById(R.id.ins_knob_right_plus);
        mKnobLeftMinus = (Button) view.findViewById(R.id.ins_knob_left_minus);
        mKnobLeftPlus = (Button) view.findViewById(R.id.ins_knob_left_plus);

        mKnobRightMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = - mRotatorRight.getRotation() + 28 + OFFSSET_ROTATOR_RIGHT;
                if(direction>160) {
                    direction = 0;
                }
                sendCommand(M2KC_Commands.InsKnobRight, String.valueOf(getValueRightKnob(direction)));
            }
        });
        mKnobRightPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = - mRotatorRight.getRotation() - 50 + OFFSSET_ROTATOR_RIGHT;
                if(direction<-10) {
                    direction = 140;
                }
                sendCommand(M2KC_Commands.InsKnobRight, String.valueOf(getValueRightKnob(direction)));
            }
        });
        mKnobLeftMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = mRotatorLeft.getRotation() - 32 + OFFSSET_ROTATOR_LEFT;
                if(direction<-10) {
                    direction = 205;
                }
                sendCommand(M2KC_Commands.InsKnobLeft, String.valueOf(getValueLeftKnob(direction)));
            }
        });
        mKnobLeftPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = mRotatorLeft.getRotation() + 32 + OFFSSET_ROTATOR_LEFT;
                if(direction>230) {
                    direction = 0;
                }
                sendCommand(M2KC_Commands.InsKnobLeft, String.valueOf(getValueLeftKnob(direction)));
            }
        });

        return view;
    }



    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.M2KC_INS_KNOBS)) {
                String ins = intent.getExtras().getString(BroadcastKeys.M2KC_INS_KNOBS);
                if(ins != null && !ins.isEmpty()){
                    String[] table_data = ins.split(";");
                    mRotatorLeft.setRotation(getDegreesLeftKnob(Float.valueOf(table_data[0])) - OFFSSET_ROTATOR_LEFT);
                    mRotatorRight.setRotation(getDegreesRightKnob(Float.parseFloat(table_data[1])) - OFFSSET_ROTATOR_RIGHT);
                }
            }

        }
    };

    private float getDegreesLeftKnob(float value) {
        if(value < 0.075) {
            return 0;
        } else if(value < 0.125) {
            return 25;
        } else if(value < 0.225) {
            return 60;
        } else if(value < 0.325) {
            return 100;
        } else if(value < 0.425) {
            return 125;
        } else if(value < 0.525) {
            return 150;
        } else if(value < 0.625) {
            return 180;
        } else {
            return 205;
        }
    }

    private float getDegreesRightKnob(float value) {
        if(value < 0.075) {
            return 0;
        } else if(value < 0.125) {
            return -35;
        } else if(value < 0.225) {
            return -70;
        } else if(value < 0.325) {
            return -105;
        } else {
            return -140;
        }
    }

    private float getValueLeftKnob(float value) {
        if(value < 10) {
            return 0;
        } else if(value < 35) {
            return 0.099998474121094f;
        } else if(value < 70) {
            return 0.19999694824219f;
        } else if(value < 110) {
            return 0.29999542236328f;
        } else if(value < 135) {
            return 0.39999389648438f;
        } else if(value < 160) {
            return 0.49999237060547f;
        } else if(value < 190) {
            return 0.59999084472656f;
        } else {
            return 0.69999694824219f;
        }
    }

    private float getValueRightKnob(float value) {
        if(value < 10) {
            return 0;
        } else if(value < 40) {
            return 0.099998474121094f;
        } else if(value < 75) {
            return 0.19999694824219f;
        } else if(value < 110) {
            return 0.29999542236328f;
        } else {
            return 0.40000152587891f;
        }
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

    private void sendCommand(M2KC_Commands pCommand) {
        sendCommand(pCommand, "1");
    }

    private void sendCommand(M2KC_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(),pCommand.getDevice().getCode(),pCommand.getCode(), pValue,getContext());
    }
}
