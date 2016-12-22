package com.fongwama.mosungi.functions;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fongwama.mosungi.background.AlarmReceiver;
import com.fongwama.mosungi.model.AgendaALarm;

import java.text.SimpleDateFormat;

import static android.content.Context.ALARM_SERVICE;
import static android.os.Build.VERSION.SDK_INT;

public class MyFunctions {

    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 42;
    public static final String EXTRA_ALARM_DATABASE_ID = "mosungi.alarm.db.id";
    public static final String ACTION_ALARM_MOSUNGI = "mosungi.alarm.action.wakeup";

    //make unix server timestamp readable(by humans) as normal date
    public static String getReadableDate(long timeStamp){
            SimpleDateFormat shortenedDate = new SimpleDateFormat("EEE\ndd\nMMM\nyyyy");
            return shortenedDate.format(timeStamp);
    }

    public static String getReadableLongDate(long timeStamp){
            SimpleDateFormat shortenedDate = new SimpleDateFormat("EEE\ndd\nMMM");
            return shortenedDate.format(timeStamp);
    }

    public static String getReadableShortDate(long timeStamp){
            SimpleDateFormat shortenedDate = new SimpleDateFormat("dd MMM yyyy");
            return shortenedDate.format(timeStamp);
    }

    public static String getReadableDateDay(long timeStamp){
            SimpleDateFormat shortenedDate = new SimpleDateFormat("EEE");
            return shortenedDate.format(timeStamp);
    }

    public static String get24Hour(long timeStamp){
        SimpleDateFormat hour12to24 = new SimpleDateFormat("HH");
        return hour12to24.format(timeStamp);
    }

    public static String getFormatedMinutes(long timeStamp){
        SimpleDateFormat min = new SimpleDateFormat("mm");
        return min.format(timeStamp);
    }

    //Fonctio qui vérifie si ub=n EditText est vide ou pas
    public Boolean checkTextInputEditText(TextInputEditText textInputEditText)
    {
        return textInputEditText.getText().toString().isEmpty() ? false: true;
    }

    public Boolean checkPhoneNumberFormat(TextInputEditText phoneNumberEditText)
    {
        //Vérifie si le numéro a au moins 6 chiffres
        return phoneNumberEditText.getText().toString().length() > 6 ? true : false;
    }

    public static void manageAlarm(Context context,AgendaALarm alarm, boolean newState){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        //WE put the alarm id in the intent, we'll use on wakeUp
        intent.putExtra(EXTRA_ALARM_DATABASE_ID, alarm.getId());
        intent.putExtra("state", alarm.isState());


        //
        intent.setAction(ACTION_ALARM_MOSUNGI);

        //alarm.getId() makes the alarm unique
        if (newState)
        {

            PendingIntent pendingIntentOn = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);

            Log.e("__ m func id set", alarm.getId()+"");
            Log.e("mosungi alarm state", "Alarm ON");
            //we use the wakeup time from AgendaAlarm object above
            //alarm.getDateMillisWakeUp()
            alarmManager.set(AlarmManager.RTC, alarm.getDateMillisWakeUp(), pendingIntentOn);
        }
        else
        {
            PendingIntent pendingIntentOff = PendingIntent.getBroadcast(context, alarm.getId(), intent,PendingIntent.FLAG_CANCEL_CURRENT);

            alarmManager.cancel(pendingIntentOff);
            Log.e("mosungi alarm state", "Alarm OFF");
            Log.e("__ m func id set", alarm.getId()+"");
        }
    }

    public static boolean checkSMSPermissions(Context context){

        if(SDK_INT>= Build.VERSION_CODES.M)
        {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            Log.i("mosungi perssion", "djà accordée");
            return true;
        }
        else //Quand les perssions ne sont pas accordées
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    Manifest.permission.SEND_SMS)) {
                // Montrer à l'utilisateur un Message temporaire expliquant le besoin de la permission
                // d'Envoi-SMS (SEND_SMS)
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                Log.i("mosungi perssion", "explication");
            } else {
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                Log.i("mosungi perssion", "demandée");
            }


            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    Manifest.permission.SEND_SMS))
            {
                Log.i("mosungi perssion", "accordée après démande");
                return true;
            }
            else return false;
        }
    }
    //Le cas des systèmes < MarshMallow
    else return true;
    }
}
