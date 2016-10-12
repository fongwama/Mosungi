package com.fongwama.mosungi.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.adapter.PatientListAdapter;

/**
 * Created by Karl on 01/07/2016.
 */
public class FragmentPatients extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        final MyDbHelper myDbHelper             = new MyDbHelper(getActivity());
        List<Patient> listPatients              = myDbHelper.getAllPatient();
        PatientListAdapter patientListAdapter   = new PatientListAdapter(listPatients);
        recyclerView.setAdapter(patientListAdapter);

        return view;
    }
}
