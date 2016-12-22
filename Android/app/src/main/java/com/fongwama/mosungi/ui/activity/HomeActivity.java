package com.fongwama.mosungi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.ui.adapter.MosungiVPAdapter;
import com.fongwama.mosungi.ui.fragments.FragmentAgenda;
import com.fongwama.mosungi.ui.fragments.FragmentPatients;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MosungiVPAdapter adapter;

    private com.github.clans.fab.FloatingActionButton fabAddInter;
    private com.github.clans.fab.FloatingActionButton fabAddPatient;

    private static final int REQUEST_ACTIVITY_AGENDA = 1;
    private static final int REQUEST_ACTIVITY_PATIENT = 2;

    private List<DataRefreshListener> mListeners;

    public interface DataRefreshListener {
        void onDataRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListeners = new ArrayList<>();

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        //Ajout des deux TAB utilisés dans l'application
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        adapter = new MosungiVPAdapter(getResources(), getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //Synchronisation du TabLayout avec le ViewPager
        tabLayout.setupWithViewPager(viewPager);


        fabAddInter = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_inter);
        fabAddPatient = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_patient);
        fabAddInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeActivity.this, AddAgendaActivity.class), REQUEST_ACTIVITY_AGENDA);
            }
        });

        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeActivity.this, AddPatientActivity.class), REQUEST_ACTIVITY_PATIENT);
            }
        });
    }

    public synchronized void registerDataRefreshListener(DataRefreshListener listener) {
        mListeners.add(listener);
    }

    public synchronized void unregisterDataRefreshListener(DataRefreshListener listener) {
        mListeners.remove(listener);
    }

    public synchronized void dataRefreshed() {
        for (DataRefreshListener listener : mListeners) {
            listener.onDataRefresh();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("result","activity has resulted");

        //refresh the view to show new added items
        if(requestCode == REQUEST_ACTIVITY_AGENDA)
            dataRefreshed();

        else if(requestCode == REQUEST_ACTIVITY_PATIENT)
            dataRefreshed();
    }
}
