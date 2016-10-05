package com.fongwama.mosungi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.ui.fragments.FragmentAgenda;
import com.fongwama.mosungi.ui.fragments.FragmentPatients;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private com.github.clans.fab.FloatingActionButton fabAddInter;
    private com.github.clans.fab.FloatingActionButton fabAddPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //final MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        //Cursor cursorPatient        = myDbHelper.getAllPatient();
        //Log.i("PATIENT", cursorPatient.getCount()+"");

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        //Ajout des deux TAB utilisés dans l'application
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch(position)
                {
                    case 0: return new FragmentPatients();
                    case 1: return new FragmentAgenda();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position)
                {
                    case 0: return getString(R.string.patient);
                    case 1: return getString(R.string.agenda);
                }
                return super.getPageTitle(position);
            }
        });

        //Synchronisation du TabLayout avec le ViewPager
        tabLayout.setupWithViewPager(viewPager);


        fabAddInter = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_inter);
        fabAddPatient = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_patient);
        fabAddInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddAgendaActivity.class));
            }
        });

        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddPatientActivity.class));
            }
        });
    }

}
