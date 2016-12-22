package com.fongwama.mosungi.ui.dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.activity.AddAgendaActivity;
import com.fongwama.mosungi.ui.activity.AddPatientActivity;
import com.fongwama.mosungi.ui.activity.MainActivity;

import java.util.Calendar;

/**
 * Created by Karl on 30/07/2016.
 */
public class PatientManagementMenu extends DialogFragment implements View.OnClickListener{

    private Patient patient;
    private MyDbHelper helper;

    public static DialogFragment newInstance(Patient patient)
    {
        DialogFragment dialogFragment = new PatientManagementMenu();
        Bundle args = new Bundle();
        args.putParcelable("PATIENT", patient);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        patient = getArguments().getParcelable("PATIENT");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //patient.getNom() patient.getPrenom()

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View popup = inflater.inflate(R.layout.dialog_view_patient_menu, null, false);

        TextView title = (TextView) popup.findViewById(R.id.title);
        title.setText(patient.getNom()+" "+patient.getPrenom());

        LinearLayout newAlarm = (LinearLayout) popup.findViewById(R.id.addNewAlarm);
        newAlarm.setOnClickListener(this);
        LinearLayout newSms = (LinearLayout) popup.findViewById(R.id.sendSms);
        newSms.setOnClickListener(this);
        LinearLayout call = (LinearLayout) popup.findViewById(R.id.call);
        call.setOnClickListener(this);
        LinearLayout update = (LinearLayout) popup.findViewById(R.id.updatePatient);
        update.setOnClickListener(this);
        LinearLayout swhichAlarms = (LinearLayout) popup.findViewById(R.id.desactivateAllAlarm);
        swhichAlarms.setOnClickListener(this);
        LinearLayout clearFromGroups = (LinearLayout) popup.findViewById(R.id.clearAllAlarm);
        clearFromGroups.setOnClickListener(this);
        LinearLayout copyNumber = (LinearLayout) popup.findViewById(R.id.copyPatientNumber);
        copyNumber.setOnClickListener(this);
        LinearLayout deletePatient = (LinearLayout) popup.findViewById(R.id.deletePatient);
        deletePatient.setOnClickListener(this);

        builder.setView(popup);
        Dialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onClick(View v) {

        helper = new MyDbHelper(getActivity());

        switch (v.getId()){
            case R.id.addNewAlarm:
                Intent intent = new Intent(getActivity(), AddAgendaActivity.class);

                Calendar cal = Calendar.getInstance();
                AgendaALarm alarm = new AgendaALarm();
                Log.e("___id init ",patient.getIdPatient());
                    alarm.setIdPatient(Integer.valueOf(patient.getIdPatient()));
                    alarm.setState(true);
                    alarm.setDateMillisWakeUp(cal.getTimeInMillis());

                //at 1 to notify AddAgendaActivity.java that this is not a update but a new Alarm
                alarm.setDateMillisNow(1);

                       intent.putExtra(MainActivity.EXTRA_EDIT_AGENDA_ALARM, alarm);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_ACTIVITY_AGENDA);
                break;
            case R.id.sendSms:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SendMessageToPatient.newInstance(patient).show(fragmentManager, "Tag patient");
                dismiss();
                break;
            case R.id.call:
                Intent itCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+patient.getTelephone()));
                startActivity(itCall);
                dismiss();
                break;
            case R.id.updatePatient:
                Intent intentUpdate = new Intent(getActivity(), AddPatientActivity.class);
                    intentUpdate.putExtra(MainActivity.EXTRA_EDIT_PATIENT, patient);
                startActivityForResult(intentUpdate, MainActivity.REQUEST_CODE_ACTIVITY_PATIENT);
                break;
            case R.id.desactivateAllAlarm:
                helper.toggleAllAlarmByPatientId(patient.getIdPatient(), false, getActivity());
                ((MainActivity)getActivity()).dataRefreshed();
                dismiss();
                break;
            case R.id.clearAllAlarm:
                dismiss();
                break;
            case R.id.copyPatientNumber:
                dismiss();
                break;
            case R.id.deletePatient:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Effacer le patient "+ patient.getNom().toUpperCase()+" "+patient.getPrenom()+" ?");
                builder.setMessage("Toutes ses planifications seront egalement suprimés");
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dismiss();
                    }
                });

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deletePatient(patient.getIdPatient(), getActivity());
                        ((MainActivity)getActivity()).dataRefreshed();
                        dismiss();

                        Toast.makeText(getActivity(), "Patient suplimé", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
                break;
        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            ((MainActivity) getActivity()).dataRefreshed();
            this.dismiss();
        }catch (Exception ex){ }
    }
}
