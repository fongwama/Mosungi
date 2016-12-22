package com.fongwama.mosungi.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.ui.activity.AddAgendaActivity;
import com.fongwama.mosungi.ui.activity.MainActivity;
import com.fongwama.mosungi.ui.adapter.AgendaAlarmListAdapter;

import java.util.List;

public class FragmentAgenda extends Fragment implements MainActivity.DataRefreshListener, AgendaAlarmListAdapter.AlarmOnClickCallback {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private MyDbHelper myDbHelper;
    private AgendaAlarmListAdapter agendaAlarmAdapter;
    private List<AgendaALarm> listAlarms;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myDbHelper  = new MyDbHelper(getActivity());
    }

    public void refreshData(){
        if(agendaAlarmAdapter != null){
            listAlarms = myDbHelper.getAllAlarm();
            agendaAlarmAdapter.setList(listAlarms);
            agendaAlarmAdapter.notifyDataSetChanged();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        listAlarms  = myDbHelper.getAllAlarm();

        Log.e("agenda list", listAlarms.size()+"");
        agendaAlarmAdapter = new AgendaAlarmListAdapter(listAlarms, getActivity(), myDbHelper);

        //attach the click listener to the Adapter
        agendaAlarmAdapter.setAgendaAlarmListAdapter(this);

        recyclerView.setAdapter(agendaAlarmAdapter);

        return view;
    }

    @Override
    public void onDataRefresh() {
        refreshData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        ((MainActivity) activity).registerDataRefreshListener(this);
        }catch (Exception ex){         }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
           ((MainActivity) getActivity()).unregisterDataRefreshListener(this);
        }catch (Exception ex){         }
    }

    @Override
    public void onItemClick(AgendaALarm alarm) {
        Intent intent = new Intent(getActivity(), AddAgendaActivity.class);
        intent.putExtra(MainActivity.EXTRA_EDIT_AGENDA_ALARM, alarm);
        getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_ACTIVITY_AGENDA);
    }
}
