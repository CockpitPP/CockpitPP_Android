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
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import fr.astazou.cockpitplusplus.utils.M2KC_Commands;
import fr.astazou.cockpitplusplus.utils.UDPSender;

import static android.view.Gravity.CENTER;


public class M2kC_INS extends Fragment {

    private static String PCN_UR = "PCN_UR";
    private static String text_PCN_MSG = "text_PCN_MSG";//CAL
    private static String text_PCN_NORD = "text_PCN_NORD";//N
    private static String text_PCN_SUD = "text_PCN_SUD";//S
    private static String text_PCN_PLUS_L = "text_PCN_PLUS_L";//+
    private static String text_PCN_MOINS_L = "text_PCN_MOINS_L";//-
    private static String text_PCN_L_NODATA = "text_PCN_L_NODATA";//---.-
    private static String text_PCN_L_LG = "text_PCN_L_LG";//88:88.88
    private static String text_PCN_L_DEG = "text_PCN_L_DEG";//0.0  //6.3  //286.3
    private static String text_PCN_L_INT = "text_PCN_L_INT";//26
    private static String text_PCN_L_TR = "text_PCN_L_TR";//1.31
    private static String text_PCN_L_DR = "text_PCN_L_DR";//17.43
    private static String text_PCN_L_ACLASS = "text_PCN_L_ACLASS";//2
    private static String text_PCN_L_ACTMR = "text_PCN_L_ACTMR";//221
    private static String text_PCN_L_POS_UPDATE_1 = "text_PCN_L_POS_UPDATE_1";//47.237
    private static String text_PCN_L_POS_UPDATE_2 = "text_PCN_L_POS_UPDATE_2";//47.237
    private static String text_PCN_L_MRQ_LAT = "text_PCN_L_MRQ_LAT";//42:18.74
    private static String text_PCN_LDE = "text_PCN_LDE";//..5,1
    private static String text_PCN_EST = "text_PCN_EST";//E
    private static String text_PCN_OUEST = "text_PCN_OUEST";//W
    private static String text_PCN_PLUS_R = "text_PCN_PLUS_R";//+
    private static String text_PCN_MOINS_R = "text_PCN_MOINS_R";//-
    private static String text_PCN_R_NODATA = "text_PCN_R_NODATA";//--.-
    private static String text_PCN_R_LG = "text_PCN_R_LG";//888:88.88  //041:06.43
    private static String text_PCN_R_DEG = "text_PCN_R_DEG";//116.5
    private static String text_PCN_R_INT = "text_PCN_R_INT";//8
    private static String text_PCN_R_TD = "text_PCN_R_TD";//000.05
    private static String text_PCN_R_ASTS = "text_PCN_R_ASTS";//000
    private static String text_PCN_R_POS_UPDATE_1 = "text_PCN_R_POS_UPDATE_1";//130.5
    private static String text_PCN_R_POS_UPDATE_2 = "text_PCN_R_POS_UPDATE_2";//130.5
    private static String text_PCN_R_MRQ_LON = "text_PCN_R_MRQ_LON";//130.5
    private static String text_PCN_RDE = "text_PCN_RDE";//041:27.16

    private static String PCN_BR = "PCN_BR";
    private static String text_PCN_BR1 = "text_PCN_BR1";//00
    private static String text_PCN_eBR1 = "text_PCN_eBR1";//1
    private static String text_PCN_BR2 = "text_PCN_BR2";//01
    private static String text_PCN_eBR2 = "text_PCN_eBR2";//1
    private static String text_PCN_EMode = "text_PCN_EMode";//--:--.--

    private ImageView mBackgroundView;
    private LinearLayout mContainer;

    private TextView mUr1;
    private TextView mUr1Top;
    private TextView mUr1Bottom;
    private TextView mUr2;
    private TextView mUr2Top;
    private TextView mUr2Bottom;
    private TextView mBr1;
    private TextView mBr2;
    private TextView mBrEmode;
    private TextView mLightM91;
    private TextView mLightM92;
    private TextView mLightM93;
    private TextView mLightPRET;
    private TextView mLightALN;
    private TextView mLightMIP;
    private TextView mLightNDEG;
    private TextView mLightSEC;
    private TextView mLightUNI;
    private ImageView mRotator;
    private final float OFFSSET_ROTATOR = 5;
    private Button mRotMinus;
    private Button mRotPlus;
    private Button mRotLightMinus;
    private Button mRotLightPlus;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private Button mBtn8;
    private Button mBtn9;
    private Button mBtn0;
    private Button mBtnEff;
    private Button mBtnIns;
    private Button mBtnPrep;
    private Button mBtnEnc;
    private Button mBtnDest;
    private Button mBtnBad;
    private Button mBtnRec;
    private Button mBtnMrc;
    private Button mBtnVal;
    private ImageView mLightPrep;
    private ImageView mLightEnc;
    private ImageView mLightDest;
    private ImageView mLightBad;
    private ImageView mLightRec;
    private ImageView mLightMrc;
    private ImageView mLightVal;
    private ImageView mLightEff;
    private ImageView mLightIns;
    private float mLightLevel = 0;

