package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 18/09/2016.
 *
 */
public enum UH1H_Commands {

    //Panel armament
    SwitchArmament(3008, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Simple),
    SwitchGun(3009, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Simple),
    SwitchCalib(3010, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Simple),
    KnobRkt(3011, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Simple),
    ResetRkt(3012, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Push),
    JettisonCover(3013, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Simple),
    SwitchJettison(3014, UH1H_Devices.WEAPON_SYS, TypeButtonCodes.Push);


    private final int mCode;
    private final UH1H_Devices mDevice;
    private final TypeButtonCodes mTypeButton;

    UH1H_Commands(int pCode, UH1H_Devices pDevice, TypeButtonCodes pTypeButton) {
        mCode = pCode;
        mDevice = pDevice;
        mTypeButton = pTypeButton;
    }

    public int getCode() {
        return mCode;
    }
    public UH1H_Devices getDevice() {
        return mDevice;
    }
    public TypeButtonCodes getTypeButton() {
        return mTypeButton;
    }
}
