package com.fongwama.mosungi.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.model.Patient;

/**
 * Created by Karl on 28/07/2016.
 */
public class SpinnerAdapter extends BaseAdapter{
    Context context;
    List<Patient> listPatient;
    LayoutInflater layoutInflater;
    private int lastPosition = -1;

    public SpinnerAdapter(Context context, List<Patient> listPatient) {
        this.context = context;
        this.listPatient = listPatient;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listPatient.size();
    }

    @Override
    public Object getItem(int position) {
        return listPatient.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder
    {
        TextView id;
        TextView nomPrenom;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.item_spinner, null);
            holder = new ViewHolder();

            //initialisation des vues

            holder.id         = (TextView) convertView.findViewById(R.id.id_item);
            holder.nomPrenom  = (TextView) convertView.findViewById(R.id.nom_prenom_item);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Patient patient = listPatient.get(position);
        holder.id.setText(patient.getIdPatient());
        holder.nomPrenom.setText(patient.getNom()+" "+patient.getPrenom());

        lastPosition = position;
        return convertView;


    }
}
