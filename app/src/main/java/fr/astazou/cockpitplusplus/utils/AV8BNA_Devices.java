package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 22/04/2017.
 *
 */
public enum AV8BNA_Devices {

    ELECTRIC(1),
    COMM1(2),
    COMM2(3),
    INTERCOM(4),
    TACAN(5),
    AWLS(6),
    RSC(7),
    ACNIP(8),
    DECS(9),
    ADC(10),
    MSC(11),
    NAV_INS(12),
    VREST(13),
    NAVFLIR(14),
    TGTPOD(15),
    DMT(16),
    RWR(17),
    RWRCONTROL(18),
    FLIGHTINSTRUMENTS(19),
    EDP(20),
    FQIS(21),
    HUDCONTROL(22),
    UFCCONTROL(23),
    ODUCONTROL(24),
    MPCD_LEFT(25),
    MPCD_RIGHT(26),
    FLIGHTCONTROLS(27),
    SMC(28),
    EWS(29),
    CBTSYST(30),
    TVWPNS(31),
    LTEXT(32),
    LTINT(33),
    LTWCA(34),
    ECS(35),
    HYDRAULIC(36),
    NETWORKINTERFACE(37),
    ANVIS9(38);

    private final int mCode;

    AV8BNA_Devices(int pCode) {
        mCode = pCode;
    }

    public int getCode() {
        return mCode;
    }
}
