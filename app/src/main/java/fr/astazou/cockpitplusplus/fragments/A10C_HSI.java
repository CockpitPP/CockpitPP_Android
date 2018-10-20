package fr.astazou.cockpitplusplus.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.custom_views.A10C_HSI_View;
import fr.astazou.cockpitplusplus.utils.A10C_Commands;
import fr.astazou.cockpitplusplus.utils.AV8BNA_Commands;
import fr.astazou.cockpitplusplus.utils.BroadcastKeys;
import fr.astazou.cockpitplusplus.utils.UDPSender;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@linkk A10C_HSI.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A10C_HSI#  newInstance} factory method to
 * create an instance of this fragment.
 */
public class A10C_HSI extends Fragment {

    //The container of every view elements which must properly takes the size of the background
    private FrameLayout mContainerLinearLayout;

    public enum HSIDial {
        HEADINGSET, COURSESET
    }

    private Button mHSITouchView;
    private Button mHSIHeadingSetButton;
    private Button mHSICourseSetButton;
    private long mLastHSITap = System.currentTimeMillis();
    private float mLastHSIYValue;
    private A10C_VVI_HSI_COMBO.HSIDial mHSIDialChosen = A10C_VVI_HSI_COMBO.HSIDial.HEADINGSET;
    private String mHSIChangeValueINC = "+0.05";
    private String mHSIChangeValueDEC = "-0.05";
    //View for the HSI
    private A10C_HSI_View mA10CHSI_view;
    /**
     * Broadcast receiver to get data from DCS
     */
    BroadcastReceiver mBroadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().contains(BroadcastKeys.A10C_HSI)) {
                String data = intent.getExtras().getString(BroadcastKeys.A10C_HSI);
                if (data != null && !data.isEmpty()) {
                    mA10CHSI_view.setData(data);
                    //Log.d(TAG, "onReceive: !!!!!");
                }
            }
        }
    };

    public A10C_HSI() {
        // Required empty public constructor
    }

    /**
     * Register the broadcast receiver
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.mBroadCastNewMessage, new IntentFilter(BroadcastKeys.A10C_HSI));
    }

    /**
     * Bind the elements of the view leading the screen
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("asd","onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_a10_c__hsi, container, false);
        mContainerLinearLayout = (FrameLayout) rootView.findViewById(R.id.a_10_hsi_containerLinearLayout);
        mA10CHSI_view = new A10C_HSI_View(getActivity());
        mContainerLinearLayout.addView(mA10CHSI_view);
        mHSITouchView = (Button) rootView.findViewById(R.id.a10_hsi_dials_touch_view);
        mHSIHeadingSetButton = (Button) rootView.findViewById(R.id.a10_hsi_heading_set_button);
        mHSICourseSetButton = (Button) rootView.findViewById(R.id.a10_hsi_course_set_button);

        mHSITouchView.bringToFront();
        mContainerLinearLayout.requestLayout();

        /*
        This works!?
        View.OnTouchListener test = new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        };
        mA10CHSI_view.setOnTouchListener(test);
        */
        Button.OnTouchListener hsiHeadingSetOnTouchListener = new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHSIDialChosen = A10C_VVI_HSI_COMBO.HSIDial.HEADINGSET;
                        if (System.currentTimeMillis() - mLastHSITap < 400) {
                            Toast.makeText(getActivity().getBaseContext(), "Heading Set selected", Toast.LENGTH_SHORT).show();
                        }
                        mLastHSITap = System.currentTimeMillis();
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        mHSIHeadingSetButton.setOnTouchListener(hsiHeadingSetOnTouchListener);

        Button.OnTouchListener hsiCourseSetOnTouchListener = new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHSIDialChosen = A10C_VVI_HSI_COMBO.HSIDial.COURSESET;
                        if (System.currentTimeMillis() - mLastHSITap < 400) {
                            Toast.makeText(getActivity().getBaseContext(), "Course Set selected", Toast.LENGTH_SHORT).show();
                        }
                        mLastHSITap = System.currentTimeMillis();
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        mHSICourseSetButton.setOnTouchListener(hsiCourseSetOnTouchListener);

        View.OnTouchListener hsiOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        view.performClick();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        //view.performClick();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (motionEvent.getY() < mLastHSIYValue) {
                            //INC
                            if (mHSIDialChosen == A10C_VVI_HSI_COMBO.HSIDial.COURSESET) {
                                sendCommand(A10C_Commands.HSICourseSetChange, mHSIChangeValueINC);
                            } else {
                                //HEADING SET
                                sendCommand(A10C_Commands.HSIHeadingSetChange, mHSIChangeValueINC);
                            }
                        } else {
                            //DEC
                            if (mHSIDialChosen == A10C_VVI_HSI_COMBO.HSIDial.COURSESET) {
                                sendCommand(A10C_Commands.HSICourseSetChange, mHSIChangeValueDEC);
                            } else {
                                //HEADING SET
                                sendCommand(A10C_Commands.HSIHeadingSetChange, mHSIChangeValueDEC);
                            }
                        }
                        mLastHSIYValue = motionEvent.getY();
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        mHSITouchView.setOnTouchListener(hsiOnTouchListener);
        return rootView;
    }

    private void sendCommand(A10C_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(), pCommand.getDevice().getCode(), pCommand.getCode(), pValue, getContext());
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
     *
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
        /*mBackgroundImageView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mBackgroundImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mBackgroundImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mBackgroundImageView.getLayoutParams().width, mBackgroundImageView.getLayoutParams().height);
                        layoutParams.gravity = CENTER;

                    }
                });*/
        /*mContainerLinearLayout.setLayoutParams(layoutParams);
                        mContainerLinearLayout.getLayoutParams().height = mBackgroundImageView.getHeight();
                        mContainerLinearLayout.getLayoutParams().width = mBackgroundImageView.getWidth();*/
    }

    /**
     * Unregister the broadcast listener when leaving
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadCastNewMessage);
    }


}
