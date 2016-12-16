package com.fongwama.mosungi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.CategoriePatient;
import com.fongwama.mosungi.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mosungi.db";
    public static final int DB_VERSION = 1;

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
            MyContracts.TableAgenda.ALARM_VOLUME,
            MyContracts.TableAgenda.ID_PATIENT,
            MyContracts.TableAgenda.ID_CATEGORIE
    };


    final String SQL_CATEGORIE = "CREATE TABLE " +
            MyContracts.TableCategorie.TABLE_NAME + " (" +
            MyContracts.TableCategorie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyContracts.TableCategorie.TITRE + " TEXT NOT NULL," +
            MyContracts.TableCategorie.DESCRIPTION + " TEXT);";
    final String SQL_CATEGORIE_DROP = "DROP TABLE IF EXISTS " + MyContracts.TableCategorie.TABLE_NAME;

    final String SQL_PATIENT = "CREATE TABLE " +
            MyContracts.TablePatient.TABLE_NAME + " (" +
            MyContracts.TablePatient._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyContracts.TablePatient.NOM + " TEXT NOT NULL, " +
            MyContracts.TablePatient.PRENOM + " TEXT NOT NULL, " +
            MyContracts.TablePatient.SEXE + " TEXT NOT NULL, " +
            MyContracts.TablePatient.DATE + " TEXT NOT NULL, " +
            MyContracts.TablePatient.TELEPHONE + " TEXT NOT NULL, " +
            MyContracts.TablePatient.CAS + " TEXT NOT NULL, " +
            MyContracts.TablePatient.CATEGORIE + " TEXT);";
    final String SQL_PATIENT_DROP = "DROP TABLE IF EXISTS " + MyContracts.TablePatient.TABLE_NAME;

    final String SQL_AGENDA = "CREATE TABLE " +
            MyContracts.TableAgenda.TABLE_NAME + " (" +
            MyContracts.TableAgenda._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyContracts.TableAgenda.TITRE + " TEXT, " +
            MyContracts.TableAgenda.MESSAGE + " TEXT, " +
            MyContracts.TableAgenda.DATE_MILLIS_SET + " TEXT NOT NULL, " +
            MyContracts.TableAgenda.DATE_MILLIS_TARGET + " TEXT NOT NULL," +
            MyContracts.TableAgenda.DATE_SET + " TEXT, " +
            MyContracts.TableAgenda.DATE_TARGET + " TEXT, " +
            MyContracts.TableAgenda.MESSAGE_NUMBERS + " TEXT," +
            MyContracts.TableAgenda.ALARM_MUSIC_PATH + " TEXT, " +
            MyContracts.TableAgenda.ALARM_STATE + " BOOLEAN, " +
            MyContracts.TableAgenda.ALARM_VOLUME + " INTEGER, " +
            MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL + " INTEGER, " +
            MyContracts.TableAgenda.ALARM_REPEAT_COUNT + " INTEGER," +
            MyContracts.TableAgenda.ID_PATIENT + " INTEGER," +
            MyContracts.TableAgenda.ID_CATEGORIE + " INTEGER);";
    final String SQL_AGENDA_DROP = "DROP TABLE IF EXISTS " + MyContracts.TableAgenda.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
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
    public long insertCategorie(CategoriePatient categorie, Context context) {
        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableCategorie.TITRE, categorie.getNom());
        values.put(MyContracts.TableCategorie.DESCRIPTION, categorie.getDescription());

        long success = -1;
        try {
            success = db.insert(MyContracts.TableCategorie.TABLE_NAME, null, values);
            Log.i(" tryed cat ", " insertion ok");
        } catch (Exception ex) {
            Log.i(" error cat ", " insertion catched");
        }
        db.close();
        return success;
    }

    public long insertPatient(Patient patient, Context context) {

        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.NOM, patient.getNom());
        values.put(MyContracts.TablePatient.PRENOM, patient.getPrenom());
        values.put(MyContracts.TablePatient.SEXE, patient.getSexe());
        values.put(MyContracts.TablePatient.DATE, patient.getDateNaissance());
        values.put(MyContracts.TablePatient.TELEPHONE, patient.getTelephone());
        values.put(MyContracts.TablePatient.CAS, patient.getCasPatient());

        long success = -1;
        try {
            success = db.insert(MyContracts.TablePatient.TABLE_NAME, null, values);
            Log.i(" tryed patient ", " insertion ok");
        } catch (Exception ex) {
            Log.i(" error patient ", " insertion catched");
        }
        db.close();
        return success;
    }

    public long insertAlarm(AgendaALarm alarm, Context context) {

        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableAgenda.TITRE, alarm.getTitre());
        values.put(MyContracts.TableAgenda.MESSAGE, alarm.getMessage());
        values.put(MyContracts.TableAgenda.MESSAGE_NUMBERS, alarm.getMessageNumbers());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_SET, alarm.getDateMillisNow());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_TARGET, alarm.getDateMillisWakeUp());
        values.put(MyContracts.TableAgenda.DATE_SET, alarm.getDateHumanNow());
        values.put(MyContracts.TableAgenda.DATE_TARGET, alarm.getDateHumanWakeUp());
        values.put(MyContracts.TableAgenda.ALARM_MUSIC_PATH, alarm.getMusicPath());
        values.put(MyContracts.TableAgenda.ALARM_STATE, alarm.isState());
        values.put(MyContracts.TableAgenda.ALARM_VOLUME, alarm.getVolumeLevel());
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_COUNT, alarm.getRepeatCount() + "");
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL, alarm.getRepeatTimeInterval());

        values.put(MyContracts.TableAgenda.ID_PATIENT, alarm.getIdPatient());
        values.put(MyContracts.TableAgenda.ID_CATEGORIE, alarm.getIdCategorie());
        long success = -1;
        try {
            success = db.insert(MyContracts.TableAgenda.TABLE_NAME, null, values);
            Log.e(" tryed Alarm ", " insertion ok");

            if(success>0){
                //On utilise l'iD inseré pour enregistrer l'Alarm auprès du système.
                alarm.setId((int)success);
                MyFunctions.manageAlarm(context, alarm, alarm.isState());
            }

        } catch (Exception ex) {
            Log.e(" error Alarm ", " insertion catched");
        }
        db.close();
        return success;
    }


    // TODO --------- SELECT METHODS ---------------------------------

    public List<Patient> getAllPatient() {
        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TablePatient.TABLE_NAME, COLUMN_PATIENT, null, null, null, null, MyContracts.TablePatient.NOM + " ASC");
        List<Patient> listPatients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();

                patient.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TablePatient._ID)) + "");
                patient.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.NOM)));
                patient.setPrenom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.PRENOM)));
                patient.setDateNaissance(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.DATE)));
                patient.setTelephone(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.TELEPHONE)));
                patient.setSexe(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.SEXE)));
                patient.setCasPatient(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CAS)));

                //TODO --- ic on recupère l'ensemble des categories auxquelles le Patient s'y trouve
                String categories = cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CATEGORIE));
                patient.setListCat(getListCategorie(categories));

                listPatients.add(patient);

            }
            while (cursor.moveToNext());
        }
        db.close();
        return listPatients;
    }

    public List<Patient> getAllPatientByCategorie(String idCat) {
        db = getReadableDatabase();
        // the query argument must consider delimiters (#) "#"+idCat+"#"
        //Why ? in the patient categorie field, there may be many IDs
        //  bacause the patient can belong to as much categories as possible
        String queryArgument = "#" + idCat + "#";

        Cursor cursor = db.query(MyContracts.TablePatient.TABLE_NAME, COLUMN_PATIENT, MyContracts.TablePatient.CATEGORIE + "=?", new String[]{queryArgument}, null, null, null, null);
        List<Patient> listPatients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();

                patient.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TablePatient._ID)) + "");
                patient.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.NOM)));
                patient.setPrenom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.PRENOM)));
                patient.setDateNaissance(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.DATE)));
                patient.setTelephone(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.TELEPHONE)));
                patient.setSexe(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.SEXE)));
                patient.setCasPatient(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CAS)));

                //TODO --- ic on recupère l'ensemble des categories auxquelles le Patient s'y trouve
                String categories = cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CATEGORIE));
                patient.setListCat(getListCategorie(categories));

                listPatients.add(patient);

            }
            while (cursor.moveToNext());
        }
        db.close();
        return listPatients;
    }

    public String getCategorieNumbers(String idCat) {
        db = getReadableDatabase();
        // the query argument must consider delimiters (#) "#"+idCat+"#"
        //Why ? in the patient categorie field, there may be many IDs
        //  bacause the patient can belong to as much categories as possible
        String queryArgument = "#" + idCat + "#";

        Cursor cursor = db.query(
                MyContracts.TablePatient.TABLE_NAME,
                new String[]{MyContracts.TablePatient.TELEPHONE},
                MyContracts.TablePatient.CATEGORIE + "=?",
                new String[]{queryArgument},
                null, null, null, null);
        String numbers = "";

        if (cursor.moveToFirst()) {
            do {
                if (numbers.isEmpty())
                    numbers += cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.TELEPHONE));
                else
                    numbers += "," + cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.TELEPHONE));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return numbers;
    }

    public Patient getPatientById(String idPatient) {
        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TablePatient.TABLE_NAME, COLUMN_PATIENT, MyContracts.TablePatient._ID + "=?", new String[]{idPatient}, null, null, null, null);

        Patient patient = null;
        if (cursor.moveToFirst()) {
            do {
                patient = new Patient();

                patient.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TablePatient._ID)) + "");
                patient.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.NOM)));
                patient.setPrenom(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.PRENOM)));
                patient.setDateNaissance(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.DATE)));
                patient.setTelephone(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.TELEPHONE)));
                patient.setSexe(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.SEXE)));
                patient.setCasPatient(cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CAS)));

                //TODO --- ic on recupère l'ensemble des categories auxquelles le Patient s'y trouve
                String categories = cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CATEGORIE));
                patient.setListCat(getListCategorie(categories));
            }
            while (cursor.moveToNext());
        }
        db.close();
        return patient;
    }


    public int getPatientCount(){
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+MyContracts.TablePatient._ID+" FROM "+MyContracts.TablePatient.TABLE_NAME, null);
        if (cursor == null)
            return 0;

        if (cursor.moveToFirst()) {
            return cursor.getCount();
        }

        return 0;
    }

    public int getAlarmCount(){
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+MyContracts.TableAgenda._ID+" FROM "+MyContracts.TableAgenda.TABLE_NAME, null);
        if (cursor == null)
            return 0;

        if (cursor.moveToFirst())
            return cursor.getCount();

        return 0;
    }

    public CategoriePatient getCategorieById(String idCat) {
        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TableCategorie.TABLE_NAME, COLUMN_CATEGORIE, null, null, null, null, null);
        CategoriePatient cat = null;

        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            do {
                cat = new CategoriePatient();

                cat.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableCategorie._ID)) + "");
                cat.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.TITRE)));
                cat.setDescription(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.DESCRIPTION)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cat;
    }

    public List<CategoriePatient> getAllCategorie() {
        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TableCategorie.TABLE_NAME, COLUMN_CATEGORIE, null, null, null, null, null);
        List<CategoriePatient> listCategorie = new ArrayList<>();

        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            do {
                CategoriePatient newCat = new CategoriePatient();

                newCat.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableCategorie._ID)) + "");
                newCat.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.TITRE)));
                newCat.setDescription(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.DESCRIPTION)));

                listCategorie.add(newCat);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listCategorie;
    }

    public List<AgendaALarm> getAllAlarmByPatientId(String idPatient) {

        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TableAgenda.TABLE_NAME, COLUMN_AGENDA, MyContracts.TableAgenda.ID_PATIENT + "=?", new String[]{idPatient}, null, null, MyContracts.TableAgenda._ID + " DESC");
        List<AgendaALarm> listAlarms = new ArrayList<>();

        if (cursor == null)
            return null;
        if (cursor.moveToFirst()) {
            do {
                AgendaALarm alarm = new AgendaALarm();

                alarm.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda._ID)));
                alarm.setTitre(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.TITRE)));
                alarm.setMessage(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE)));
                alarm.setMessageNumbers(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE_NUMBERS)));

                alarm.setDateMillisNow(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_SET)));
                alarm.setDateMillisWakeUp(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_TARGET)));
                alarm.setDateHumanNow(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_SET)));
                alarm.setDateHumanWakeUp(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_TARGET)));

                alarm.setState(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_STATE)) == 1);
                alarm.setRepeatCount((char) cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_COUNT)));
                alarm.setRepeatTimeInterval(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL)));
                alarm.setMusicPath(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_MUSIC_PATH)));
                alarm.setVolumeLevel(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_VOLUME)));

                alarm.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ID_PATIENT)));
                alarm.setIdCategorie(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ID_CATEGORIE)));

                listAlarms.add(alarm);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listAlarms;

    }

    public List<AgendaALarm> getAllAlarm() {
        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TableAgenda.TABLE_NAME, COLUMN_AGENDA, null, null, null, null, MyContracts.TableAgenda._ID + " DESC");
        List<AgendaALarm> listAlarms = new ArrayList<>();

        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            do {
                AgendaALarm alarm = new AgendaALarm();

                alarm.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda._ID)));
                alarm.setTitre(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.TITRE)));
                alarm.setMessage(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE)));
                alarm.setMessageNumbers(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE_NUMBERS)));

                alarm.setDateMillisNow(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_SET)));
                alarm.setDateMillisWakeUp(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_TARGET)));
                alarm.setDateHumanNow(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_SET)));
                alarm.setDateHumanWakeUp(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_TARGET)));

                alarm.setState(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_STATE)) == 1);
                alarm.setRepeatCount((char) cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_COUNT)));
                alarm.setRepeatTimeInterval(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL)));
                alarm.setMusicPath(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_MUSIC_PATH)));
                alarm.setVolumeLevel(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_VOLUME)));

                alarm.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ID_PATIENT)));
                alarm.setIdCategorie(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ID_CATEGORIE)));

                listAlarms.add(alarm);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listAlarms;
    }


    public AgendaALarm getAlarmById(String alarmId) {
        db = getReadableDatabase();
        Cursor cursor = db.query(MyContracts.TableAgenda.TABLE_NAME, COLUMN_AGENDA, MyContracts.TableAgenda._ID+"=?", new String[]{alarmId}, null, null, null);
        AgendaALarm alarm = null;

        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            do {
                alarm = new AgendaALarm();

                alarm.setId(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda._ID)));
                alarm.setTitre(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.TITRE)));
                alarm.setMessage(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE)));
                alarm.setMessageNumbers(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.MESSAGE_NUMBERS)));

                alarm.setDateMillisNow(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_SET)));
                alarm.setDateMillisWakeUp(cursor.getLong(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_MILLIS_TARGET)));
                alarm.setDateHumanNow(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_SET)));
                alarm.setDateHumanWakeUp(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.DATE_TARGET)));

                alarm.setState(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_STATE)) == 1);
                alarm.setRepeatCount((char) cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_COUNT)));
                alarm.setRepeatTimeInterval(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL)));
                alarm.setMusicPath(cursor.getString(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_MUSIC_PATH)));
                alarm.setVolumeLevel(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ALARM_VOLUME)));

                alarm.setIdPatient(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ID_PATIENT)));
                alarm.setIdCategorie(cursor.getInt(cursor.getColumnIndex(MyContracts.TableAgenda.ID_CATEGORIE)));

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alarm;
    }

    // TODO --------- DELETE METHODS ---------------------------------

    public void deletePatient(String idPatient, Context context) {
        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
        long deletedID = 0;
        try{
            deletedID = db.delete(MyContracts.TablePatient.TABLE_NAME, MyContracts.TablePatient._ID + "=?", new String[]{idPatient});
        }catch (Exception ex){
            ex.printStackTrace();
        }
        db.close();

        //supprimer tous les alarmes liés à ce PATIENT
        if(deletedID>0){
            myDbHelper.deleteAlarmByIdPatient(idPatient, context);
        }
    }

    public void deleteAlarmByIdPatient(String idPatient, Context context) {

        List<AgendaALarm> alarms = getAllAlarmByPatientId(idPatient);

        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        for (AgendaALarm alarm : alarms) {

                int successID = 0;
                try {
                    successID = db.delete(MyContracts.TableAgenda.TABLE_NAME, MyContracts.TableAgenda._ID + "=?", new String[]{alarm.getId() + ""});

                    //On desactive les alarmes qui étaient ACTIVES
                    if (successID > 0 & alarm.isState()) {
                        alarm.setId(successID);
                        //Gestion au niveau du système
                        //supression possible de la planification.
                        MyFunctions.manageAlarm(context, alarm, false);
                    }
                    Log.e("delete alarm" + alarm.getId(), "________ success");
                } catch (Exception ex) {
                    Log.e("delete alarm", "________ failed");
                }
        }

        db.close();
    }

    public void deleteAgendaAlarm(String idAgenda, Context context) {

        db = getWritableDatabase();
        long deletedID = 0;

        try {
            deletedID = db.delete(MyContracts.TableAgenda.TABLE_NAME, MyContracts.TableAgenda._ID + "=?", new String[]{idAgenda});
        }catch (Exception ex){ex.printStackTrace();}
        db.close();

        //Desactiver l'alarm
        if(deletedID>0){
            AgendaALarm alarm = new AgendaALarm();
            alarm.setId(Integer.valueOf(idAgenda));
            MyFunctions.manageAlarm(context,alarm, false);
        }

    }

    public void deleteCategorie(String idCategorie) {

        db = getWritableDatabase();
        db.delete(MyContracts.TableCategorie.TABLE_NAME, MyContracts.TableCategorie._ID + "=?", new String[]{idCategorie});
        db.close();
    }

    // TODO --------- UPDATE METHODS ---------------------------------
    public void updatePatient(Patient patient) {

        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.NOM, patient.getNom());
        values.put(MyContracts.TablePatient.PRENOM, patient.getPrenom());
        values.put(MyContracts.TablePatient.SEXE, patient.getSexe());
        values.put(MyContracts.TablePatient.DATE, patient.getDateNaissance());
        values.put(MyContracts.TablePatient.TELEPHONE, patient.getTelephone());
        values.put(MyContracts.TablePatient.CAS, patient.getCasPatient());

        //TODO --the categorie update doesn't impact here, we use a special method for that
        //TODO -- it's: updatePatientCategorie

        try {
            db.update(MyContracts.TablePatient.TABLE_NAME, values, MyContracts.TablePatient._ID + "=?", new String[]{patient.getIdPatient()});
        } catch (Exception ex) {
            Log.e("update cat", "________ failed");
        }
        db.close();
    }

    public void addPatientToCategory(String idCat, String idPatient) {

        db = getWritableDatabase();

        Cursor cursor = db.query(MyContracts.TablePatient.TABLE_NAME, new String[]{MyContracts.TablePatient.CATEGORIE}, MyContracts.TablePatient._ID + "=?", new String[]{idPatient}, null, null, null);
        String categories = "";

        if (cursor != null)
            while (cursor.moveToNext())
                categories = cursor.getString(cursor.getColumnIndex(MyContracts.TablePatient.CATEGORIE));

        cursor.close();

        //TODO En: check if this is the first add-to-category; If so, add cat #ID# without comma(,)
        //TODO Fr: virifier si c'est la 1ère fois qu'on ajoute ce patient à une categorie;
        //TODO     si c'est le cas, ajouter l' #ID# de la categorie sans virgule(,)

        if (categories.isEmpty())
            categories = "#" + idCat + "#";
        else
            categories += ",#" + idCat + "#";

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.CATEGORIE, categories);

        try {
            db.update(MyContracts.TablePatient.TABLE_NAME, values, MyContracts.TablePatient._ID + "=?", new String[]{idPatient});
        } catch (Exception ex) {
            Log.e("update patient cat", "________ failed");
        }
        db.close();

    }


    public void removePatientFromCategorie(String removeIdCat, Patient patient) {

        db = getWritableDatabase();

        //TODO : the PatientCategorie id is referenced by the following: #id#
        //TODO : "#id#" the # are delimiters, and it makes a number unique in char Set.
        List<CategoriePatient> list = patient.getListCat();
        String categoriesStr = "";
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getId() != removeIdCat) {
                if (categoriesStr.isEmpty())
                    categoriesStr += "#" + list.get(i).getId() + "#";
                else //On insère le separateur à partir du deuxième ID.
                    categoriesStr += ",#" + list.get(i).getId() + "#";
            }
        }

        //TODO Dans categoriesStr on a exclu removeIdCat qui est l'id à enlever
        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.CATEGORIE, categoriesStr);

        try {
            db.update(MyContracts.TablePatient.TABLE_NAME, values, MyContracts.TablePatient._ID + "=?", new String[]{patient.getIdPatient()});
        } catch (Exception ex) {
            Log.e("update cat", "________ failed");
        }
        db.close();
    }

    public void removePatientFromAllCategorie(String idPatient) {

        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.CATEGORIE, "");

        try {
            db.update(MyContracts.TablePatient.TABLE_NAME, values, MyContracts.TablePatient._ID + "=?", new String[]{idPatient});
        } catch (Exception ex) {
            Log.e("update cat", "________ failed");
        }
        db.close();
    }

    public void updateAlarm(AgendaALarm alarm, Context context) {

        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableAgenda.TITRE, alarm.getTitre());
        values.put(MyContracts.TableAgenda.MESSAGE, alarm.getMessage());
        values.put(MyContracts.TableAgenda.MESSAGE_NUMBERS, alarm.getMessageNumbers());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_SET, alarm.getDateMillisNow());
        values.put(MyContracts.TableAgenda.DATE_MILLIS_TARGET, alarm.getDateMillisWakeUp());

        values.put(MyContracts.TableAgenda.DATE_SET, alarm.getDateHumanNow());
        values.put(MyContracts.TableAgenda.DATE_TARGET, alarm.getDateHumanWakeUp());
        values.put(MyContracts.TableAgenda.ALARM_MUSIC_PATH, alarm.getMusicPath());
        values.put(MyContracts.TableAgenda.ALARM_STATE, alarm.isState());
        values.put(MyContracts.TableAgenda.ALARM_VOLUME, alarm.getVolumeLevel());

        values.put(MyContracts.TableAgenda.ALARM_REPEAT_COUNT, alarm.getRepeatCount() + "");
        values.put(MyContracts.TableAgenda.ALARM_REPEAT_TIME_INTERVAL, alarm.getRepeatTimeInterval());

        values.put(MyContracts.TableAgenda.ID_PATIENT, alarm.getIdPatient());
        values.put(MyContracts.TableAgenda.ID_CATEGORIE, alarm.getIdCategorie());

        try {
            int successID = 0;
            successID = db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID + "=?", new String[]{alarm.getId() + ""});
            if (successID > 0) {
                //manageAlarm(...) a besoin de l'ID et de la DATE qui se trouve ds l'Objet alarm.
                MyFunctions.manageAlarm(context, alarm, alarm.isState());
                Log.e("update alarm", "________ success id:"+alarm.getId());
            }

        } catch (Exception ex) {
            Log.e("update alarm", "________ failed : " + alarm.getId());
            ex.printStackTrace();
        }

        db.close();
    }


    public void toggleAlarmOff(AgendaALarm alarm, Context context) {

        db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //faire baculer ll'etat sans savoir l'etat actuel
        values.put(MyContracts.TableAgenda.ALARM_STATE, false);
        int successID = 0;
        try {
            successID = db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID + "=?", new String[]{alarm.getId() + ""});

            if (successID > 0) {
                //manageAlarm(...) a besoin de l'ID et de la DATE qui se trouve ds l'Objet alarm.
                MyFunctions.manageAlarm(context, alarm, false);
                Log.e("update toggle alarm", "________ success");
            }

        } catch (Exception ex) {
            Log.e("update alarm", "________ failed");
        }

        db.close();
    }

    public void toggleAlarmOn(AgendaALarm alarm, Context context) {

        db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //faire baculer ll'etat sans savoir l'etat actuel
        values.put(MyContracts.TableAgenda.ALARM_STATE, true);
        int successID = 0;
        try {
            successID = db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID + "=?", new String[]{alarm.getId() + ""});

            if (successID > 0) {
                //manageAlarm(...) a besoin de l'ID et de la DATE qui se trouve ds l'Objet alarm.
                MyFunctions.manageAlarm(context, alarm, true);
                Log.e("update toggle alarm", "________ success id:"+alarm.getId());
            }

        } catch (Exception ex) {
            Log.e("update alarm", "________ failed");
        }

        db.close();
    }

    public void toggleAllAlarmByPatientId(String idPatient, boolean newState, Context context) {

        List<AgendaALarm> alarms = getAllAlarmByPatientId(idPatient);

        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        for (AgendaALarm alarm : alarms) {

            //On utilise que les alarms qui sont d'ETAT different
            if (alarm.isState() != newState) {

                ContentValues values = new ContentValues();
                values.put(MyContracts.TableAgenda.ALARM_STATE, newState);

                int successID = 0;
                try {
                    successID = db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID + "=?", new String[]{alarm.getId() + ""});

                    if (successID > 0) {
                        //Gestion au niveau du système
                        //manageAlarm(...) a besoin de l'ID et de la DATE qui se trouve ds l'Objet alarm.
                        MyFunctions.manageAlarm(context, alarm, newState);
                    }
                    Log.e("update toggle alarm" + alarm.getId(), "________ success");
                } catch (Exception ex) {
                    Log.e("update alarm", "________ failed");
                }
            }
        }

        db.close();
    }

    public void updateCategorie(CategoriePatient categorie, Context context) {
        MyDbHelper myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TableCategorie.TITRE, categorie.getNom());
        values.put(MyContracts.TableCategorie.DESCRIPTION, categorie.getDescription());

        try {
            db.update(MyContracts.TableAgenda.TABLE_NAME, values, MyContracts.TableAgenda._ID + "=?", new String[]{categorie.getId()});
        } catch (Exception ex) {
            Log.e("update cat", "________ failed");
        }
        db.close();
    }


    // TODO ---------- EXTRA METHODS ----------

    private List<CategoriePatient> getListCategorie(String categories) {

        List<CategoriePatient> listCategorie = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        if (categories != null) {
            String[] listCat = categories.split("-");

            for (String idCategorie : listCat) {

                //On suprime le delimiteur "#" pour retrouver la valeur entière(int) de l'id
                idCategorie = idCategorie.replaceAll("#", "");

                Cursor cursor = db.query(MyContracts.TableCategorie.TABLE_NAME, COLUMN_CATEGORIE, MyContracts.TableCategorie._ID + "=?", new String[]{idCategorie}, null, null, null, null);
                if (cursor != null) {

                    while (cursor.moveToNext()) {

                        CategoriePatient newCat = new CategoriePatient();
                        newCat.setId(idCategorie);
                        newCat.setDescription(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.DESCRIPTION)));
                        newCat.setNom(cursor.getString(cursor.getColumnIndex(MyContracts.TableCategorie.DESCRIPTION)));

                        listCategorie.add(newCat);
                    }
                    cursor.close();
                }
            }
        }

        db.close();
        return listCategorie;
    }
}
