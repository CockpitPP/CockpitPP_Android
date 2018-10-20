package fr.astazou.cockpitplusplus.utils;

/**
 * Created by JERKER on 11/09/2018.
 *
 */
public enum A10C_Commands {


    HSICourseSetChange(3002, A10C_Devices.HSI, TypeButtonCodes.Push),
    HSIHeadingSetChange(3001, A10C_Devices.HSI, TypeButtonCodes.Push);

    private final int mCode;
    private final A10C_Devices mDevice;
    private final TypeButtonCodes mTypeButton;

    A10C_Commands(int pCode, A10C_Devices pDevice, TypeButtonCodes pTypeButton) {
        mCode = pCode;
        mDevice = pDevice;
        mTypeButton = pTypeButton;
    }

    public int getCode() {
        return mCode;
    }
    public A10C_Devices getDevice() {
        return mDevice;
    }
    public TypeButtonCodes getTypeButton() {
        return mTypeButton;
    }
}
