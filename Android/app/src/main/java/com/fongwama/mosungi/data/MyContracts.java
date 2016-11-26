package com.fongwama.mosungi.data;

import android.provider.BaseColumns;

/**
 * Created by Karl on 20/07/2016.
 */
public class MyContracts
{
    public static class TablePatient implements BaseColumns {

        public static final String TABLE_NAME   = "patient";
        public static final String NOM          = "nom_patient";
        public static final String PRENOM       = "prenom_patient";
        public static final String SEXE         = "sexe_patient";
        public static final String DATE         = "date_patient";
        public static final String TELEPHONE    = "telephone_patient";
        public static final String CAS          = "cas_patient";
        public static final String CATEGORIE    = "groupe_patient";
    }

    public static class TableAgenda implements BaseColumns {

        public static final String TABLE_NAME = "agenda";

        public static final String TITRE      = "libele_agenda";
        public static final String MESSAGE    = "sms_agenda";

        //This represent the IDs of patients to whom a message will be sent.
        public static final String MESSAGE_NUMBERS = "sms_destinations_agenda";

        //The date of the moment the Alarm(Agenda field) is set
        public static final String DATE_MILLIS_SET = "date_unix_start_agenda";

        //The date of WAKE-UP (déclenchement de l'alarme).
        public static final String DATE_MILLIS_TARGET  = "date_unix_target_agenda";

        /*
        En - Number of alarm repeats (specialy for the DOCTOR) if the user(Doctor) doesn't stop the Alarm.
             Why? ... The  agenda has two types of Alarms, the one which will send a SMS to patients
             and the one destined to remember the Doctor a specific TASK.

        Fr - Nombre de fois qu'il faudra repeter l'alarm (pour MEDECIN uniquement) si l'utilisateur n'inter-agit point
           Pourquoi? ...L'agenda a deux types d'alrme, celle qui envoi un texto au(x) patient(s) et
           celle qui Rappelle au Docteur une Tache.
        */
        public static final String ALARM_REPEAT_COUNT  = "alarm_rc_agenda";

        // En: Let the user chose after how many minutes the Alarme has to cme back or not (in MINUTES)
        //(in case he is abscent during the 1st Alarm Ring)
        public static final String ALARM_REPEAT_TIME_INTERVAL  = "alarm_interval_agenda";

        /*En: To avoid multiple julians DATE calculations, it's better to save the Human understandable date once in the DB
              so that the Collections Adapter won't have to process it all time (Performances Optimization)

          Fr: Pour éviter de convertir(calculer) à chaque fois la date UNIX en date Humaine au niveau de l'adaptateur de la collection Agenda ,
          il est preferable de sauvegarder une bonne fois la date Humaine dans la Base de Donnees
          (Optimisation des performances)
        */
        public static final String DATE_SET     = "human_date_set_agenda";
        public static final String DATE_TARGET  = "human_date_target_agenda";

        /*
            En: These two are for the SOUND played by the Alarm (If no sound chosen, the default will be played)

            Fr: Ces deux Champs sont reservé au SON joué par l'alarme (Au cas où l'utilisateur ne specifi rien, le son par defaut sera utilisé)
         */
        public static final String ALARM_MUSIC_PATH  = "alarm_music_agenda";
        public static final String ALARM_VOLUME  = "alarm_vol_agenda";

        // Fr: show Alarm setted or Not
        public static final String ALARM_STATE  = "alarm_state_agenda";

    }


    public static class TableCategorie implements BaseColumns{

        public static final String TABLE_NAME = "categorie";
        public static final String TITRE  = "titre_cat";
        public static final String DESCRIPTION  = "description_cat";
    }
}
