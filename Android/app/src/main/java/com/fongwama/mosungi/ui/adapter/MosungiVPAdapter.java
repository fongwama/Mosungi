package com.fongwama.mosungi.ui.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.ui.fragments.FragmentAgenda;
import com.fongwama.mosungi.ui.fragments.FragmentPatients;

public class MosungiVPAdapter extends FragmentPagerAdapter {

    Resources res;

    public MosungiVPAdapter(Resources res, FragmentManager fm) {
        super(fm);
        this.res = res;
    }

    @Override
    public Fragment getItem(int position) {

        Log.e("get item", "called");
        if(position==0)
             return new FragmentPatients();
        else if(position==1)
             return new FragmentAgenda();

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return res.getString(R.string.patient);
            case 1: return res.getString(R.string.agenda);
        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

}
