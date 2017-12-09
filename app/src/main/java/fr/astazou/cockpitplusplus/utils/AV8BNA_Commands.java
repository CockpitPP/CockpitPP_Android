package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 01/12/2017.
 *
 */
public enum AV8BNA_Commands {


    // Nozzle controller
    NozzleController(3487, AV8BNA_Devices.VREST, TypeButtonCodes.Simple),
    NozzleStopController(3488, AV8BNA_Devices.VREST, TypeButtonCodes.Simple);


    private final int mCode;
    private final AV8BNA_Devices mDevice;
    private final TypeButtonCodes mTypeButton;

    AV8BNA_Commands(int pCode, AV8BNA_Devices pDevice, TypeButtonCodes pTypeButton) {
        mCode = pCode;
        mDevice = pDevice;
        mTypeButton = pTypeButton;
    }

    public int getCode() {
        return mCode;
    }
    public AV8BNA_Devices getDevice() {
        return mDevice;
    }
    public TypeButtonCodes getTypeButton() {
        return mTypeButton;
    }
}
