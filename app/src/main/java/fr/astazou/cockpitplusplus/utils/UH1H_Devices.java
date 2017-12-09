package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 13/09/2017.
 *
 */
public enum UH1H_Devices {

    ELEC_INTERFACE(1),
    FUELSYS_INTERFACE(2),
    ENGINE_INTERFACE(3),
    HYDRO_SYS_INTERFACE(4),
    ADI_PILOT(5),
    ADI_OPERATOR(6),
    NAVLIGHT_SYSTEM(7),
    SOUND_SYSTEM(8),
    WEAPON_SYS(9),
    GMCS(10),
    VARIOMETER_PILOT(11),
    CPT_MECH(12),
    RADAR_ALTIMETER(13),
    MISC_SYSTEMS_INTERFACE(14),
    SYS_CONTROLLER(15),
    HELMET_DEVICE(16),
    IFF(17),
    AAU7(18),
    AAU32(19),
    VHF_ARC_134(20),
    INTERCOM(21),
    UHF_ARC_51(22),
    VHF_ARC_131(23),
    ILS(24),
    ARN_82(25),
    MARKER_BEACON(26),
    ADF_ARN_83(27),
    REMOTE_COMPASS(28),
    COURSE_IND(29),
    CLOCK(30),
    FM_PROXY(31),
    FLEX_SIGHT(32),
    TURN_IND(33),
    NAVIGATION_RELAY(34),
    MD_1_GYRO(35),
    ROOF_AIRSPEED(36),
    NOSE_AIRSPEED(37),
    COPILOT_RMI(38),
    STANDBY_COMPASS(39),
    VARIOMETER_COPILOT(40),
    CONTROL_SYSTEM(41),
    MACROS(42),
    ARCADE(43),
    PADLOCK(44),
    KNEEBOARD(45),
    HEAD_WRAPPER(46),
    HEATING_SYSTEM(47),
    CARGO_CAM(48),
    PILOT_SIGHT(49),
    XM_130(50),
    EXTERNAL_CARGO_VIEW(51);

    private final int mCode;

    UH1H_Devices(int pCode) {
        mCode = pCode;
    }

    public int getCode() {
        return mCode;
    }
}
