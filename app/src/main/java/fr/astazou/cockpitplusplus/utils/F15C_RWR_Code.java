package fr.astazou.cockpitplusplus.utils;

/**
 * source for these codes: https://forums.eagle.ru/showthread.php?t=63162
 * Created by astazou on 07/05/2017.
 */

public class F15C_RWR_Code {

    public static String getCode(String pName) {
        String result = "U";
        switch (pName) {
            //Planes
            case ("e-3a"):
                result = "E3";
                break;
            case ("mig-23ml"):
                result = "23";
                break;
            case ("mig-29c"):
            case ("mig-29"):
            case ("su-33"):
            case ("su-27"):
                result = "29";
                break;
            case ("F-5E"):
            case ("F-5E-3"):
                result = "F5";
                break;
            case ("C-101CC"):
                result = "U";
                break;
            case ("a-50"):
                result = "50";
                break;
            case ("F-86F"):
                result = "U";
                break;
            case ("M-2000C"):
            case ("mirage 2000c"):
                result = "M2";
                break;
            case ("AJS37"):
                result = "U";
                break;
            case ("MiG-21Bis"):
                result = "21";
                break;
            case ("mig-25p"):
                result = "25";
                break;
            case ("mig-31"):
                result = "31";
                break;
            case ("su-34"):
                result = "34";
                break;
            case ("su-30"):
                result = "30";
                break;
            case ("e-2c hawkeye"):
                result = "E2";
                break;
            case ("f-14a"):
                result = "14";
                break;
            case ("F-15E"):
            case ("F-15C"):
                result = "15";
                break;
            case ("F-16c"):
            case ("F-16A"):
                result = "16";
                break;
            case ("f-18c"):
                result = "18";
                break;

            //Boats
            case ("FFG-7 Oliver H. Perry class"):
                result = "49";
                break;
            case ("Albatros (Grisha-5)"):
                result = "HP";
                break;
            case ("CVN-70 Vinson"):
                result = "48";
                break;
            case ("SG-47 Ticonderoga class"):
                result = "AE";
                break;
            case ("TAKR Kuznetsov"):
                result = "SW";
                break;
            case ("Piotr Velikiy"):
                result = "HN";
                break;
            case ("Moscow"):
                result = "T2";
                break;
            case ("Rezky (Krivak-2)"):
            case ("Neustrashimy"):
                result = "TP";
                break;
            case ("Molniya (Tarantul-3)"):
                result = "PS";
                break;

            //Ground
            case ("hawk sr"):
                result = "HA";
                break;
            case ("patriot str"):
                result = "PT";
                break;
            case ("1l13 ewr station"):
                result = "EW";
                break;
            case ("55g6 ewr station"):
            case ("Dog Ear Radar"):
                result = "EW";
                break;
            case ("shilka zsu-23-4"):
                result = "A";
                break;

            //Else
            case ("UK"):
                result = "U";
                break;
            default:
                result = "U";
                //Log.d("F15C_RWR_Code",pName);
                break;
        }
        return result;
    }
}
