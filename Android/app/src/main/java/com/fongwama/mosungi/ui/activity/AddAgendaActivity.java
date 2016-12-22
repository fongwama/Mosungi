package com.fongwama.mosungi.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.adapter.SpinnerAdapter;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AddAgendaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextInputEditText dateEdit;
    private TextInputEditText hourEdit;
    private TextInputEditText message;
    private TextInputEditText titre; //courte description

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    private Switch stateSwitch;
    private Button btnAddUpdate;

    private FloatingActionButton pick_date;
    private FloatingActionButton pick_hour;

    MyFunctions myFunctions = new MyFunctions();
    MyDbHelper myDbHelper;
    Calendar calendar;
    List<Patient> listPatients;
    AgendaALarm alarmUpdate;

    int cYear, cMonth, cDay, cHour, cMin, cSec = 0;
    boolean isAlarmUpdate = false;

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources() .getColor(R.color.colorPrimary)));

        Bundle data = getIntent().getExtras();
        if(data != null){
            alarmUpdate = data.getParcelable(MainActivity.EXTRA_EDIT_AGENDA_ALARM);
            Log.e("_____id patient add", alarmUpdate.getIdPatient()+"");
            //Demande de mise-à-jour activé
            if(alarmUpdate!=null && alarmUpdate.getDateMillisNow()!= 1){
                isAlarmUpdate = true;
            }
        }

        dateEdit = (TextInputEditText) findViewById(R.id.dates);
        hourEdit = (TextInputEditText) findViewById(R.id.hours);
        message  = (TextInputEditText) findViewById(R.id.message);
        titre  = (TextInputEditText) findViewById(R.id.titre);

        btnAddUpdate = (Button) findViewById(R.id.validate);
        spinner  = (Spinner)findViewById(R.id.spinner);
        stateSwitch = (Switch) findViewById(R.id.state);
        stateSwitch.setChecked(true);

        myDbHelper = new MyDbHelper(getApplicationContext());

        ///////////////////////////////////////////////////////////////////////
        //////////////////////////

        if(alarmUpdate!=null)
        {
            listPatients = new ArrayList<Patient>();
            listPatients.add(myDbHelper.getPatientById(alarmUpdate.getIdPatient() + ""));
            spinnerAdapter = new SpinnerAdapter(getApplicationContext(), listPatients);
            spinner.setAdapter(spinnerAdapter);
        }
        else
        {
            listPatients = myDbHelper.getAllPatient();
            spinnerAdapter = new SpinnerAdapter(getApplicationContext(), listPatients);
            spinner.setAdapter(spinnerAdapter);
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(alarmUpdate!=null)
                    alarmUpdate.setIdPatient(
                        Integer.valueOf(
                                ((Patient)parent.getItemAtPosition(position)).getIdPatient()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //////////////////////////
        ///////////////////////////////////////////////////////////////////////


        pick_date = (FloatingActionButton) findViewById(R.id.pick_date);
        pick_hour = (FloatingActionButton) findViewById(R.id.pick_hour);

        //TODO --- check if the user requested an update & populate the views with existing data
        //TODO --- this position is strategic, the views have to be populated exactly here.
        if(isAlarmUpdate)
            populateViews();

        //Get the current time to use in Date & Time selection
        if(this.calendar == null){
            calendar = Calendar.getInstance();
            cYear  = calendar.get(Calendar.YEAR);
            cMonth = calendar.get(Calendar.MONTH);
            cDay   = calendar.get(Calendar.DAY_OF_MONTH);
            cHour  = calendar.get(Calendar.HOUR_OF_DAY);
            cMin   = calendar.get(Calendar.MINUTE);
        }

        datePickerDialog = DatePickerDialog.newInstance(
               this,
                cYear,
                cMonth,
                cDay
        );
        //On limite la programmation du message à 5 ans
        datePickerDialog.setYearRange(calendar.get(Calendar.YEAR),calendar.get(Calendar.YEAR)+5);

        timePickerDialog = TimePickerDialog.newInstance(
                this,
                cHour,
                cMin,
                true, false);
        timePickerDialog.setOnTimeSetListener(this);

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
/*
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
*/

        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myFunctions.checkTextInputEditText(dateEdit) &&
                        myFunctions.checkTextInputEditText(hourEdit) &&
                        myFunctions.checkTextInputEditText(message)) {



                    AgendaALarm alarm = new AgendaALarm();

                    Calendar calendar = Calendar.getInstance();
                    alarm.setDateMillisNow(calendar.getTimeInMillis());

                    calendar.set(cYear, cMonth, cDay, cHour, cMin,cSec);
                    alarm.setDateMillisWakeUp(calendar.getTimeInMillis());

                    alarm.setRepeatTimeInterval(0);
                    alarm.setState(stateSwitch.isChecked());

                    //On recupère l'id du patient dans le Spiner
                    alarm.setIdPatient( Integer.valueOf(((Patient)spinner.getSelectedItem()).getIdPatient()) );

                    alarm.setTitre(titre.getText().toString().toLowerCase());
                    alarm.setMessage(message.getText().toString());

                    //Si --le temps programé n'est pas au futur ET --L'alarm est activé
                    if (System.currentTimeMillis() > calendar.getTimeInMillis() && alarm.isState()) {

                        Toast.makeText(AddAgendaActivity.this,getResources().getString(R.string.error_planification_date),Toast.LENGTH_LONG).show();

                        // two second before updating the view to unchecked
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stateSwitch.setChecked(false);
                            }
                        }, 1000);

                        Log.i("alarm time",System.currentTimeMillis()+"  __ "+calendar.getTimeInMillis());
                        return;
                    }
                    else{
                        Log.i("alarm time","_______respected AND alarm is OFF/ON");
                    }

                    //START DYNAMIC PERMISSION REQUEST - à partit de l'API 23
                    MyFunctions.checkSMSPermissions(AddAgendaActivity.this);
                    //END PERMISSION REQUEST

                    if(isAlarmUpdate){

                        //inserer l'id existant
                        alarm.setId(alarmUpdate.getId());

                        //Fr: mettre à jour l'Alarm au niveau du télephone et de la base_de_donness avec l'ID de alarm(Object) as TAG
                        //En: Register(update) with the alarm(Object) ID as TAG(REQUEST_CODE) & update the alarm in database
                          myDbHelper.updateAlarm(alarm, AddAgendaActivity.this);

                        Toast.makeText(AddAgendaActivity.this, "Alarm mis-à-jour", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        long sucessID = 0;
                        sucessID = myDbHelper.insertAlarm(alarm, AddAgendaActivity.this);

                        if (sucessID>0){
                            //Register the alarm with the inserted ID as TAG
                            Toast.makeText(AddAgendaActivity.this, "Alarm ajouté "+sucessID, Toast.LENGTH_SHORT).show();
                            clearFields();
                        }
                        else
                        {
                            Toast.makeText(AddAgendaActivity.this, "Echec ajout", Toast.LENGTH_SHORT).show();
                        }
                    }

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

    private void clearFields(){
        dateEdit.getText().clear();
        hourEdit.getText().clear();
        message.getText().clear();
        titre.getText().clear();
        stateSwitch.setChecked(true);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void populateViews() {
        List list = null;

        if(alarmUpdate.getIdPatient()>0) {

            stateSwitch.setChecked(alarmUpdate.isState());
            titre.setText(alarmUpdate.getTitre());
            message.setText(alarmUpdate.getMessage());

            if(alarmUpdate.getDateMillisNow()>1)
            btnAddUpdate.setText(getResources().getString(R.string.btn_patient_update));


            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(alarmUpdate.getDateMillisWakeUp());

            //affecter la valeur du déclenchement à la variable globale calendar
            this.calendar = mCalendar;

            cYear = mCalendar.get(Calendar.YEAR);
            cMonth= mCalendar.get(Calendar.MONTH);
            cDay  = mCalendar.get(Calendar.DAY_OF_MONTH);
            cHour = Integer.valueOf(MyFunctions.get24Hour(alarmUpdate.getDateMillisWakeUp()));
            cMin  = Integer.valueOf(MyFunctions.getFormatedMinutes(alarmUpdate.getDateMillisWakeUp()));
            cSec  = mCalendar.get(Calendar.SECOND);

            //pour aficher la date en horizontale, on utilise la methose replaceAll() qui se charge
            //de remplacer les retour à la ligne "\n" par des espaces " "
            dateEdit.setText(MyFunctions.getReadableDate(mCalendar.getTimeInMillis()).replaceAll("\n"," "));

            //Par defaut, l'heure est en AM/PM, on le converti en 24H avec la methode get24Hour()
            hourEdit.setText(MyFunctions.get24Hour(mCalendar.getTimeInMillis())+":"+mCalendar.get(Calendar.MINUTE));
        }
        /*else if(alarmUpdate.getIdCategorie()!=0){
            list = new ArrayList<CategoriePatient>();
            list.add(myDbHelper.getPatientById(alarmUpdate.getIdPatient()+""));
        }*/
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
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        cHour = hourOfDay;
        cMin = minute;

        hourEdit.setText(hourOfDay+":"+minute);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        //show date to the User
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeZone(TimeZone.getDefault());
        mCalendar.set(cYear, cMonth, cDay);

        cYear = year;
        cMonth = month;
        cDay = day;

        String tmpDate = MyFunctions.getReadableDate(mCalendar.getTimeInMillis()).replaceAll("\n"," ");
        ((TextInputEditText) findViewById(R.id.dates) ).setText(tmpDate);
    }


}
