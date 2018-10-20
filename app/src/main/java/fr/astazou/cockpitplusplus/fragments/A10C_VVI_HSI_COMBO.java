package fr.astazou.cockpitplusplus.fragments;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.A10C_Commands;
import fr.astazou.cockpitplusplus.utils.UDPSender;

/**
 *
 */
public class A10C_VVI_HSI_COMBO extends Fragment {


    //The background image of the panel, every view element (textview, buttons, images, ...) must
    //be properly resized according to this image
    public enum HSIDial {
        HEADINGSET, COURSESET
    }

    private Button mHSITouchView;
    private Button mHSIHeadingSetButton;
    private Button mHSICourseSetButton;
    private long mLastHSITap = System.currentTimeMillis();
    private float mLastHSIYValue;
    private HSIDial mHSIDialChosen = HSIDial.HEADINGSET;
    private String mHSIChangeValueINC = "+0.05";
    private String mHSIChangeValueDEC = "-0.05";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = new FrameLayout(getActivity());
        populateViewForOrientation(inflater, frameLayout);
        return frameLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Reload current fragment
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_a10_c_vvi_hsi, viewGroup);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment vvi = new A10C_VVI();
        transaction.replace(R.id.a10_vvi_combo, vvi).commit();
        transaction = getChildFragmentManager().beginTransaction();
        Fragment hsi = new A10C_HSI();
        transaction.replace(R.id.a10_hsi_combo, hsi).commit();

        mHSITouchView = (Button) subview.findViewById(R.id.a10_hsi_dials_touch_view);
        mHSIHeadingSetButton = (Button) subview.findViewById(R.id.a10_hsi_heading_set_button);
        mHSICourseSetButton = (Button) subview.findViewById(R.id.a10_hsi_course_set_button);
        Button.OnTouchListener hsiHeadingSetOnTouchListener = new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHSIDialChosen = HSIDial.HEADINGSET;
                        if (System.currentTimeMillis() - mLastHSITap < 300) {
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
                        mHSIDialChosen = HSIDial.COURSESET;
                        if (System.currentTimeMillis() - mLastHSITap < 300) {
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
                        if (motionEvent.getY() < mLastHSIYValue - 10) {
                            //INC
                            if (mHSIDialChosen == HSIDial.COURSESET) {
                                sendCommand(A10C_Commands.HSICourseSetChange, mHSIChangeValueINC);
                            } else {
                                //HEADING SET
                                sendCommand(A10C_Commands.HSIHeadingSetChange, mHSIChangeValueINC);
                            }
                            mLastHSIYValue = motionEvent.getY();
                        } else if (motionEvent.getY() > mLastHSIYValue + 10){
                            //DEC
                            if (mHSIDialChosen == HSIDial.COURSESET) {
                                sendCommand(A10C_Commands.HSICourseSetChange, mHSIChangeValueDEC);
                            } else {
                                //HEADING SET
                                sendCommand(A10C_Commands.HSIHeadingSetChange, mHSIChangeValueDEC);
                            }
                            mLastHSIYValue = motionEvent.getY();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        mHSITouchView.setOnTouchListener(hsiOnTouchListener);
    }

    private void sendCommand(A10C_Commands pCommand, String pValue) {
        UDPSender.getInstance().sendToDCS(pCommand.getTypeButton().getCode(), pCommand.getDevice().getCode(), pCommand.getCode(), pValue, getContext());
    }
}