    public M2kC_INS() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_INS_BR));
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_INS_UR));
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_INS_DATA));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m2kc_ins, container, false);
        mBackgroundView = (ImageView)view.findViewById(R.id.layout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mUr1 = (TextView) view.findViewById(R.id.ur1);
        mUr1Top = (TextView) view.findViewById(R.id.ur1top);
        mUr1Bottom = (TextView) view.findViewById(R.id.ur1bottom);
        mUr2 = (TextView) view.findViewById(R.id.ur2);
        mUr2Top = (TextView) view.findViewById(R.id.ur2top);
        mUr2Bottom = (TextView) view.findViewById(R.id.ur2bottom);
        mBr1 = (TextView) view.findViewById(R.id.br1);
        mBr2 = (TextView) view.findViewById(R.id.br2);
        mBrEmode = (TextView) view.findViewById(R.id.bremode);
        mLightM91 = (TextView) view.findViewById(R.id.lightM91);
        mLightM92 = (TextView) view.findViewById(R.id.lightM92);
        mLightM93 = (TextView) view.findViewById(R.id.lightM93);
        mLightPRET = (TextView) view.findViewById(R.id.lightPRET);
        mLightALN = (TextView) view.findViewById(R.id.lightALN);
        mLightMIP = (TextView) view.findViewById(R.id.lightMIP);
        mLightNDEG = (TextView) view.findViewById(R.id.lightNDEG);
        mLightSEC = (TextView) view.findViewById(R.id.lightSEC);
        mLightUNI = (TextView) view.findViewById(R.id.lightUNI);
        mRotator = (ImageView) view.findViewById(R.id.ins_rotator);
        mRotMinus = (Button) view.findViewById(R.id.rotMinus);
        mRotPlus =  (Button) view.findViewById(R.id.rotPlus);
        mRotLightMinus =  (Button) view.findViewById(R.id.rotLightMinus);
        mRotLightPlus =  (Button) view.findViewById(R.id.rotLightPlus);
        mBtn1 =  (Button) view.findViewById(R.id.ins_btn1);
        mBtn2 =  (Button) view.findViewById(R.id.ins_btn2);
        mBtn3 =  (Button) view.findViewById(R.id.ins_btn3);
        mBtn4 =  (Button) view.findViewById(R.id.ins_btn4);
        mBtn5 =  (Button) view.findViewById(R.id.ins_btn5);
        mBtn6 =  (Button) view.findViewById(R.id.ins_btn6);
        mBtn7 =  (Button) view.findViewById(R.id.ins_btn7);
        mBtn8 =  (Button) view.findViewById(R.id.ins_btn8);
        mBtn9 =  (Button) view.findViewById(R.id.ins_btn9);
        mBtn0 =  (Button) view.findViewById(R.id.ins_btn0);
        mBtnPrep =  (Button) view.findViewById(R.id.ins_btn_prep);
        mLightPrep =  (ImageView) view.findViewById(R.id.ins_light_prep);
        mBtnEnc =  (Button) view.findViewById(R.id.ins_btn_enc);
        mLightEnc =  (ImageView) view.findViewById(R.id.ins_light_enc);
        mBtnDest =  (Button) view.findViewById(R.id.ins_btn_dest);
        mLightDest =  (ImageView) view.findViewById(R.id.ins_light_dest);
        mBtnBad =  (Button) view.findViewById(R.id.ins_btn_bad);
        mLightBad =  (ImageView) view.findViewById(R.id.ins_light_bad);
        mBtnRec =  (Button) view.findViewById(R.id.ins_btn_rec);
        mLightRec =  (ImageView) view.findViewById(R.id.ins_light_rec);
        mBtnMrc =  (Button) view.findViewById(R.id.ins_btn_mrc);
        mLightMrc =  (ImageView) view.findViewById(R.id.ins_light_mrc);
        mBtnVal =  (Button) view.findViewById(R.id.ins_btn_val);
        mLightVal =  (ImageView) view.findViewById(R.id.ins_light_val);
        mBtnEff =  (Button) view.findViewById(R.id.ins_btneff);
        mLightEff =  (ImageView) view.findViewById(R.id.ins_light_eff);
        mBtnIns =  (Button) view.findViewById(R.id.ins_btnins);
        mLightIns =  (ImageView) view.findViewById(R.id.ins_light_ins);

        mRotMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = mRotator.getRotation() - 24f + OFFSSET_ROTATOR;
                if(direction<0) {
                    direction = 323;
                }
                sendCommand(M2KC_Commands.InsKnob, String.valueOf(getValueKnob(direction)));
            }
        });
        mRotPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float direction = mRotator.getRotation() + 29.4f + OFFSSET_ROTATOR;
                if(direction>340) {
                    direction = 0;
                }
                sendCommand(M2KC_Commands.InsKnob, String.valueOf(getValueKnob(direction)));
            }
        });
        mRotLightMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsKnobLight, String.valueOf(mLightLevel - 0.1f<0?0:mLightLevel - 0.1f));
            }
        });
        mRotLightPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsKnobLight,String.valueOf(mLightLevel + 0.1f>1?1:mLightLevel + 0.1f));
            }
        });
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn1);
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn2);
            }
        });
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn3);
            }
        });
        mBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn4);
            }
        });
        mBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn5);
            }
        });
        mBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn6);
            }
        });
        mBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn7);
            }
        });
        mBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn8);
            }
        });
        mBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn9);
            }
        });
        mBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtn0);
            }
        });
        mBtnEff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnEff);
            }
        });
        mBtnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnIns);
            }
        });
        mBtnPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnPrep);
            }
        });
        mBtnEnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnEnc);
            }
        });
        mBtnDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnDest);
            }
        });
        mBtnBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnBad);
            }
        });
        mBtnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnRec);
            }
        });
        mBtnMrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnMrc);
            }
        });
        mBtnVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.InsBtnVal);
            }
        });
        return view;
    }



    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.M2KC_INS_UR)) {
                String ins = intent.getExtras().getString(BroadcastKeys.M2KC_INS_UR);
                if(ins != null && !ins.isEmpty()){
                    String[] table_ins = ins.split("\n");

                    List<String> ins_data_digit = Arrays.asList(table_ins);

                    //Manage the left message on the INS
                    if(ins_data_digit.contains(text_PCN_MSG)
                            || ins_data_digit.contains(text_PCN_L_NODATA)
                            || ins_data_digit.contains(text_PCN_L_LG)
                            || ins_data_digit.contains(text_PCN_L_DEG)
                            || ins_data_digit.contains(text_PCN_L_INT)
                            || ins_data_digit.contains(text_PCN_LDE)
                            || ins_data_digit.contains(text_PCN_L_TR)
                            || ins_data_digit.contains(text_PCN_L_DR)
                            || ins_data_digit.contains(text_PCN_L_ACTMR)
                            || ins_data_digit.contains(text_PCN_L_ACLASS)
                            || ins_data_digit.contains(text_PCN_L_POS_UPDATE_1)
                            || ins_data_digit.contains(text_PCN_L_POS_UPDATE_2)
                            || ins_data_digit.contains(text_PCN_L_MRQ_LAT)) {
                        if(ins_data_digit.contains(text_PCN_MSG)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_MSG)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_NODATA)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_NODATA)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_LG)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_LG)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_DEG)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_DEG)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_INT)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_INT)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_LDE)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_LDE)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_TR)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_TR)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_DR)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_DR)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_ACTMR)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_ACTMR)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_ACLASS)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_ACLASS)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_ACLASS) && ins_data_digit.contains(text_PCN_L_ACTMR)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_ACLASS)+1] + "    " + table_ins[ins_data_digit.indexOf(text_PCN_L_ACTMR)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_POS_UPDATE_1)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_POS_UPDATE_1)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_POS_UPDATE_2)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_POS_UPDATE_2)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_L_MRQ_LAT)) {
                            mUr1.setText(table_ins[ins_data_digit.indexOf(text_PCN_L_MRQ_LAT)+1]);
                        }
                    } else {
                        mUr1.setText("");
                    }


                    //Manage the right message on the INS
                    if(ins_data_digit.contains(text_PCN_R_NODATA)
                            || ins_data_digit.contains(text_PCN_R_LG)
                            || ins_data_digit.contains(text_PCN_R_DEG)
                            || ins_data_digit.contains(text_PCN_R_INT)
                            || ins_data_digit.contains(text_PCN_R_TD)
                            || ins_data_digit.contains(text_PCN_R_ASTS)
                            || ins_data_digit.contains(text_PCN_RDE)
                            || ins_data_digit.contains(text_PCN_R_POS_UPDATE_1)
                            || ins_data_digit.contains(text_PCN_R_POS_UPDATE_2)
                            || ins_data_digit.contains(text_PCN_R_MRQ_LON)) {
                        if(ins_data_digit.contains(text_PCN_R_NODATA)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_NODATA)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_LG)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_LG)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_DEG)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_DEG)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_INT)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_INT)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_TD)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_TD)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_ASTS)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_ASTS)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_RDE)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_RDE)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_POS_UPDATE_1)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_POS_UPDATE_1)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_POS_UPDATE_2)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_POS_UPDATE_2)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_R_MRQ_LON)) {
                            mUr2.setText(table_ins[ins_data_digit.indexOf(text_PCN_R_MRQ_LON)+1]);
                        }
                    } else {
                        mUr2.setText("");
                    }


                    //Manage the + and N icons on the INS
                    if(ins_data_digit.contains(text_PCN_NORD)
                            || ins_data_digit.contains(text_PCN_PLUS_L)) {
                        if(ins_data_digit.contains(text_PCN_NORD)) {
                            mUr1Top.setText(table_ins[ins_data_digit.indexOf(text_PCN_NORD)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_PLUS_L)) {
                            mUr1Top.setText(table_ins[ins_data_digit.indexOf(text_PCN_PLUS_L)+1]);
                        }
                    } else {
                        mUr1Top.setText("");
                    }


                    //Manage the - and S icons on the INS
                    if(ins_data_digit.contains(text_PCN_SUD)
                            || ins_data_digit.contains(text_PCN_MOINS_L)) {
                        if(ins_data_digit.contains(text_PCN_SUD)) {
                            mUr1Bottom.setText(table_ins[ins_data_digit.indexOf(text_PCN_SUD)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_MOINS_L)) {
                            mUr1Bottom.setText(table_ins[ins_data_digit.indexOf(text_PCN_MOINS_L)+1]);
                        }
                    } else {
                        mUr1Bottom.setText("");
                    }


                    //Manage the + and E icons on the INS
                    if(ins_data_digit.contains(text_PCN_EST)
                            || ins_data_digit.contains(text_PCN_PLUS_R)) {
                        if(ins_data_digit.contains(text_PCN_EST)) {
                            mUr2Top.setText(table_ins[ins_data_digit.indexOf(text_PCN_EST)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_PLUS_R)) {
                            mUr2Top.setText(table_ins[ins_data_digit.indexOf(text_PCN_PLUS_R)+1]);
                        }
                    } else {
                        mUr2Top.setText("");
                    }


                    //Manage the - and W icons on the INS
                    if(ins_data_digit.contains(text_PCN_OUEST)
                            || ins_data_digit.contains(text_PCN_MOINS_R)) {
                        if(ins_data_digit.contains(text_PCN_OUEST)) {
                            mUr2Bottom.setText(table_ins[ins_data_digit.indexOf(text_PCN_OUEST)+1]);
                        }
                        if(ins_data_digit.contains(text_PCN_MOINS_R)) {
                            mUr2Bottom.setText(table_ins[ins_data_digit.indexOf(text_PCN_MOINS_R)+1]);
                        }
                    } else {
                        mUr2Bottom.setText("");
                    }


                }
            } else if(intent.getAction().contains(BroadcastKeys.M2KC_INS_BR)) {
                String ins = intent.getExtras().getString(BroadcastKeys.M2KC_INS_BR);
                if(ins != null && !ins.isEmpty()){
                    String[] table_ins = ins.split("\n");
                    List<String> ins_br_digit = Arrays.asList(table_ins);
                    if(ins_br_digit.contains(text_PCN_BR1)
                            ||ins_br_digit.contains(text_PCN_eBR1)) {
                        if(ins_br_digit.contains(text_PCN_BR1)) {
                            mBr1.setText(table_ins[ins_br_digit.indexOf(text_PCN_BR1)+1]);
                        }
                        if(ins_br_digit.contains(text_PCN_eBR1)) {
                            mBr1.setText(table_ins[ins_br_digit.indexOf(text_PCN_eBR1)+1]);
                        }
                    } else {
                        mBr1.setText("");
                    }
                    if(ins_br_digit.contains(text_PCN_BR2)
                            ||ins_br_digit.contains(text_PCN_eBR2)) {
                        if(ins_br_digit.contains(text_PCN_BR2)){
                            mBr2.setText(table_ins[ins_br_digit.indexOf(text_PCN_BR2)+1]);
                        }
                        if(ins_br_digit.contains(text_PCN_eBR2)){
                            mBr2.setText(table_ins[ins_br_digit.indexOf(text_PCN_eBR2)+1]);
                        }
                    } else {
                        mBr2.setText("");
                    }

                    if(ins_br_digit.contains(text_PCN_EMode)) {
                        mBrEmode.setText(table_ins[ins_br_digit.indexOf(text_PCN_EMode)+1]);
                    } else {
                        mBrEmode.setText("");
                    }
                }
            } else if(intent.getAction().contains(BroadcastKeys.M2KC_INS_DATA)) {
                String ins = intent.getExtras().getString(BroadcastKeys.M2KC_INS_DATA);
                if(ins != null && !ins.isEmpty()){
                    String[] table_data = ins.split(";");
                    mLightM91.setVisibility((table_data[0].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightM92.setVisibility((table_data[1].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightM93.setVisibility((table_data[2].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightPRET.setVisibility((table_data[3].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightALN.setVisibility((table_data[4].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightMIP.setVisibility((table_data[5].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightNDEG.setVisibility((table_data[6].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightSEC.setVisibility((table_data[7].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightUNI.setVisibility((table_data[8].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mRotator.setRotation(getDegreesKnob(Float.parseFloat(table_data[9])) - OFFSSET_ROTATOR);
                    mLightLevel = Float.valueOf(table_data[10]);
                    mLightPrep.setVisibility((table_data[11].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightEnc.setVisibility((table_data[12].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightDest.setVisibility((table_data[13].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightBad.setVisibility((table_data[14].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightRec.setVisibility((table_data[15].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightVal.setVisibility((table_data[16].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightMrc.setVisibility((table_data[17].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightEff.setVisibility((table_data[18].contains("1")?View.VISIBLE:View.INVISIBLE));
                    mLightIns.setVisibility((table_data[19].contains("1")?View.VISIBLE:View.INVISIBLE));

                    //We want a value between 0.3min and 1max
                    float bigValue = mLightLevel + 1.0f; //Light knob value is between -1 and 1, so moving this interval to 0:2
                    float smallValue = bigValue*0.7f/2.0f; //Now I have a value between 0 and 0.7
                    setLightLevel(smallValue + 0.3f);//Finally have my interval between 0.3 and 1
                }
            }

        }
    };

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


    private float getDegreesKnob(float value) {
        //Log.v(getTag(),"value: " + value);
        if(value < 0.075) {
            return 0;
        } else if(value < 0.125) {
            return 25;
        } else if(value < 0.225) {
            return 52;
        } else if(value < 0.325) {
            return 80;
        } else if(value < 0.425) {
            return 112;
        } else if(value < 0.525) {
            return 145;
        } else if(value < 0.625) {
            return 183;
        } else if(value < 0.725) {
            return 208;
        } else if(value < 0.825) {
            return 237;
        } else if(value < 0.925) {
            return 284;
        } else {
            return 323;
        }
    }



    private float getValueKnob(float value) {
        if(value < 10) {
            return 0;
        } else if(value < 40) {
            return 0.099998474121094f;
        } else if(value < 70) {
            return 0.19999694824219f;
        } else if(value < 108) {
            return 0.29999542236328f;
        } else if(value < 140) {
            return 0.39999389648438f;
        } else if(value < 170) {
            return 0.49999237060547f;
        } else if(value < 205) {
            return 0.59999084472656f;
        } else if(value < 235){
            return 0.69998931884766f;
        }else if(value < 264){
            return 0.79998779296875f;
        }else if(value < 300){
            return 0.89998626708984f;
        } else {
            return 1;
        }
    }

    private void setLightLevel(float pAlpha) {
        mLightPrep.setAlpha(pAlpha);
        mLightEnc.setAlpha(pAlpha);
        mLightDest.setAlpha(pAlpha);
        mLightBad.setAlpha(pAlpha);
        mLightRec.setAlpha(pAlpha);
        mLightMrc.setAlpha(pAlpha);
        mLightVal.setAlpha(pAlpha);
        mLightEff.setAlpha(pAlpha);
        mLightIns.setAlpha(pAlpha);
        mLightM91.setAlpha(pAlpha);
        mLightM92.setAlpha(pAlpha);
        mLightM93.setAlpha(pAlpha);
        mLightPRET.setAlpha(pAlpha);
        mLightALN.setAlpha(pAlpha);
        mLightMIP.setAlpha(pAlpha);
        mLightNDEG.setAlpha(pAlpha);
        mLightSEC.setAlpha(pAlpha);
        mLightUNI.setAlpha(pAlpha);
    }
}
