package com.fongwama.mosungi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.CategoriePatient;
import com.fongwama.mosungi.model.Patient;

public class MyDbHelper extends SQLiteOpenHelper{

    public static final String DB_NAME      = "mosungi.db";
    public static final int    DB_VERSION   = 1;

    SQLiteDatabase db;

    public MyDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String[] COLUMN_PATIENT = new String[]{
            MyContracts.TablePatient._ID,
            MyContracts.TablePatient.NOM,
            MyContracts.TablePatient.PRENOM,
            MyContracts.TablePatient.SEXE,
            MyContracts.TablePatient.DATE,
            MyContracts.TablePatient.TELEPHONE,
            MyContracts.TablePatient.CAS,
            MyContracts.TablePatient.CATEGORIE,
    };

    public static final String[] COLUMN_CATEGORIE = new String[]{
            MyContracts.TableCategorie._ID,
            MyContracts.TableCategorie.TITRE,
            MyContracts.TableCategorie.DESCRIPTION
    };

    public static final String[] COLUMN_AGENDA = new String[]{
            MyContracts.TableAgenda._ID,
            MyContracts.TableAgenda.TITRE,
            MyContracts.TableAgenda.MESSAGE,
            MyContracts.TableAgenda.MESSAGE_NUMBERS,
            MyContracts.TableAgenda.DATE_MILLIS_SET,
            MyContracts.TableAgenda.DATE_MILLIS_TARGET,
            MyContracts.TableAgenda.DATE_SET,
            MyContracts.TableAgenda.DATE_TARGET,
            MyContracts.TableAgenda.ALARM_STATE,
            MyContracts.TableAgenda.ALARM_REPEAT_COUNT,
            MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL,
            MyContracts.TableAgenda.ALARM_MUSIC_PATH,
            MyContracts.TableAgenda.ALARM_VOLUME
    };



    final String SQL_CATEGORIE = "CREATE TABLE "+
            MyContracts.TableCategorie.TABLE_NAME+" ("+
            MyContracts.TableCategorie._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MyContracts.TableCategorie.TITRE+" TEXT NOT NULL,"+
            MyContracts.TableCategorie.DESCRIPTION+" TEXT);";
    final String SQL_CATEGORIE_DROP = "DROP TABLE IF EXISTS "+MyContracts.TableCategorie.TABLE_NAME;

    final String SQL_PATIENT = "CREATE TABLE "+
            MyContracts.TablePatient.TABLE_NAME+" ("+
            MyContracts.TablePatient._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MyContracts.TablePatient.NOM+" TEXT NOT NULL, "+
            MyContracts.TablePatient.PRENOM+" TEXT NOT NULL, "+
            MyContracts.TablePatient.SEXE+" TEXT NOT NULL, "+
            MyContracts.TablePatient.DATE+" TEXT NOT NULL, "+
            MyContracts.TablePatient.TELEPHONE+" TEXT NOT NULL, "+
            MyContracts.TablePatient.CAS+" TEXT NOT NULL,"+
            MyContracts.TablePatient.CATEGORIE+" TEXT);";
    final String SQL_PATIENT_DROP = "DROP TABLE IF EXISTS "+MyContracts.TablePatient.TABLE_NAME;

    final String SQL_AGENDA = "CREATE TABLE "+
            MyContracts.TableAgenda.TABLE_NAME+" ("+
            MyContracts.TableAgenda._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MyContracts.TableAgenda.TITRE+" TEXT, "+
            MyContracts.TableAgenda.MESSAGE+" TEXT, "+
            MyContracts.TableAgenda.DATE_MILLIS_SET+" TEXT NOT NULL, "+
            MyContracts.TableAgenda.DATE_MILLIS_TARGET+" TEXT NOT NULL,"+
            MyContracts.TableAgenda.DATE_SET+" TEXT, "+
            MyContracts.TableAgenda.DATE_TARGET+" TEXT, "+
            MyContracts.TableAgenda.MESSAGE_NUMBERS+" TEXT,"+
            MyContracts.TableAgenda.ALARM_MUSIC_PATH+" TEXT, "+
            MyContracts.TableAgenda.ALARM_STATE+" BOOLEAN, "+
            MyContracts.TableAgenda.ALARM_VOLUME+" INTEGER, "+
            MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL+" INTEGER, "+
            MyContracts.TableAgenda.ALARM_REPEAT_COUNT+" INTEGER);";
    final String SQL_AGENDA_DROP = "DROP TABLE IF EXISTS "+MyContracts.TableAgenda.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /* TODO *** CREATION des TABLES *** */
        //On exécute la requete de création de la table patient
        db.execSQL(SQL_CATEGORIE);
        db.execSQL(SQL_PATIENT);
        db.execSQL(SQL_AGENDA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_PATIENT_DROP);
        db.execSQL(SQL_AGENDA_DROP);
        db.execSQL(SQL_CATEGORIE_DROP);

