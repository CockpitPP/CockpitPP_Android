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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.custom_views.F15C_RWR_View;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;

import static android.view.Gravity.CENTER;

/**
 * Fragment of the F15C's RWR
 */
public class F15C_RWR extends Fragment {

    //The background image of the panel, every view element (textview, buttons, images, ...) must
    //be properly resized according to this image
    private ImageView mBackgroundView;

    //The container of every view elements which must properly takes the size of the background
    //image (mBackgroundView)
    private LinearLayout mContainer;

    //The layout where we add the custom_view
    private RelativeLayout mScreen;

    //View for the RWR
    private F15C_RWR_View mF15RWR;

    public F15C_RWR() {
        // Required empty public constructor
    }


    /**
     * Register the broadcast receiver
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.F15C_RWR));
    }

    /**
     * Bind the elements of the view leading the screen
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f15c_rwr, container, false);
        mBackgroundView = (ImageView)view.findViewById(R.id.layout);
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mScreen = (RelativeLayout) view.findViewById(R.id.screen);
        mF15RWR = new F15C_RWR_View(getActivity());
        mScreen.addView(mF15RWR);
        return view;
    }

    /**
     * Resize properly the elements view in the screen when we open the panel
     */
    @Override
    public void onResume() {
        super.onResume();
        resizeView();
    }

    /**
     * Detect when the user is rotating the phone/tablet
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resizeView();
    }

    /**
     * Method used to resize the elements in the view to match properly and with good proportion the
     * background image. Very important for the rotations
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
     * Unregister the broadcast listener when leaving
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadCastNewMessage);
    }

    /**
     * Broadcast receiver to get data from DCS
     */
    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().contains(BroadcastKeys.F15C_RWR)) {
                String rwr = intent.getExtras().getString(BroadcastKeys.F15C_RWR);
                //Log.d("F15C_RWR",rwr);
                if(rwr != null && !rwr.isEmpty()){
                    mF15RWR.setData(rwr);
                }
            }
        }
    };


}
