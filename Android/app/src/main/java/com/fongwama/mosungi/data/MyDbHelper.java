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

import com.fongwama.mosungi.model.Patient;

/**
 * Created by Karl on 20/07/2016.
 */
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
    };

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        final String SQL_PATIENT = "CREATE TABLE "+
                MyContracts.TablePatient.TABLE_NAME+" ("+
                MyContracts.TablePatient._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MyContracts.TablePatient.NOM+" TEXT NOT NULL, "+
                MyContracts.TablePatient.PRENOM+" TEXT NOT NULL, "+
                MyContracts.TablePatient.SEXE+" TEXT NOT NULL, "+
                MyContracts.TablePatient.DATE+" TEXT NOT NULL, "+
                MyContracts.TablePatient.TELEPHONE+" TEXT NOT NULL, "+
                MyContracts.TablePatient.CAS+" TEXT NOT NULL)";

        //On exécute la requete de création de la table patient
        db.execSQL(SQL_PATIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void insertPatient(Patient patient, Context context){

        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyContracts.TablePatient.NOM,            patient.getNom());
        values.put(MyContracts.TablePatient.PRENOM,         patient.getPrenom());
        values.put(MyContracts.TablePatient.SEXE,           patient.getSexe());
        values.put(MyContracts.TablePatient.DATE,           patient.getDateNaissance());
        values.put(MyContracts.TablePatient.TELEPHONE,      patient.getTelephone());
        values.put(MyContracts.TablePatient.CAS,            patient.getCasPatient());

        try {
            db.insert(MyContracts.TablePatient.TABLE_NAME, null, values);
            Log.i(" tryed Patient ", " insertion ok");
        }
        catch (Exception ex){
            Log.i(" error Patient "," insertion catched");
        }
        db.close();
    }

    public List<Patient> getAllPatient()
    {
        SQLiteDatabase db   = getReadableDatabase();
        Cursor cursor       = db.query(MyContracts.TablePatient.TABLE_NAME, COLUMN_PATIENT, null, null, null, null, null);
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
        return listPatients;
    }

    //Nous avons besoin de cette méthode pour le Spinner
    /*public ArrayList<HashMap<String,String>> selectNomPrenomPatient(Context context)
    {
        MyDbHelper myDbHelper      = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();

        ArrayList<HashMap<String,String>> listNomPrenom=new ArrayList<HashMap<String,String>>();
        HashMap<String,String> map;

        Cursor cursorPatient       = myDbHelper.getAllPatient();
        Log.i("CountPatient", cursorPatient.getCount()+"");

        if (cursorPatient.moveToFirst())
        {
            do
            {
                int id              = cursorPatient.getInt(cursorPatient.getColumnIndex(MyContracts.TablePatient._ID));
                String nom          = cursorPatient.getString(cursorPatient.getColumnIndex(MyContracts.TablePatient.NOM));
                String prenom       = cursorPatient.getString(cursorPatient.getColumnIndex(MyContracts.TablePatient.PRENOM));
                String nomPrenom    = nom+" "+prenom;

                map=new HashMap<String, String>();
                map.put("id", String.valueOf(id));
                map.put("prenom_nom", nomPrenom);

                listNomPrenom.add(map);
            }
            while (cursorPatient.moveToNext());
        }

        Log.i("CountPatient", listNomPrenom.size()+"");
        return listNomPrenom;
    }*/
}
