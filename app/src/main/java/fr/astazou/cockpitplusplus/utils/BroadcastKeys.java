package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 29/04/2017.
 * All the keys used by broadcast receivers and broadcast senders
 */
public class BroadcastKeys {

    /** General broadcast keys **/
    public static final String ONLINE  = "online";
    public static final String CURRENT_AIRCRAFT = "currentAircraft";
    public static final String OPEN_PLAYSTORE = "open_playstore";
    public static final String UPDATE_LUA = "update_lua";

    /** Broadcast keys for the Mirage 2000C **/
    public static final String M2KC_UR = "M2KC_ur";
    public static final String M2KC_BR = "M2KC_br";
    public static final String M2KC_PCABTN = "M2KC_pcabtn";
    public static final String M2KC_PPA = "M2KC_ppa";
    public static final String M2KC_PPABTN = "M2KC_ppabtn";
    public static final String M2KC_INS_UR = "M2KC_ins_ur";
    public static final String M2KC_INS_BR = "M2KC_ins_br";
    public static final String M2KC_INS_DATA = "M2KC_ins_data";
    public static final String M2KC_INS_KNOBS = "M2KC_ins_knobs";

    /** Broadcast keys for the F-15C Eagle **/
    public static final String F15C_RWR = "F15C_rwr";

    /** Broadcast keys for the UH-1H Huey **/
    public static final String UH1H_ARMAMENT = "UH1H_armament_panel";

    /** Broadcast keys for the AV-8B n/a Harrier **/
    public static final String AV8BNA_NOZZLE = "AV8BNA_nozzle";
}