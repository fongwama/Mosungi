package com.fongwama.mosungi.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by STEVEN on 06/12/2016.
 */

public class MyPreferences {

    private static final String LAST_DATE_VALUE = "mosungi.pref.date";

    public void manageSystemDate(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        long lastDate;


        lastDate =  pref.getLong(LAST_DATE_VALUE, 0);
        if(lastDate>0){

            //Compare the current time with the last
            //If the date is advancing ? no pb : send a notification to the user
            Calendar cal = Calendar.getInstance();
            if(cal.getTimeInMillis() > lastDate){
                //DO nothing
            }
            else {
                //TODO : Notify the user that the Date might have changed unwillingly

            }
        }
        else
        {
            //add the curent time in millis
            Calendar cal = Calendar.getInstance();

            SharedPreferences.Editor editor = pref.edit();
            editor.putLong(LAST_DATE_VALUE, cal.getTimeInMillis());
            editor.commit();
        }

    }
}
