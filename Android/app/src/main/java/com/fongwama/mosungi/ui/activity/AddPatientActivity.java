package com.fongwama.mosungi.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.Patient;

public class AddPatientActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextInputEditText name;
    private TextInputEditText prenom;
    private TextInputEditText date;
    private TextInputEditText telephone;
    private TextInputEditText cas;

    private RadioGroup sexe;
    private Button btnAdd;

    private FloatingActionButton pick_date;
    DatePickerDialog datePickerDialog;

    public static final String DATEPICKER_TAG = "datepicker";

    MyFunctions myFunctions = new MyFunctions();

    Calendar calendarDate;
    Patient updatePatient;
    MyDbHelper myDbHelper;
    //ColoredSnackbar coloredSnackbar = new ColoredSnackbar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDbHelper = new MyDbHelper(getApplicationContext());
        calendarDate = Calendar.getInstance();

        name        = (TextInputEditText)findViewById(R.id.name);
        prenom      = (TextInputEditText)findViewById(R.id.prenom);
        date        = (TextInputEditText)findViewById(R.id.date);
        telephone   = (TextInputEditText)findViewById(R.id.telephone);
        cas         = (TextInputEditText)findViewById(R.id.cas);
        btnAdd = (Button) findViewById(R.id.validate);

        sexe      =(RadioGroup)findViewById(R.id.sexe);
        pick_date = (FloatingActionButton) findViewById(R.id.pick_date);

        //Au cas il s'agit d'un Update, on recupère le patient dans l'itent
        Bundle data = getIntent().getExtras();
        if(data!=null){
            updatePatient = data.getParcelable(MainActivity.EXTRA_EDIT_PATIENT);
            if(updatePatient!=null){
                calendarDate.setTimeInMillis(Long.valueOf(updatePatient.getDateNaissance()));
                populateViews();
            }
        }
        else
        {
            datePickerDialog = DatePickerDialog.newInstance(
                    AddPatientActivity.this,
                    calendarDate.get(Calendar.YEAR),
                    calendarDate.get(Calendar.MONTH),
                    calendarDate.get(Calendar.DAY_OF_MONTH));
        }



        //on limite l'age des patients à 85 ans
        try {
            datePickerDialog.setYearRange((calendarDate.get(Calendar.YEAR) - 85), calendarDate.get(Calendar.YEAR));
        }catch (IllegalArgumentException ex){ex.printStackTrace();}

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myFunctions.checkTextInputEditText(name) &&
                        myFunctions.checkTextInputEditText(prenom) &&
                        myFunctions.checkTextInputEditText(date) &&
                        myFunctions.checkTextInputEditText(telephone) &&
                        myFunctions.checkTextInputEditText(cas))
                {
                    if (myFunctions.checkPhoneNumberFormat(telephone)) {
                        String nomPatient = name.getText().toString().toLowerCase();
                        String prenomPatient = prenom.getText().toString().toLowerCase();
                        String telephonePatient = telephone.getText().toString();
                        String casPatient = cas.getText().toString();
                        String sexePatient = sexe.getCheckedRadioButtonId() == R.id.radioM ? "M" : "F";
                        Log.i("______ sexe", sexePatient);
                        //On prend la Date du patient en format unix(dateInMillis)
                        String datePatient = calendarDate.getTimeInMillis() + "";

                        Patient patient = new Patient(null, nomPatient, prenomPatient, sexePatient, datePatient, telephonePatient, casPatient);


                        if (updatePatient == null) {
                            myDbHelper.insertPatient(patient, getApplicationContext());

                            name.getText().clear();
                            prenom.getText().clear();
                            telephone.getText().clear();
                            cas.getText().clear();
                            date.getText().clear();

                            Toast.makeText(AddPatientActivity.this, R.string.alert_patient_added, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //On recupère l'id du patient
                            patient.setIdPatient(updatePatient.getIdPatient());
                            myDbHelper.updatePatient(patient);
                            Toast.makeText(AddPatientActivity.this, R.string.alert_patient_updated, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Snackbar snackbar = Snackbar.make(v, R.string.alert_phone_format, Snackbar.LENGTH_SHORT);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();
                    }

                }
                else
                {
                    Snackbar snackbar = Snackbar.make(v, R.string.alert_empty, Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });


    }

    //Methode utilisé uniquement pour le UPDATE
    private void populateViews() {

        name.setText(updatePatient.getNom());
        prenom.setText(updatePatient.getPrenom());
        telephone.setText(updatePatient.getTelephone());
        cas.setText(updatePatient.getCasPatient());

        date.setText(MyFunctions.getReadableShortDate(calendarDate.getTimeInMillis()));

        this.datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendarDate.get(Calendar.YEAR),
                calendarDate.get(Calendar.MONTH),
                calendarDate.get(Calendar.DAY_OF_MONTH));

        //on limite l'age des patients à contacter à 85 ans
        try {
            datePickerDialog.setYearRange((calendarDate.get(Calendar.YEAR) - 85), calendarDate.get(Calendar.YEAR));
        }catch (IllegalArgumentException ex){
            Log.e("_____","min year end must > 1902");
        }
        Log.e("sex val__","__"+updatePatient.getSexe());
        //On
        if(updatePatient.getSexe().toLowerCase().equals("m"))
            ((RadioButton)sexe.findViewById(R.id.radioM)).setChecked(true);
        else
            ((RadioButton)sexe.findViewById(R.id.radioF)).setChecked(true);

        btnAdd.setText(getResources().getString(R.string.btn_patient_update));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        calendarDate.set(year, month, day);
        date.setText(MyFunctions.getReadableShortDate(Long.valueOf(calendarDate.getTimeInMillis())));
    }
}
