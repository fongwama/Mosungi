package com.fongwama.mosungi.ui.fragments;

import android.app.Activity;
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
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.activity.MainActivity;
import com.fongwama.mosungi.ui.adapter.PatientListAdapter;

import java.util.List;

public class FragmentPatients extends Fragment implements MainActivity.DataRefreshListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private MyDbHelper myDbHelper;
    private List<Patient> listPatients;
    private PatientListAdapter patientListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myDbHelper   = new MyDbHelper(getActivity());
    }

    public void refreshData(){
        if(patientListAdapter != null){

            listPatients = myDbHelper.getAllPatient();
            patientListAdapter.setList(listPatients);
            patientListAdapter.notifyDataSetChanged();
            Log.i("patients adapter", " notified");
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        listPatients = myDbHelper.getAllPatient();
        patientListAdapter = new PatientListAdapter(listPatients);
        recyclerView.setAdapter(patientListAdapter);

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
        try {
            ((MainActivity) getActivity()).unregisterDataRefreshListener(this);
        }catch (Exception ex){}
    }

}

