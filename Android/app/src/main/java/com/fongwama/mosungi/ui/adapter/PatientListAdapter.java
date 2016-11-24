package com.fongwama.mosungi.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.dialogfragment.SendMessageToPatient;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Karl on 27/07/2016.
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder>{

    public List<Patient> listPatients = new ArrayList<>();

    public PatientListAdapter(List<Patient> listPatients) {
        this.listPatients = listPatients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_patient, null);
        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nomPrenom.setText(listPatients.get(position).getNom()+" "+listPatients.get(position).getPrenom());
        holder.profilImageTxt.setText(listPatients.get(position).getNom());
        holder.casPatient.setText(listPatients.get(position).getCasPatient());
    }

    @Override
    public int getItemCount() {
        return listPatients != null ? listPatients.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nomPrenom;
        public TextView profilImageTxt;
        public TextView casPatient;

        public ViewHolder(View itemLayout)
        {
            super(itemLayout);
            nomPrenom = (TextView) itemLayout.findViewById(R.id.nom_prenom);
            casPatient = (TextView) itemLayout.findViewById(R.id.categorie);
            profilImageTxt = (TextView) itemLayout.findViewById(R.id.profilTextImg);

            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context c = v.getContext();
            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();

            Patient patient = listPatients.get(getPosition());

            SendMessageToPatient.newInstance(patient).show(fragmentManager, "Tag");
        }
    }
}
