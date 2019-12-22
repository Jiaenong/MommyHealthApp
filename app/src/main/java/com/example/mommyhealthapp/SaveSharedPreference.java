package com.example.mommyhealthapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_ID = "id";
    static final String PREF_CHECK_LOGIN = "logged";
    static final String USER_TYPE = "user";

    static SharedPreferences getSharedPreference(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, String user)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USER_TYPE, user);
        editor.commit();
    }

    public static String getUser(Context context)
    {
        return getSharedPreference(context).getString(USER_TYPE,"");
    }

    public static void setID(Context context, String id)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREF_USER_ID,id);
        editor.commit();
    }

    public static String getID(Context context)
    {
        return getSharedPreference(context).getString(PREF_USER_ID,"");
    }


    public static void setCheckLogin(Context context, Boolean login)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(PREF_CHECK_LOGIN,login);
        editor.commit();
    }

    public static Boolean getCheckLogin(Context context)
    {
        return getSharedPreference(context).getBoolean(PREF_CHECK_LOGIN,false);
    }

    public static void clearUser(Context context)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.clear();
        editor.commit();
    }

}


