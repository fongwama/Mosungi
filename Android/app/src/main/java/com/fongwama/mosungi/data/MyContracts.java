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

        public static final String DATE_MILLISS  = "date_unix_agenda";
        public static final String DATE          = "date_human_agenda";
        public static final String ALARM_MUSIC_PATH  = "alarm_music_agenda";
        public static final String ALARM_VOLUME  = "alarm_vol_agenda";

    }
}
