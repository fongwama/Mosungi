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

    }
}
