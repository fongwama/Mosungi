package com.fongwama.mosungi.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.ui.adapter.MosungiVPAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    public ViewPager viewPager;
    private MosungiVPAdapter adapter;

    private com.github.clans.fab.FloatingActionMenu fabContainer;
    private com.github.clans.fab.FloatingActionButton fabAddAlarm;
    private com.github.clans.fab.FloatingActionButton fabAddPatient;

    public static final int REQUEST_CODE_ACTIVITY_AGENDA = 1;
    public static final int REQUEST_CODE_ACTIVITY_PATIENT = 2;
    public static final String EXTRA_EDIT_AGENDA_ALARM = "mosungi.edit.alarm";
    public static final String EXTRA_EDIT_PATIENT = "mosungi.edit.patient";

    private List<DataRefreshListener> mListeners;

    public interface DataRefreshListener {
        void onDataRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources() .getColor(R.color.colorPrimary)));

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


        fabContainer = (com.github.clans.fab.FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
        fabAddAlarm = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_inter);
        fabAddPatient = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_patient);
        fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHelper helper = new MyDbHelper(MainActivity.this);

                if (helper.getPatientCount()>0)
                startActivityForResult(new Intent(MainActivity.this, AddAgendaActivity.class), REQUEST_CODE_ACTIVITY_AGENDA);
                else Toast.makeText(MainActivity.this,getResources().getString(R.string.alert_patients_count),Toast.LENGTH_SHORT).show();
            }
        });

        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddPatientActivity.class), REQUEST_CODE_ACTIVITY_PATIENT);
            }
        });

        fabContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    if(fabContainer.isOpened())
                        fabContainer.close(true);
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

        Log.e("___data refreshed"," calles");
    }

    public synchronized void onAlarmItemClick() {
        for (DataRefreshListener listener : mListeners) {
            listener.onDataRefresh();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("result","activity has resulted");

         //refresh the view to show new added items
        //Par la suite, il faudra parametrer dataRefreshed()
        //  de telle sorte qu'il ne reactualise plus tous deux fragments
        if(requestCode == REQUEST_CODE_ACTIVITY_PATIENT){
            dataRefreshed();
            viewPager.setCurrentItem(0);
        }
        else if(requestCode == REQUEST_CODE_ACTIVITY_AGENDA){
            dataRefreshed();
            viewPager.setCurrentItem(1);
        }

        if(fabContainer!= null && fabContainer.isOpened())
            fabContainer.close(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        if(fabContainer.isOpened())
            fabContainer.close(true);
        else
            super.onBackPressed();
    }
}
