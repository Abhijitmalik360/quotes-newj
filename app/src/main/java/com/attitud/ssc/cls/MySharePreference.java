package com.attitud.ssc.cls;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreference {

    public static MySharePreference mySharePreference;
    private SharedPreferences sharedPreferences;

    private final  String DARK_MODE = "dark_mode";

    private MySharePreference (Context context){
        sharedPreferences = context.getSharedPreferences("DailyQuoteDb",Context.MODE_PRIVATE);
    }

    public static MySharePreference getInstance(Context context){
        if (mySharePreference==null){
            mySharePreference = new MySharePreference(context);
        }
        return mySharePreference;
    }


    public void saveDarkMode(boolean enable){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(DARK_MODE,enable);
        editor.apply();
    }

    public boolean isDarkMode(){
        return sharedPreferences.getBoolean(DARK_MODE,false);
    }



}
