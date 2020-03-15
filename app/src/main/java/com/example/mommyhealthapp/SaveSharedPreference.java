package com.example.mommyhealthapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_ID = "id";
    static final String PREF_CHECK_LOGIN = "logged";
    static final String USER_TYPE = "user";
    static final String EARLY_TEST = "earlyTest";
    static final String PREVIOUS_PREGNANT = "previousPregnant";
    static final String MUMMY_ID = "mummyId";
    static final String HEALTHINFO_ID = "healthInfoId";
    static final String ALARM_REQUEST_CODE = "alarmRequestCode";
    static final String REMINDER_REQUEST_CODE = "reminderRequestCode";

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

    public static void setReminderRequestCode(Context context, int reminderRequestCode)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(REMINDER_REQUEST_CODE, reminderRequestCode);
        editor.commit();
    }

    public static int getReminderRequestCode(Context context)
    {
        return getSharedPreference(context).getInt(REMINDER_REQUEST_CODE,0);
    }

    public static void setAlarmRequestCode(Context context, int alarmRequestCode)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(ALARM_REQUEST_CODE, alarmRequestCode);
        editor.commit();
    }

    public static int getAlarmRequestCode(Context context)
    {
        return getSharedPreference(context).getInt(ALARM_REQUEST_CODE,0);
    }

    public static void setHealthInfoId(Context context, String healthInfoId)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(HEALTHINFO_ID, healthInfoId);
        editor.commit();
    }

    public static String getHealthInfoId(Context context)
    {
        return getSharedPreference(context).getString(HEALTHINFO_ID,"");
    }

    public static void setMummyId(Context context, String mummyId)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(MUMMY_ID, mummyId);
        editor.commit();
    }

    public static String getMummyId(Context context)
    {
        return getSharedPreference(context).getString(MUMMY_ID,"");
    }

    public static void setPreviousPregnant(Context context, String previousPregnant)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREVIOUS_PREGNANT, previousPregnant);
        editor.commit();
    }

    public static String getPreviousPregnant(Context context)
    {
        return getSharedPreference(context).getString(PREVIOUS_PREGNANT,"");
    }

    public static void setEarlyTest(Context context, String earlyTest)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(EARLY_TEST, earlyTest);
        editor.commit();
    }

    public static String getEarlyTest(Context context)
    {
        return getSharedPreference(context).getString(EARLY_TEST,"");
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


