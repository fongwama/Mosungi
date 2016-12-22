package com.fongwama.mosungi.background;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.CategoriePatient;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.activity.AddAgendaActivity;
import com.fongwama.mosungi.ui.activity.MainActivity;

public class AlarmService extends IntentService {

    private NotificationManager notifManager;
    public AlarmService() {
        super("MosungiAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            MyDbHelper helper = new MyDbHelper(this);

            int idAlarm = intent.getIntExtra(MyFunctions.EXTRA_ALARM_DATABASE_ID, 0);
            AgendaALarm alarm = helper.getAlarmById(idAlarm+"");

            Log.e("state in intent", intent.getBooleanExtra("state", false)+"");

                //on desactive l'alarm
                MyFunctions.manageAlarm(this, alarm, false);
                Log.e("____ is service", "true");

            //Cas de l'alarm planifié pour une categorie(un group)
            if(alarm.getIdPatient() == 0){

                CategoriePatient cat = helper.getCategorieById(alarm.getIdCategorie()+"");

                //Recuperation des numeros

                String numbers = helper.getCategorieNumbers(alarm.getIdCategorie()+"");

                //Notification Envoi
                sendNotification(alarm, cat.getNom(),numbers.split(",").length );

                //Envoi
                sendSmsToCategorie(alarm.getMessage(), numbers);
            }

            else  //Cas de l'alarm planifié pour un patient
            {

                Patient patient = helper.getPatientById(alarm.getIdPatient()+"");

                Log.e("____ pat serv", patient.getPrenom());
                Log.e("____ pat serv", patient.getTelephone());
                Log.e("____ pat serv", alarm.getMessage());

                //Notification Envoi
                sendNotification(alarm, patient.getNom()+" "+patient.getPrenom(), Integer.valueOf(patient.getIdPatient()));

                //Envoi
                sendSmsToPatient(alarm.getMessage(), patient.getTelephone());
            }
    }

    private void sendNotification(AgendaALarm alarm,String nomDestinateur,int nbrNumeros) {

        notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, AddAgendaActivity.class);
        intent.putExtra(MainActivity.EXTRA_EDIT_AGENDA_ALARM, alarm);

        PendingIntent notifIntent = PendingIntent.getActivity(this, 0,intent, 0);

        NotificationCompat.Builder alarmNotifBuilder = new NotificationCompat.Builder(this);

        alarmNotifBuilder.setContentTitle("Alarm "+getResources().getString(R.string.app_name));
        alarmNotifBuilder.setSmallIcon(R.mipmap.ic_launcher);

        alarmNotifBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Envoi de "+nbrNumeros+" SMS à "+ nomDestinateur));
        alarmNotifBuilder.setContentText(alarm.getMessage());

        alarmNotifBuilder.setContentIntent(notifIntent);
        alarmNotifBuilder.setPriority(Notification.PRIORITY_MIN);
        notifManager.notify(alarm.getId(), alarmNotifBuilder.build());

        Log.i("Alarm Service", "Notification envoyée.");
    }

    private void sendSmsToPatient(String msg, String number) {

            SmsManager.getDefault().sendTextMessage(number, null, msg, null, null);
    }

    private void sendSmsToCategorie(String msg, String numbers) {
        SmsManager.getDefault().sendTextMessage(numbers, null, msg, null, null);
    }
}
