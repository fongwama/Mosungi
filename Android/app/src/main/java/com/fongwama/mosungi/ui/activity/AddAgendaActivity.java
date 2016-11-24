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
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fongwama.mosungi.model.AgendaALarm;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.adapter.SpinnerAdapter;

public class AddAgendaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextInputEditText dateEdit;
    private TextInputEditText hourEdit;
    private TextInputEditText message;

    private Spinner spinner;

    private FloatingActionButton pick_date;
    private FloatingActionButton pick_hour;

    MyFunctions myFunctions = new MyFunctions();
    MyDbHelper myDbHelper;
    Calendar calendar;
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateEdit = (TextInputEditText) findViewById(R.id.dates);
        hourEdit = (TextInputEditText) findViewById(R.id.hours);
        message  = (TextInputEditText) findViewById(R.id.message);

        myDbHelper = new MyDbHelper(getApplicationContext());

        ///////////////////////////////////////////////////////////////////////
        //////////////////////////
        spinner  = (Spinner)findViewById(R.id.spinner);

        List<Patient> listPatients = myDbHelper.getAllPatient();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), listPatients);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //////////////////////////
        ///////////////////////////////////////////////////////////////////////


        pick_date = (FloatingActionButton) findViewById(R.id.pick_date);
        pick_hour = (FloatingActionButton) findViewById(R.id.pick_hour);

        calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);



        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        pick_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });
        hourEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }


        findViewById(R.id.validate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myFunctions.checkTextInputEditText(dateEdit) &&
                        myFunctions.checkTextInputEditText(hourEdit) &&
                        myFunctions.checkTextInputEditText(message)) {

                    AgendaALarm alarm = new AgendaALarm();
                    calendar
                    alarm.setMessage(message.getText().toString());
                    alarm.setDateHumanWakeUp(message.getText().toString());

                    myDbHelper.insertAlarm(alarm, AddAgendaActivity.this);
                } else {
                    Snackbar snackbar = Snackbar.make(v, R.string.alert_empty, Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
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
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {


        dateEdit.setText(day+"/"+(month+1)+"/"+year+"");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        hourEdit.setText(hourOfDay+":"+minute);
    }
}
