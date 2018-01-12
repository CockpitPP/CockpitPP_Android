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
import android.widget.TextView;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import fr.astazou.cockpitplusplus.utils.M2KC_Commands;
import fr.astazou.cockpitplusplus.utils.UDPSender;

import static android.view.Gravity.CENTER;

/**
 *
 */
public class M2kC_PCA extends Fragment {

    private ImageView mBackgroundView;
    private LinearLayout mContainer;

    private TextView mUr1;
    private TextView mUr2;
    private TextView mUr3;
    private TextView mUr4;
    private TextView mUr5;
    private TextView mBr1;
    private TextView mBr2;
    private TextView mBr3;
    private TextView mBr4;
    private TextView mBr5;
    private TextView mUr1S;
    private TextView mUr2S;
    private TextView mUr3S;
    private TextView mUr4S;
    private TextView mUr5S;
    private TextView mBr1S;
    private TextView mBr1P;
    private TextView mBr2S;
    private TextView mBr2P;
    private TextView mBr3S;
    private TextView mBr3P;
    private TextView mBr4S;
    private TextView mBr4P;
    private TextView mBr5S;
    private TextView mBr5P;
    private TextView mK1;
    private TextView mK2;
    private ImageView mMasterArmUp;
    private ImageView mMasterArmDown;
    private ImageView mJetti1;
    private ImageView mJetti2;
    private ImageView mJetti3;
    private LinearLayout mKBtn;
    private LinearLayout mBrBtn1;
    private LinearLayout mBrBtn2;
    private LinearLayout mBrBtn3;
    private LinearLayout mBrBtn4;
    private LinearLayout mBrBtn5;

