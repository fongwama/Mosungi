package com.fongwama.mosungi.ui.dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.model.Patient;

/**
 * Created by Karl on 30/07/2016.
 */
public class SendMessageToPatient extends DialogFragment{

    private Patient patient;
    public String messageText;

    public static DialogFragment newInstance(Patient patient)
    {
        DialogFragment dialogFragment = new SendMessageToPatient();
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
        builder.setTitle(getString(R.string.to)+" "+patient.getNom()+" "+patient.getPrenom()+"\n"+patient.getTelephone());
        builder.setIcon(R.drawable.ic_textsms_24dp);
//        builder.setView(getContentView());

        final TextInputEditText input = new TextInputEditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(16, 16, 16, 16);

        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), input.getText().toString(), Toast.LENGTH_SHORT).show();
                if (input.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Ajouter du contenu", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    messageText = input.getText().toString();
                    SmsManager.getDefault().sendTextMessage(patient.getTelephone(), null, messageText, null, null);
                    dialog.dismiss();
                }

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);

        Dialog dialog = builder.create();

        return dialog;

    }

    /*private View getContentView()
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_send_message, null);

        TextInputEditText message = (TextInputEditText)view.findViewById(R.id.text);
        String messageText = message.getText().toString();

        return view;
    }*/
}
