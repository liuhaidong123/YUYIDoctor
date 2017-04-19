package com.doctor.yuyi.MyUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by liuhaidong on 2017/4/18.
 */

public class SharedPreferencesUtils {
    public static SharedPreferences sharedPreferences=null;
    public static SharedPreferencesUtils sharedPre = null;
    public static  SharedPreferences.Editor editor;
    public static SharedPreferencesUtils getSharedPreferencesUtils(Context context) {
        if (sharedPre == null) {
            sharedPre = new SharedPreferencesUtils();

        }
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            editor=sharedPreferences.edit();
        }
        return sharedPre;
    }

    private SharedPreferencesUtils() {

    }


    public void setIsnetwork(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }


    public boolean getIsnewwork(String key) {
        boolean b = sharedPreferences.getBoolean(key, true);
        return b;
    }
}
