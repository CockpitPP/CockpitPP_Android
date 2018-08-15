package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 10/06/2016.
 *
 */
public enum MiG21Bis_Commands {

    //Panel armament
    RadarState(3094, MiG21Bis_Devices.UUA,TypeButtonCodes.Simple),
    RadarLow(3095, MiG21Bis_Devices.UUA,TypeButtonCodes.Simple),
    RadarBeam(3096, MiG21Bis_Devices.UUA,TypeButtonCodes.Simple);



    private final int mCode;
    private final MiG21Bis_Devices mDevice;
    private final TypeButtonCodes mTypeButton;

    MiG21Bis_Commands(int pCode, MiG21Bis_Devices pDevice, TypeButtonCodes pTypeButton) {
        mCode = pCode;
        mDevice = pDevice;
        mTypeButton = pTypeButton;
    }

    public int getCode() {
        return mCode;
    }
    public MiG21Bis_Devices getDevice() {
        return mDevice;
    }
    public TypeButtonCodes getTypeButton() {
        return mTypeButton;
    }
}
