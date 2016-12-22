package com.fongwama.mosungi.ui.dialogfragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.ui.activity.AddAgendaActivity;
import com.fongwama.mosungi.ui.activity.MainActivity;

import static com.fongwama.mosungi.ui.activity.MainActivity.REQUEST_CODE_ACTIVITY_AGENDA;
import static com.fongwama.mosungi.ui.activity.MainActivity.REQUEST_CODE_ACTIVITY_PATIENT;


public class AlarmManagementMenu extends DialogFragment implements View.OnClickListener{

    private AgendaALarm alarm;
    private MyDbHelper helper;

    public static DialogFragment newInstance(AgendaALarm alarm)
    {
        DialogFragment dialogFragment = new AlarmManagementMenu();
        Bundle args = new Bundle();
        args.putParcelable("ALARM", alarm);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        alarm = getArguments().getParcelable("ALARM");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //patient.getNom() patient.getPrenom()

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View popup = inflater.inflate(R.layout.dialog_view_alarm_menu, null, false);

        TextView title = (TextView) popup.findViewById(R.id.title);
        title.setText(alarm.getTitre());

        LinearLayout updateAlarm = (LinearLayout) popup.findViewById(R.id.alarm_update);
            updateAlarm.setOnClickListener(this);
        LinearLayout switchAlarm = (LinearLayout) popup.findViewById(R.id.alarm_switch);
            switchAlarm.setOnClickListener(this);
        LinearLayout triggerAlarm = (LinearLayout) popup.findViewById(R.id.alarm_trigger);
            triggerAlarm.setOnClickListener(this);
        LinearLayout deleteAlarm = (LinearLayout) popup.findViewById(R.id.alarm_delete);
            deleteAlarm.setOnClickListener(this);

        builder.setView(popup);
        Dialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onClick(View v) {

        helper = new MyDbHelper(getActivity());
        switch (v.getId()){
            case R.id.alarm_update:
                Intent intent = new Intent(getActivity(), AddAgendaActivity.class);
                intent.putExtra(MainActivity.EXTRA_EDIT_AGENDA_ALARM, alarm);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_ACTIVITY_AGENDA);
                break;
            case R.id.alarm_switch:
                 helper.toggleAlarmOff(alarm,getActivity());

                ((MainActivity)getActivity()).dataRefreshed();
                break;
            case R.id.alarm_trigger:
                //alarm
                //getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_ACTIVITY_AGENDA);

                break;
            case R.id.alarm_delete:
                helper.deleteAgendaAlarm(alarm.getId()+"", getActivity());
                ((MainActivity)getActivity()).dataRefreshed();
                dismiss();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            ((MainActivity) getActivity()).dataRefreshed();

            if(requestCode == REQUEST_CODE_ACTIVITY_AGENDA)
                ((MainActivity) getActivity()).viewPager.setCurrentItem(1, true);
            else if(requestCode == REQUEST_CODE_ACTIVITY_PATIENT)
                ((MainActivity) getActivity()).viewPager.setCurrentItem(0, true);

            this.dismiss();

        }catch (Exception ex){ex.printStackTrace(); }
    }
}
