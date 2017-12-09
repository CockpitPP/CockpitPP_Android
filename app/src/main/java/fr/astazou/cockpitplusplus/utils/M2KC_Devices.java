package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 22/04/2017.
 *
 */
public enum M2KC_Devices {

    FLIGHTINST(1),
    NAVINST(2),
    ENGINE(3),
    INSTPANEL(4),
    VTH_VTB(5),
    PCA_PPA(6),
    ENGPANEL(7),
    PWRPNL(8),
    PCN_NAV(9),
    RADAR_RDI(10),
    RADAR(11),
    EW_RWR(12),
    RWR(13),
    SUBSYSTEMS(14),
    MAGIC(15),
    SYSLIGHTS(16),
    AFCS(17),
    ELECTRIC(18),
    UVHF(19),
    UHF(20),
    INTERCOM(21),
    MISCPANELS(22),
    TACAN(23),
    VORILS(24),
    ECS(25),
    FBW(26),
    DDM(27),
    DDM_IND(28),
    WEAPONS_CONTROL(29);

    private final int mCode;

    M2KC_Devices(int pCode) {
        mCode = pCode;
    }

    public int getCode() {
        return mCode;
    }
}
