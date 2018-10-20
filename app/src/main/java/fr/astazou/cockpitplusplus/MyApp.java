package fr.astazou.cockpitplusplus;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by astazou on 23/04/2017.
 *
 */
public class MyApp extends Application{

    public static String LUA_VERSION = "4";

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/hemi_head_bd_it.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
