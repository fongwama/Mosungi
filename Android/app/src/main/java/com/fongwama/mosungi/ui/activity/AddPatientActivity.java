package com.fongwama.mosungi.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.functions.ColoredSnackbar;
import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.Patient;

public class AddPatientActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextInputEditText name;
    private TextInputEditText lastname;
    private TextInputEditText date;
    private TextInputEditText telephone;
    private TextInputEditText cas;

    private RadioGroup sexe;
    private RadioButton sexe_checked;

    private FloatingActionButton pick_date;

    public static final String DATEPICKER_TAG = "datepicker";

    MyFunctions myFunctions = new MyFunctions();
    //ColoredSnackbar coloredSnackbar = new ColoredSnackbar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());

        name        = (TextInputEditText)findViewById(R.id.name);
        lastname    = (TextInputEditText)findViewById(R.id.lastname);
        date        = (TextInputEditText)findViewById(R.id.date);
        telephone   = (TextInputEditText)findViewById(R.id.telephone);
        cas         = (TextInputEditText)findViewById(R.id.cas);

        sexe            =(RadioGroup)findViewById(R.id.sexe);
        sexe_checked    = (RadioButton) sexe.findViewById(sexe.getCheckedRadioButtonId());

        pick_date = (FloatingActionButton) findViewById(R.id.pick_date);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

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



        findViewById(R.id.validate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myFunctions.checkTextInputEditText(name) &&
                        myFunctions.checkTextInputEditText(lastname) &&
                        myFunctions.checkTextInputEditText(date) &&
                        myFunctions.checkTextInputEditText(telephone) &&
                        myFunctions.checkTextInputEditText(cas))
                {
                    if (myFunctions.checkPhoneNumberFormat(telephone))
                    {
                        String nomPatient       = name.getText().toString();
                        String prenomPatient    = lastname.getText().toString();
                        String datePatient      = date.getText().toString();
                        String telephonePatient = telephone.getText().toString();
                        String casPatient       = cas.getText().toString();
                        String sexePatient      = sexe_checked.getText().toString();

                        Patient patient         = new Patient(nomPatient, prenomPatient, sexePatient, datePatient, telephonePatient, casPatient);
                        myDbHelper.insertPatient(patient, getApplicationContext());

                        name.getText().clear();
                        lastname.getText().clear();
                        telephone.getText().clear();
                        cas.getText().clear();
                        date.getText().clear();

                        Snackbar snackbar = Snackbar.make(v, R.string.alert_patient_added, Snackbar.LENGTH_SHORT);
                        snackbar.show();
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
                    //coloredSnackbar.alert(snackbar, getApplicationContext()).show();
                }
            }
        });


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
        date.setText(day+"/"+(month+1)+"/"+year+"");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }
}
