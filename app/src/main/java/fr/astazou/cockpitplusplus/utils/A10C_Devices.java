package fr.astazou.cockpitplusplus.utils;

/**
 * Created by JERKER on 11/06/2018.
 *
 */
public enum A10C_Devices {
    ELEC_INTERFACE(1),
    MFCD_LEFT(2),
    MFCD_RIGHT(3),
    CMSP(4),
    CMSC(5),
    JAMMERS_INTERFACE(6),
    AHCP(7),
    UFC(8),
    CDU(9),
    MAVERICK_INTERFACE(10),
    LITENING_INTERFACE(11),
    IFFCC(12),
    DSMS_INTERFACE(13),
    DATA_TRANSFER_SYSTEM(14),
    DIGITAL_CLOCK(15),
    DBG(16),
    HOTAS(17),
    EGI(18),
    HUD(19),
    DTSAS(20),
    NAVIGATION_COMPUTER(21),
    AAP(22),
    CADC(23),
    SYS_CONTROLLER(24),
    PULSE_TIMER(25),
    TAD(26),
    VMU(27),
    TONE_GENERATOR(28),
    AN_ALR69V(29),
    AN_ALE40V(30),
    SIDEWINDER_INTERFACE(31),
    SADL(32),
    IAM_INTERFACE(33),
    HELMET_DEVICE(34),
    FM_PROXY(35),
    FUEL_SYSTEM(36),
    ENGINE_SYSTEM(37),
    AUTOPILOT(38),
    CPT_MECH(39),
    OXYGEN_SYSTEM(40),
    ENVIRONMENT_SYSTEM(41),
    HYDRAULIC_SYSTEM(42),
    IFF(43),
    HARS(44),
    HSI(45),
    NMSP(46),
    ADI(47),
    SAI(48),
    LIGHT_SYSTEM(49),
    FIRE_SYSTEM(50),
    TACAN(51),
    STALL(52),
    ILS(53),
    UHF_RADIO(54),
    VHF_AM_RADIO(55),
    VHF_FM_RADIO(56),
    TISL(57),
    INTERCOM(58),
    AAR47(59),
    RT1720(60),
    AIRSPEED_INDICATOR(61),
    AAU34(62),
    CICU(63),
    STANDBY_COMPASS(64),
    ARCADE(65),
    PADLOCK(66),
    AN_APN_194(67),
    REMOTE_COMPASS(68),
    KY_58(69),
    MACROS(70),
    AVIONICS_PROXY(71),
    ACCELEROMETER(72),
    DVADR(73),
    TACAN_CTRL_PANEL(74);

    private final int mCode;

    A10C_Devices(int pCode) {
        mCode = pCode;
    }

    public int getCode() {
        return mCode;
    }
}
