package com.fongwama.mosungi.background;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.fongwama.mosungi.functions.MyFunctions;

import static com.fongwama.mosungi.functions.MyFunctions.ACTION_ALARM_MOSUNGI;

/**
 * Created by STEVEN on 06/12/2016.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        
        if(intent.getAction().equals(ACTION_ALARM_MOSUNGI)) {

            //Condition utile
            int idAlarm = intent.getIntExtra(MyFunctions.EXTRA_ALARM_DATABASE_ID, 0);
            if(idAlarm<=0)
                return;

            //Lancer le son de l'alarm(Notification simple)
            //Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

            //this will send a notification message to the user so that he understands that the SMS
            //is being sent
            ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmService.class.getName());

            //the intent here is the same i send, it contains my AgendaAlarm ID
            startWakefulService(context, (intent.setComponent(comp)));

            setResultCode(Activity.RESULT_OK);
        }



    }


}
