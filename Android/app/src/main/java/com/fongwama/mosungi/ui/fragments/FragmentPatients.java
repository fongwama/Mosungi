package com.fongwama.mosungi.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.activity.HomeActivity;
import com.fongwama.mosungi.ui.adapter.PatientListAdapter;

/**
 * Created by Karl on 01/07/2016.
 */
public class FragmentPatients extends Fragment implements HomeActivity.DataRefreshListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private MyDbHelper myDbHelper;
    private List<Patient> listPatients;
    private PatientListAdapter patientListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

        myDbHelper   = new MyDbHelper(getActivity());
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
        ((HomeActivity) activity).registerDataRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomeActivity) getActivity()).unregisterDataRefreshListener(this);
    }
}

