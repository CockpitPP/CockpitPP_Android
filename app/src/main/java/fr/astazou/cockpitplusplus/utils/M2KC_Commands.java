package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 18/09/2016.
 *
 */
public enum M2KC_Commands {


    PlaneGearUp(430, M2KC_Devices.MISCPANELS, TypeButtonCodes.Simple),
    PlaneGearDown(431, M2KC_Devices.MISCPANELS, TypeButtonCodes.Simple),

    //M2kC_PCA
    SwitchMasterArm(283, M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    GunSafeSwitch(463, M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    GunModBtn(3245, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    JettisonCover(3248, M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    JettisonSwitch(3249, M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    Ur1Btn(3235, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Ur2Btn(3237, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Ur3Btn(3239, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Ur4Btn(3241, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Ur5Btn(3243, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Br1Btn(3250, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Br2Btn(3253, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Br3Btn(3256, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Br4Btn(3259, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Br5Btn(3262, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),

    //M2kC_PPA
    BombFuze(3276,M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    Missile(3266,M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    Magic(3272,M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    TotPart(3279,M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    AutMan(3269,M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    MissileSelector(3265, M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    PPATestSwitch(3275, M2KC_Devices.PCA_PPA, TypeButtonCodes.Simple),
    PPAQuantity(3277, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),
    PPADistance(3278, M2KC_Devices.PCA_PPA, TypeButtonCodes.Push),

    //M2kC_INS
    InsKnob(3574, M2KC_Devices.PCN_NAV, TypeButtonCodes.Simple),
    InsKnobLight(3575, M2KC_Devices.SYSLIGHTS, TypeButtonCodes.Simple),
    InsBtn1(3584, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn2(3585, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn3(3586, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn4(3587, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn5(3588, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn6(3589, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn7(3590, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn8(3591, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn9(3592, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtn0(3593, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnEff(3594, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnIns(3596, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnPrep(3570, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnEnc(3667, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnDest(3572, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnBad(3576, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnRec(3578, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnVal(3580, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),
    InsBtnMrc(3582, M2KC_Devices.PCN_NAV, TypeButtonCodes.Push),

    //M2kC_INS_knob
    InsKnobLeft(3627, M2KC_Devices.PCN_NAV, TypeButtonCodes.Simple),
    InsKnobRight(3629, M2KC_Devices.PCN_NAV, TypeButtonCodes.Simple);


    private final int mCode;
    private final M2KC_Devices mDevice;
    private final TypeButtonCodes mTypeButton;

    M2KC_Commands(int pCode, M2KC_Devices pDevice, TypeButtonCodes pTypeButton) {
        mCode = pCode;
        mDevice = pDevice;
        mTypeButton = pTypeButton;
    }

    public int getCode() {
        return mCode;
    }
    public M2KC_Devices getDevice() {
        return mDevice;
    }
    public TypeButtonCodes getTypeButton() {
        return mTypeButton;
    }
}
