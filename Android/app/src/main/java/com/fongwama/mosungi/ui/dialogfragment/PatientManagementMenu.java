package com.fongwama.mosungi.ui.dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.model.Patient;

/**
 * Created by Karl on 30/07/2016.
 */
public class PatientManagementMenu extends DialogFragment{

    private Patient patient;

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

        builder.setView(popup);
        Dialog dialog = builder.create();

        return dialog;
    }

}
