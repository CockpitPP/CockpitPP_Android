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
import android.widget.SeekBar;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.custom_views.VerticalSeekBar;
import fr.astazou.cockpitplusplus.utils.AV8BNA_Commands;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import fr.astazou.cockpitplusplus.utils.UDPSender;

import static android.view.Gravity.CENTER;


public class AV8BNA_NOZZLE_Controller extends Fragment {


    private ImageView mBackgroundView;
    private LinearLayout mContainer;

    private VerticalSeekBar mNozzleController;
    private VerticalSeekBar mBlockerNozzleController;

    private int MAX_VALUE_NOZZLE = 100;


    public AV8BNA_NOZZLE_Controller() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.AV8BNA_NOZZLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_av8bna_nozzle_controller, container, false);
        mBackgroundView = (ImageView) view.findViewById(R.id.layout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);

        mNozzleController = (VerticalSeekBar) view.findViewById(R.id.nozzle);
        mNozzleController.setOriginalThumb(getResources().getDrawable(R.drawable.av8bna_nozzle_controller));
        mNozzleController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendCommand(AV8BNA_Commands.NozzleController, String.valueOf(Float.valueOf(MAX_VALUE_NOZZLE-seekBar.getProgress())/100));
                //Do nothing
            }
        });

        mBlockerNozzleController = (VerticalSeekBar) view.findViewById(R.id.blocker);
        mBlockerNozzleController.setOriginalThumb(getResources().getDrawable(R.drawable.av8bna_nozzle_bloker));
        mBlockerNozzleController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Do nothing
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendCommand(AV8BNA_Commands.NozzleStopController, String.valueOf(Float.valueOf(MAX_VALUE_NOZZLE-seekBar.getProgress())/100));
            }
        });

        return view;
    }



    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.AV8BNA_NOZZLE)) {
                String ins = intent.getExtras().getString(BroadcastKeys.AV8BNA_NOZZLE);
                if(ins != null && !ins.isEmpty()){
                    String[] table_data = ins.split(";");
                    mNozzleController.setProgressAndThumb(MAX_VALUE_NOZZLE-Math.round(Float.parseFloat(table_data[0])*100));
                    mBlockerNozzleController.setProgressAndThumb(MAX_VALUE_NOZZLE-Math.round(Float.parseFloat(table_data[1])*100));
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

    private void sendCommand(AV8BNA_Commands pCommand) {
        sendCommand(pCommand, "1");
    }

    private void sendCommand(AV8BNA_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(),pCommand.getDevice().getCode(),pCommand.getCode(), pValue,getContext());
    }
}
