package com.soldis.yourthaitea;

/**
 * Created by hiantohendry on 2/1/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.soldis.yourthaitea.model.Tbl_GPS_Logs;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.Tbl_List_Sales_Motoris;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_Presence;

import java.util.Locale;

public class SessionManager {

    public static final String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    private static final String EMPTY_STRING = "";

    // Shared key name
    public static final String GCM_REG_ID = "gcm_registration_id";
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String LANGUAGE = "language";
    public static final String USER_ACCOUNT = "user_account";
    public static final String LIST_MOTORIS = "list_motoris";
    public static final String LIST_SALES_MOTORIS = "list_sales_motoris";
    public static final String LIST_JOURNEY = "list_journey";
    public static final String LIST_PRESENCE= "list_presence";
    public static final String DOMAIN_URL= "domain_url";
    private final String SESSION_PREFERENCE = "session_pref";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(SESSION_PREFERENCE, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Clear all preference data
     */
    public void clearAllPreference() {
        editor.clear();
        editor.apply();
    }

    /**
     * Store string data to preferences
     *
     * @param key
     * @param value
     */
    public void putStringData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get string data from preference
     *
     * @param key
     * @return
     */
    public String getStringData(String key) {
        return pref.getString(key, EMPTY_STRING);
    }


    /**
     * Remove string data from
     *
     * @param key
     */
    public void removeStringData(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * Put Boolean data to preference
     *
     * @param key
     * @param value
     */
    public void putBooleanData(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get Boolean data from preference
     * <br>Default value : FALSE
     *
     * @param key
     * @return
     */
    public Boolean getBooleanData(String key) {
        return pref.getBoolean(key, Boolean.FALSE);
    }

    /**
     * Put Integer data to preference
     *
     * @param key
     * @param value
     */
    public void putIntegerData(String key, Integer value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get Integer data from preference
     * <br>Default value : 0
     *
     * @param key
     * @return
     */
    public Integer getIntegerData(String key) {
        return pref.getInt(key, 0);
    }


    /**
     * Put Float data to preference
     *
     * @param key
     * @param value
     */
    public void putFloatData(String key, Float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Get Float data from preference
     * <br>Default value : 0
     *
     * @param key
     * @return
     */
    public Float getFloatData(String key) {
        return pref.getFloat(key, 0);
    }

    /**
     * Remove data from preference
     *
     * @param key
     */
    public void removeData(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * Put list of object to shared preference
     * <br>list object must be converted to json string
     */


    /**
     * Get gcm key
     */
    public String getGcmKey() {
        return getStringData(GCM_REG_ID);
    }

    public void setGcmKey(String GcmKey) {
        putStringData(GCM_REG_ID, GcmKey);
    }


    public boolean isUserLogon() {
        String userSession = getStringData(SessionManager.USER_ACCOUNT);
        if (userSession.equals("")) {
            return false;
        }

        return true;
    }


    public void setLanguage(String language) {

        putStringData(LANGUAGE, language);
    }

    public String getLanguage() {
        return getStringData(LANGUAGE);
    }

    public void setToken(String token) {
        putStringData(TOKEN, token);
    }

    public String getToken() {
        return getStringData(TOKEN);
    }

    public void setRefreshToken(String refreshToken) {
        putStringData(REFRESH_TOKEN, refreshToken);
    }

    public String getRefreshToken() {
        return getStringData(REFRESH_TOKEN);
    }

    public void setLocale(String lang) {
        Locale myLocale;
        myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public void setUserAccount(Tbl_Karyawan userData) {
        if (userData != null) {
            String data = new Gson().toJson(userData);
            putStringData(USER_ACCOUNT, data);
        } else {
            putStringData(USER_ACCOUNT, "");
        }
    }

    public void setListMotoris(Tbl_List_Motoris listMotoris) {
        if (listMotoris != null) {
            String data = new Gson().toJson(listMotoris);
            putStringData(LIST_MOTORIS, data);
        } else {
            putStringData(LIST_MOTORIS, "");
        }
    }

    public void setListPresence(Tbl_Presence listSalesMotoris) {
        if (listSalesMotoris != null) {
            String data = new Gson().toJson(listSalesMotoris);
            putStringData(LIST_PRESENCE, data);
        } else {
            putStringData(LIST_PRESENCE, "");
        }
    }

    public Tbl_Presence getListPresence() {
        return new Gson().fromJson(getStringData(LIST_PRESENCE), Tbl_Presence.class);
    }

    public void setListSalesMotoris(Tbl_List_Sales_Motoris listSalesMotoris) {
        if (listSalesMotoris != null) {
            String data = new Gson().toJson(listSalesMotoris);
            putStringData(LIST_SALES_MOTORIS, data);
        } else {
            putStringData(LIST_SALES_MOTORIS, "");
        }
    }

    public void setListJourney(Tbl_GPS_Logs listJourney) {
        if (listJourney != null) {
            String data = new Gson().toJson(listJourney);
            putStringData(LIST_JOURNEY, data);
        } else {
            putStringData(LIST_JOURNEY, "");
        }
    }

    public Tbl_Karyawan getUserProfile() {
        return new Gson().fromJson(getStringData(USER_ACCOUNT), Tbl_Karyawan.class);
    }

    public Tbl_List_Motoris getListMotoris() {
        return new Gson().fromJson(getStringData(LIST_MOTORIS), Tbl_List_Motoris.class);
    }

    public Tbl_List_Sales_Motoris getListSalesMotoris() {
        return new Gson().fromJson(getStringData(LIST_SALES_MOTORIS), Tbl_List_Sales_Motoris.class);
    }

    public Tbl_GPS_Logs getListJourney() {
        return new Gson().fromJson(getStringData(LIST_JOURNEY), Tbl_GPS_Logs.class);
    }
}

