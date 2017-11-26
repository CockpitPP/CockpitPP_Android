package fr.astazou.cockpitplusplus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.astazou.cockpitplusplus.R;
import fr.astazou.cockpitplusplus.utils.UDPSender;

public class Config_SandBox extends Fragment {

    private Button mSendButton;
    private EditText mCode;
    private EditText mDevice;
    private EditText mButtonType;
    private EditText mValue;

    private View mRootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView =  inflater.inflate(R.layout.fragment_sandbox, container, false);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCode = (EditText) mRootView.findViewById(R.id.editTextCode);
        mDevice = (EditText) mRootView.findViewById(R.id.editTextDevice);
        mButtonType = (EditText) mRootView.findViewById(R.id.editTextButtonType);
        mValue = (EditText) mRootView.findViewById(R.id.editTextValue);
        mSendButton = (Button) mRootView.findViewById(R.id.buttonSend);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCode.getText().toString().isEmpty()
                        || mDevice.getText().toString().isEmpty()
                        || mButtonType.getText().toString().isEmpty()
                        || mValue.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),R.string.Sandbox_inputsNull,Toast.LENGTH_SHORT).show();
                } else {
                    UDPSender.getInstance().sendToDCS(
                            Integer.valueOf(mButtonType.getText().toString()),
                            Integer.valueOf(mDevice.getText().toString()),
                            Integer.valueOf(mCode.getText().toString()),
                            mValue.getText().toString(),
                            getActivity());
                }
            }
        });
    }
}
