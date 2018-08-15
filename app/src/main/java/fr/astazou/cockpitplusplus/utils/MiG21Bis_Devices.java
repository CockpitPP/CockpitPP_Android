package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 13/09/2017.
 *
 */
public enum MiG21Bis_Devices {

    DC_BUS(1),
    AC_BUS(2),
    ENGINE_START_DEVICE(3),
    FUEL_PUMPS(4),
    FUEL_SYSTEM(5),
    ENGINE(6),
    //?(7),
    CONTROL_SYSTEM(8),
    SAU(9),
    TRIMER(10),
    SPS(11),
    ARU(12),
    AIRBRAKE(13),
    GEAR_BRAKES(14),
    GEARS(15),
    FLAPS(16),
    CHUTE(17),
    KONUS(18),
    SOPLO(19),
    OXYGENE_SYSTEM(20),
    COMPRESSED_AIR_SYSTEM(21),
    GYRO_DEVICES(22),
    RADIO(23),
    //?(24),
    //?(25),
    KSI(26),
    ARK(27),
    RSBN(28),
    avAChS(29),
    //?(30),
    PITOT_TUBES(31),
    KPP(32),
    IAS_INDICATOR(33),
    TAS_INDICATOR(34),
    M_INDICATOR(35),
    ALTIMETER(36),
    RADIO_ALTIMETER(37),
    DA_200(38),
    ACCELEROMETER(39),
    UUA(40),
    SPO(41),
    SRZO(42),
    SOD(43),
    RADAR(44),
    ASP(45),
    WEAPON_CONTROL(46),
    CANOPY(47),
    MAIN_HYDRO(48),
    //?(49),
    HELMET_VISOR(50),
    //?(51);
    PADLOCK(52),
    LIGHTS(53),
    LIGHTS_WARNING(54),
    SPRD(55),
    SARPP(56),
    AIR_CONDITIONING(57),
    UNCLASSIFIED(58),
    MARKER(59),
    FIRE_EXTINGUISHER(60),
    MACROS(61);
    private final int mCode;

    MiG21Bis_Devices(int pCode) {
        mCode = pCode;
    }

    public int getCode() {
        return mCode;
    }
}