    public M2kC_PCA() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_UR));
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_BR));
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_PCABTN));
    }


    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().contains(BroadcastKeys.M2KC_UR)) {
                String urs = intent.getExtras().getString(BroadcastKeys.M2KC_UR);
                //Log.d("urs",urs);
                String[] table_ur = urs.split("\n");
                if(table_ur.length>17) {
                    mUr1.setText(table_ur[5]);
                    mUr2.setText(table_ur[8]);
                    mUr3.setText(table_ur[11]);
                    mUr4.setText(table_ur[14]);
                    mUr5.setText(table_ur[17]);
                }
            }

            if(intent.getAction().contains(BroadcastKeys.M2KC_BR)) {
                String brs = intent.getExtras().getString(BroadcastKeys.M2KC_BR);
                //Log.d("brs",brs);
                String[] table_br = brs.split("\n");
                if(table_br.length>17) {
                    mBr1.setText(table_br[5]);
                    mBr2.setText(table_br[8]);
                    mBr3.setText(table_br[11]);
                    mBr4.setText(table_br[14]);
                    mBr5.setText(table_br[17]);
                } else if(table_br.length>14) {
                    mBr1.setText(table_br[5]);
                    mBr2.setText(table_br[8]);
                    mBr3.setText(table_br[11]);
                    mBr4.setText(table_br[14]);
                    mBr5.setText("");
                } else if(table_br.length>11) {
                    mBr1.setText(table_br[5]);
                    mBr2.setText(table_br[8]);
                    mBr3.setText(table_br[11]);
                    mBr4.setText("");
                    mBr5.setText("");
                } else if(table_br.length>8) {
                    mBr1.setText(table_br[5]);
                    mBr2.setText(table_br[8]);
                    mBr3.setText("");
                    mBr4.setText("");
                    mBr5.setText("");
                } else if(table_br.length>5) {
                    mBr1.setText(table_br[5]);
                    mBr2.setText("");
                    mBr3.setText("");
                    mBr4.setText("");
                    mBr5.setText("");
                } else {
                    mBr1.setText("");
                    mBr2.setText("");
                    mBr3.setText("");
                    mBr4.setText("");
                    mBr5.setText("");
                }
            }


            if(intent.getAction().contains(BroadcastKeys.M2KC_PCABTN)) {
                String pcabtn = intent.getExtras().getString(BroadcastKeys.M2KC_PCABTN);
                //Log.d("pcabtn",pcabtn);
                String btn[] = pcabtn.split(";");
                if(btn[0].contains("-1") || btn[0].contains("0")) {
                    mMasterArmDown.setVisibility(View.VISIBLE);
                    mMasterArmUp.setVisibility(View.GONE);
                } else {
                    mMasterArmUp.setVisibility(View.VISIBLE);
                    mMasterArmDown.setVisibility(View.GONE);
                }
                if(btn[2].contains("1")) {
                    mJetti1.setVisibility(View.GONE);
                    mJetti2.setVisibility(View.GONE);
                    mJetti3.setVisibility(View.VISIBLE);
                } else if(btn[2].contains("0") && btn[3].contains("1") ){
                    mJetti1.setVisibility(View.GONE);
                    mJetti2.setVisibility(View.VISIBLE);
                    mJetti3.setVisibility(View.GONE);
                } else {
                    mJetti1.setVisibility(View.VISIBLE);
                    mJetti2.setVisibility(View.GONE);
                    mJetti3.setVisibility(View.GONE);
                }
                mUr1S.setText((btn[4].contains("1")?"S":" "));
                mUr2S.setText((btn[5].contains("1")?"S":" "));
                mUr3S.setText((btn[6].contains("1")?"S":" "));
                mUr4S.setText((btn[7].contains("1")?"S":" "));
                mUr5S.setText((btn[8].contains("1")?"S":" "));
                mK1.setText((btn[9].contains("1")?"K1":"  "));
                mK2.setText((btn[10].contains("1")?"k2":"  "));
                mBr1S.setText((btn[11].contains("1")?"S":" "));
                mBr1P.setText((btn[12].contains("1")?"P":" "));
                mBr2S.setText((btn[13].contains("1")?"S":" "));
                mBr2P.setText((btn[14].contains("1")?"P":" "));
                mBr3S.setText((btn[15].contains("1")?"S":" "));
                mBr3P.setText((btn[16].contains("1")?"P":" "));
                mBr4S.setText((btn[17].contains("1")?"S":" "));
                mBr4P.setText((btn[18].contains("1")?"P":" "));
                mBr5S.setText((btn[19].contains("1")?"S":" "));
                mBr5P.setText((btn[20].contains("1")?"P":" "));
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_m2kc_pca, container, false);
        mBackgroundView = (ImageView)view.findViewById(R.id.pcalayout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mUr1 = (TextView) view.findViewById(R.id.ur1);
        mUr2 = (TextView) view.findViewById(R.id.ur2);
        mUr3 = (TextView) view.findViewById(R.id.ur3);
        mUr4 = (TextView) view.findViewById(R.id.ur4);
        mUr5 = (TextView) view.findViewById(R.id.ur5);
        mBr1 = (TextView) view.findViewById(R.id.br1);
        mBr2 = (TextView) view.findViewById(R.id.br2);
        mBr3 = (TextView) view.findViewById(R.id.br3);
        mBr4 = (TextView) view.findViewById(R.id.br4);
        mBr5 = (TextView) view.findViewById(R.id.br5);
        mUr1S = (TextView) view.findViewById(R.id.ur1s);
        mUr2S = (TextView) view.findViewById(R.id.ur2s);
        mUr3S = (TextView) view.findViewById(R.id.ur3s);
        mUr4S = (TextView) view.findViewById(R.id.ur4s);
        mUr5S = (TextView) view.findViewById(R.id.ur5s);
        mBr1S = (TextView) view.findViewById(R.id.br1s);
        mBr1P = (TextView) view.findViewById(R.id.br1p);
        mBr2S = (TextView) view.findViewById(R.id.br2s);
        mBr2P = (TextView) view.findViewById(R.id.br2p);
        mBr3S = (TextView) view.findViewById(R.id.br3s);
        mBr3P = (TextView) view.findViewById(R.id.br3p);
        mBr4S = (TextView) view.findViewById(R.id.br4s);
        mBr4P = (TextView) view.findViewById(R.id.br4p);
        mBr5S = (TextView) view.findViewById(R.id.br5s);
        mBr5P = (TextView) view.findViewById(R.id.br5p);
        mK1 = (TextView) view.findViewById(R.id.k1);
        mK2 = (TextView) view.findViewById(R.id.k2);
        mMasterArmUp = (ImageView) view.findViewById(R.id.masterArmUp);
        mMasterArmDown = (ImageView) view.findViewById(R.id.masterArmDown);
        mJetti1 = (ImageView) view.findViewById(R.id.jetti1);
        mJetti2 = (ImageView) view.findViewById(R.id.jetti2);
        mJetti3 = (ImageView) view.findViewById(R.id.jetti3);
        mKBtn = (LinearLayout) view.findViewById(R.id.kbtn);
        mBrBtn1 = (LinearLayout) view.findViewById(R.id.ur1btn);
        mBrBtn2 = (LinearLayout) view.findViewById(R.id.ur2btn);
        mBrBtn3 = (LinearLayout) view.findViewById(R.id.ur3btn);
        mBrBtn4 = (LinearLayout) view.findViewById(R.id.ur4btn);
        mBrBtn5 = (LinearLayout) view.findViewById(R.id.ur5btn);

        View.OnClickListener onClickListenerMasterArm = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.SwitchMasterArm);
            }
        };
        mMasterArmUp.setOnClickListener(onClickListenerMasterArm);
        mMasterArmDown.setOnClickListener(onClickListenerMasterArm);

        mJetti1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.JettisonCover,"1");
            }
        });
        mJetti2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.JettisonSwitch,"1");
            }
        });
        mJetti3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.JettisonCover,"0");
            }
        });

        mUr1S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Ur1Btn);
            }
        });
        mUr2S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Ur2Btn);
            }
        });
        mUr3S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Ur3Btn);
            }
        });
        mUr4S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Ur4Btn);
            }
        });
        mUr5S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Ur5Btn);
            }
        });
        mBrBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Br1Btn);
            }
        });
        mBrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Br2Btn);
            }
        });
        mBrBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Br3Btn);
            }
        });
        mBrBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Br4Btn);
            }
        });
        mBrBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Br5Btn);
            }
        });
        mKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.GunModBtn);
            }
        });

        return view;
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
