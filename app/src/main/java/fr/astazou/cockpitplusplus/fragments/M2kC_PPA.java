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


public class M2kC_PPA extends Fragment {

    private ImageView mBackgroundView;
    private LinearLayout mContainer;
    private TextView mQuantity;
    private TextView mDistance;
    private TextView mCanRoqS530Tot;
    private TextView mCanRoqS530Par;
    private TextView mS530P;
    private TextView mS530MIS;
    private TextView mAut;
    private TextView mMan;
    private TextView mMagicP;
    private TextView mMagicMAG;
    private ImageView mDet1;
    private ImageView mDet2;
    private ImageView mDet3;
    private ImageView mMissel1;
    private ImageView mMissel2;
    private ImageView mMissel3;
    private ImageView mTest1;
    private ImageView mTest2;
    private ImageView mTest3;
    private ImageView mQty1;
    private ImageView mQty2;
    private ImageView mQty3;
    private ImageView mDist1;
    private ImageView mDist2;
    private ImageView mDist3;
    private View mQtyMinus;
    private View mQtyPlus;
    private View mDistMinus;
    private View mDistPlus;

    public M2kC_PPA() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_PPA));
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.M2KC_PPABTN));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m2kc_ppa, container, false);
        mBackgroundView = (ImageView)view.findViewById(R.id.ppalayout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mQuantity = (TextView) view.findViewById(R.id.quantity);
        mDistance = (TextView) view.findViewById(R.id.distance);
        mCanRoqS530Tot = (TextView) view.findViewById(R.id.canRorS530Tot);
        mCanRoqS530Par = (TextView) view.findViewById(R.id.canRorS530Par);
        mS530P = (TextView) view.findViewById(R.id.s530P);
        mS530MIS = (TextView) view.findViewById(R.id.s530MIS);
        mAut = (TextView) view.findViewById(R.id.aut);
        mMan = (TextView) view.findViewById(R.id.man);
        mMagicP = (TextView) view.findViewById(R.id.magicP);
        mMagicMAG = (TextView) view.findViewById(R.id.magicMAG);
        mDet1 = (ImageView) view.findViewById(R.id.m2kc_det1);
        mDet2 = (ImageView) view.findViewById(R.id.m2kc_det2);
        mDet3 = (ImageView) view.findViewById(R.id.m2kc_det3);
        mMissel1 = (ImageView) view.findViewById(R.id.m2kc_missel1);
        mMissel2 = (ImageView) view.findViewById(R.id.m2kc_missel2);
        mMissel3 = (ImageView) view.findViewById(R.id.m2kc_missel3);
        mTest1 = (ImageView) view.findViewById(R.id.m2kc_ppa_test1);
        mTest2 = (ImageView) view.findViewById(R.id.m2kc_ppa_test2);
        mTest3 = (ImageView) view.findViewById(R.id.m2kc_ppa_test3);
        mQty1 = (ImageView) view.findViewById(R.id.m2kc_ppa_qty1);
        mQty2 = (ImageView) view.findViewById(R.id.m2kc_ppa_qty2);
        mQty3 = (ImageView) view.findViewById(R.id.m2kc_ppa_qty3);
        mDist1 = (ImageView) view.findViewById(R.id.m2kc_ppa_dist1);
        mDist2 = (ImageView) view.findViewById(R.id.m2kc_ppa_dist2);
        mDist3 = (ImageView) view.findViewById(R.id.m2kc_ppa_dist3);
        mDistMinus = view.findViewById(R.id.distMinus);
        mDistPlus = view.findViewById(R.id.distPlus);
        mQtyMinus = view.findViewById(R.id.qtyMinus);
        mQtyPlus = view.findViewById(R.id.qtyPlus);

        View.OnClickListener autMan = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.AutMan);
            }
        };
        mAut.setOnClickListener(autMan);
        mMan.setOnClickListener(autMan);

        View.OnClickListener magic = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Magic);
            }
        };
        mMagicP.setOnClickListener(magic);
        mMagicMAG.setOnClickListener(magic);

        View.OnClickListener missile = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.Missile);
            }
        };
        mS530P.setOnClickListener(missile);
        mS530MIS.setOnClickListener(missile);

        View.OnClickListener partTot = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.TotPart);
            }
        };
        mCanRoqS530Tot.setOnClickListener(partTot);
        mCanRoqS530Par.setOnClickListener(partTot);

        mDet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.BombFuze,"0.5");
            }
        });
        mDet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.BombFuze,"0");
            }
        });
        mDet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.BombFuze,"1");
            }
        });

        mMissel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.MissileSelector,"-1");
            }
        });
        mMissel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.MissileSelector,"1");
            }
        });
        mMissel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.MissileSelector,"0");
            }
        });

        mTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.PPATestSwitch,"-1");
            }
        });
        mTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.PPATestSwitch,"0");
            }
        });
        mTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand(M2KC_Commands.PPATestSwitch,"1");
            }
        });

        mDistMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDist1.setVisibility(View.INVISIBLE);
                mDist2.setVisibility(View.VISIBLE);
                mDist3.setVisibility(View.INVISIBLE);
                sendCommand(M2KC_Commands.PPADistance,"-1");

            }
        });
        mDistPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDist1.setVisibility(View.INVISIBLE);
                mDist2.setVisibility(View.INVISIBLE);
                mDist3.setVisibility(View.VISIBLE);
                sendCommand(M2KC_Commands.PPADistance,"1");
            }
        });
        mQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQty1.setVisibility(View.INVISIBLE);
                mQty2.setVisibility(View.VISIBLE);
                mQty3.setVisibility(View.INVISIBLE);
                sendCommand(M2KC_Commands.PPAQuantity,"-1");
            }
        });
        mQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQty1.setVisibility(View.INVISIBLE);
                mQty2.setVisibility(View.INVISIBLE);
                mQty3.setVisibility(View.VISIBLE);
                sendCommand(M2KC_Commands.PPAQuantity,"1");
            }
        });
        return view;
    }



    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().contains(BroadcastKeys.M2KC_PPA)) {
                String ppa = intent.getExtras().getString(BroadcastKeys.M2KC_PPA);
                if(ppa != null && !ppa.isEmpty()){
                    String[] table_ppa = ppa.split("\n");
                    if(table_ppa.length>8){
                        mQuantity.setText(table_ppa[5]);
                        mDistance.setText(table_ppa[8]);
                    }
                }
            }
            if(intent.getAction().contains(BroadcastKeys.M2KC_PPABTN)) {
                String ppabtn = intent.getExtras().getString(BroadcastKeys.M2KC_PPABTN);
                String btn[] = ppabtn.split(";");
                //Log.d("M2kC_PPA", "Received: " + btn[1] + ","+ btn[2] + ","+ btn[3] + ","+ btn[4]);
                if(btn[0].contains("0.5")){
                    mDet1.setVisibility(View.GONE);
                    mDet2.setVisibility(View.VISIBLE);
                    mDet3.setVisibility(View.GONE);
                } else if(btn[0].contains("0")) {
                    mDet1.setVisibility(View.GONE);
                    mDet2.setVisibility(View.GONE);
                    mDet3.setVisibility(View.VISIBLE);
                } else {
                    mDet1.setVisibility(View.VISIBLE);
                    mDet2.setVisibility(View.GONE);
                    mDet3.setVisibility(View.GONE);
                }

                if(btn[1].contains("-1")){
                    mMissel1.setVisibility(View.GONE);
                    mMissel2.setVisibility(View.VISIBLE);
                    mMissel3.setVisibility(View.GONE);
                } else if(btn[1].contains("0")) {
                    mMissel1.setVisibility(View.VISIBLE);
                    mMissel2.setVisibility(View.GONE);
                    mMissel3.setVisibility(View.GONE);
                } else {
                    mMissel1.setVisibility(View.GONE);
                    mMissel2.setVisibility(View.GONE);
                    mMissel3.setVisibility(View.VISIBLE);
                }

                if(btn[2].contains("0")){
                    mQty1.setVisibility(View.VISIBLE);
                    mQty2.setVisibility(View.INVISIBLE);
                    mQty3.setVisibility(View.INVISIBLE);
                } else if(btn[2].contains("-1")) {
                    mQty1.setVisibility(View.INVISIBLE);
                    mQty2.setVisibility(View.VISIBLE);
                    mQty3.setVisibility(View.INVISIBLE);
                } else {
                    mQty1.setVisibility(View.INVISIBLE);
                    mQty2.setVisibility(View.INVISIBLE);
                    mQty3.setVisibility(View.VISIBLE);
                }

                if(btn[3].contains("-1")){
                    mDist1.setVisibility(View.INVISIBLE);
                    mDist2.setVisibility(View.VISIBLE);
                    mDist3.setVisibility(View.INVISIBLE);
                } else if(btn[3].contains("0")) {
                    mDist1.setVisibility(View.VISIBLE);
                    mDist2.setVisibility(View.INVISIBLE);
                    mDist3.setVisibility(View.INVISIBLE);
                } else {
                    mDist1.setVisibility(View.INVISIBLE);
                    mDist2.setVisibility(View.INVISIBLE);
                    mDist3.setVisibility(View.VISIBLE);
                }

                if(btn[4].contains("0")){
                    mTest1.setVisibility(View.VISIBLE);
                    mTest2.setVisibility(View.GONE);
                    mTest3.setVisibility(View.GONE);
                } else if(btn[4].contains("-1")) {
                    mTest1.setVisibility(View.GONE);
                    mTest2.setVisibility(View.GONE);
                    mTest3.setVisibility(View.VISIBLE);
                } else {
                    mTest1.setVisibility(View.GONE);
                    mTest2.setVisibility(View.VISIBLE);
                    mTest3.setVisibility(View.GONE);
                }

                mS530P.setText((btn[5].contains("1")?" P ":"   "));
                if(getActivity()!=null && getActivity().getResources() != null) {
                    mS530MIS.setTextColor(btn[6].contains("1")?
                            getActivity().getResources().getColor(R.color.m2kCYellow)
                            :getActivity().getResources().getColor(R.color.m2kCGrey));
                }
                mAut.setText((btn[7].contains("1")?"AUT":"   "));
                mMan.setText((btn[8].contains("1")?"MAN":"   "));
                mMagicP.setText((btn[9].contains("1")?" P ":"   "));
                mMagicMAG.setText((btn[10].contains("1")?"MAG":"   "));
                mCanRoqS530Tot.setText((btn[11].contains("1")?"TOT":"   "));
                mCanRoqS530Par.setText((btn[12].contains("1")?"PAR":"   "));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadCastNewMessage);
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

    private void sendCommand(M2KC_Commands pCommand) {
        sendCommand(pCommand, "1");
    }

    private void sendCommand(M2KC_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(),pCommand.getDevice().getCode(),pCommand.getCode(), pValue,getContext());
    }
}