        onCreate(db);
    }

    // TODO --------- INSERT METHODS ---------------------------------
    public long insertCategorie(CategoriePatient categorie, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableCategorie.TITRE, categorie.getNom());
        values.put(MyContracts.TableCategorie.DESCRIPTION, categorie.getDescription());

        long success = -1;
        try {
            success = db.insert(MyContracts.TableCategorie.TABLE_NAME, null, values);
            Log.i(" tryed cat ", " insertion ok");
        }
        catch (Exception ex){
            Log.i(" error cat "," insertion catched");
        }
        db.close();
        return success;
    }

    public long insertPatient(Patient patient, Context context){

        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.NOM,            patient.getNom());
        values.put(MyContracts.TablePatient.PRENOM,         patient.getPrenom());
        values.put(MyContracts.TablePatient.SEXE,           patient.getSexe());
        values.put(MyContracts.TablePatient.DATE,           patient.getDateNaissance());
        values.put(MyContracts.TablePatient.TELEPHONE,      patient.getTelephone());
        values.put(MyContracts.TablePatient.CAS,            patient.getCasPatient());

        long success = -1;
        try {
            success = db.insert(MyContracts.TablePatient.TABLE_NAME, null, values);
            Log.i(" tryed patient ", " insertion ok");
        }
        catch (Exception ex){
            Log.i(" error patient "," insertion catched");
        }
        db.close();
        return success;
    }

    public long insertAlarm(AgendaALarm alarm, Context context){

        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableAgenda.TITRE,    alarm.getTitre());
        values.put(MyContracts.TableAgenda.MESSAGE,    alarm.getMessage());
        values.put(MyContracts.TableAgenda.MESSAGE_NUMBERS,    alarm.getMessageNumbers());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_SET,    alarm.getDateMillisNow());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_TARGET, alarm.getDateMillisWakeUp());
        values.put(MyContracts.TableAgenda.DATE_SET,    alarm.getDateHumanNow());
        values.put(MyContracts.TableAgenda.DATE_TARGET,    alarm.getDateHumanWakeUp());
        values.put(MyContracts.TableAgenda.ALARM_MUSIC_PATH,    alarm.getMusicPath());
        values.put(MyContracts.TableAgenda.ALARM_STATE,    alarm.isState());
        values.put(MyContracts.TableAgenda.ALARM_VOLUME,    alarm.getVolumeLevel());
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_COUNT,    alarm.getRepeatCount()+"");
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL,    alarm.getRepeatTimeInterval());
        long success = -1;
        try {
            success = db.insert(MyContracts.TableAgenda.TABLE_NAME, null, values);
            Log.e(" tryed Alarm ", " insertion ok");
        }
        catch (Exception ex){
            Log.e(" error Alarm "," insertion catched");
        }
        db.close();
        return success;
    }




    // TODO --------- SELECT METHODS ---------------------------------

    public List<Patient> getAllPatient()
    {
        SQLiteDatabase db   = getReadableDatabase();
        Cursor cursor       = db.query(MyContracts.TablePatient.TABLE_NAME, COLUMN_PATIENT, null, null, null, null,MyContracts.TablePatient.NOM+" ASC");
        List<Patient> listPatients = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do
            {
                Patient patient = new Patient();

                patient.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TablePatient._ID))+"");
                patient.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.NOM)));
                patient.setPrenom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.PRENOM)));
                patient.setDateNaissance(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.DATE)));
                patient.setTelephone(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.TELEPHONE)));
                patient.setSexe(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.SEXE)));
                patient.setCasPatient(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CAS)));

                listPatients.add(patient);

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return listPatients;
    }

    public List<CategoriePatient> getAllCategorie(){
        SQLiteDatabase db   = getReadableDatabase();
        Cursor cursor       = db.query(MyContracts.TableCategorie.TABLE_NAME, COLUMN_CATEGORIE, null, null, null, null, null);
        List<CategoriePatient> listCategorie = new ArrayList<>();

        if(cursor==null)
            return null;

        if (cursor.moveToFirst())
        {
            do
            {
                CategoriePatient newCat = new CategoriePatient();

                newCat.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableCategorie._ID))+"");
                newCat.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.TITRE)));
                newCat.setDescription(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.DESCRIPTION)));

                listCategorie.add(newCat);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return listCategorie;
    }

    public List<AgendaALarm> getAllAlarm()
    {
        SQLiteDatabase db   = getReadableDatabase();
        Cursor cursor       = db.query(MyContracts.TableAgenda.TABLE_NAME, COLUMN_AGENDA, null, null, null, null, null);
        List<AgendaALarm> listAlarms = new ArrayList<>();

        if(cursor==null)
            return null;

        if (cursor.moveToFirst())
        {
            do
            {
                AgendaALarm alarm = new AgendaALarm();

                alarm.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda._ID)));
                alarm.setTitre(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.TITRE)));
                alarm.setMessage(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE)));
                alarm.setMessageNumbers(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE_NUMBERS)));

                alarm.setDateMillisNow(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_SET)));
                alarm.setDateMillisWakeUp(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_TARGET)));
                alarm.setDateHumanNow(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_SET)));
                alarm.setDateHumanWakeUp(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_TARGET)));

                alarm.setRepeatCount((char)cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_COUNT)));
                alarm.setRepeatTimeInterval(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL)));
                alarm.setMusicPath(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_MUSIC_PATH)));
                alarm.setVolumeLevel(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_VOLUME)));

                listAlarms.add(alarm);

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return listAlarms;
    }

    // TODO --------- DELETE METHODS ---------------------------------

    public void deletePatient(String idPatient, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
        db.delete(MyContracts.TablePatient.TABLE_NAME, MyContracts.TablePatient._ID,new String[]{ idPatient });
        db.close();
    }

    void deleteAgendaAlarm(String idAgenda, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
        db.delete(MyContracts.TableAgenda.TABLE_NAME, MyContracts.TableAgenda._ID,new String[]{ idAgenda });
        db.close();
    }

    public void deleteCategorie(String idCategorie, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
        db.delete(MyContracts.TableCategorie.TABLE_NAME, MyContracts.TableCategorie._ID,new String[]{ idCategorie });
        db.close();
    }

    // TODO --------- UPDATE METHODS ---------------------------------
    public void updatePatient(Patient patient, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.NOM,            patient.getNom());
        values.put(MyContracts.TablePatient.PRENOM,         patient.getPrenom());
        values.put(MyContracts.TablePatient.SEXE,           patient.getSexe());
        values.put(MyContracts.TablePatient.DATE,           patient.getDateNaissance());
        values.put(MyContracts.TablePatient.TELEPHONE,      patient.getTelephone());
        values.put(MyContracts.TablePatient.CAS,            patient.getCasPatient());
        values.put(MyContracts.TablePatient.CATEGORIE,      patient.getCategories());

        db.update(MyContracts.TablePatient.TABLE_NAME, values, MyContracts.TablePatient._ID, new String[]{patient.getIdPatient()});
        db.close();
    }

    public void updateAlarm(AgendaALarm alarm, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableAgenda.TITRE,      alarm.getTitre());
        values.put(MyContracts.TableAgenda.MESSAGE,    alarm.getMessage());
        values.put(MyContracts.TableAgenda.MESSAGE_NUMBERS,    alarm.getMessageNumbers());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_SET,    alarm.getDateMillisNow());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_TARGET, alarm.getDateMillisWakeUp());
        values.put(MyContracts.TableAgenda.DATE_SET,        alarm.getDateHumanNow());
        values.put(MyContracts.TableAgenda.DATE_TARGET,     alarm.getDateHumanWakeUp());
        values.put(MyContracts.TableAgenda.ALARM_MUSIC_PATH,alarm.getMusicPath());
        values.put(MyContracts.TableAgenda.ALARM_STATE,     alarm.isState());
        values.put(MyContracts.TableAgenda.ALARM_VOLUME,    alarm.getVolumeLevel());
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_COUNT,    alarm.getRepeatCount()+"");
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL,    alarm.getRepeatTimeInterval());

        db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID, new String[]{alarm.getId()+""});
        db.close();
    }

    public void updateCategorie(CategoriePatient categorie, Context context){
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableCategorie.TITRE, categorie.getNom());
        values.put(MyContracts.TableCategorie.DESCRIPTION, categorie.getDescription());

        db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID, new String[]{categorie.getId()});
        db.close();
    }

}
