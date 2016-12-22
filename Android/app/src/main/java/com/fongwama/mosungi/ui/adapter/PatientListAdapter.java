package com.fongwama.mosungi.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.dialogfragment.PatientManagementMenu;
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

    public void setList(List<Patient> list){
        listPatients = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String nom = listPatients.get(position).getNom().toLowerCase();
        String prenom = listPatients.get(position).getPrenom().toLowerCase();

        //On met la première lettre du Nom en capital et le reste en minuscule
        String firstLetter = nom.substring(0,1).toUpperCase();
        String lastPart = nom.substring(1, (nom.length()));
        nom = firstLetter + lastPart;

        prenom = prenom.substring(0,1).toUpperCase() +prenom.substring(1, prenom.length());

        holder.nomPrenom.setText(nom+" "+prenom);
        holder.profilImageTxt.setText(nom.charAt(0)+"");
        holder.casPatient.setText(listPatients.get(position).getCasPatient());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

               PatientManagementMenu.newInstance(listPatients.get(position)).show(((FragmentActivity) v.getContext()).getSupportFragmentManager(),"tag_menu");
               return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPatients != null ? listPatients.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nomPrenom;
        public TextView profilImageTxt;
        public TextView casPatient;

        public ViewHolder(View itemLayout) {
            super(itemLayout);
            Typeface font = Typeface.createFromAsset(itemLayout.getContext().getAssets(), "Fibon_Sans_Regular.otf");

            nomPrenom = (TextView) itemLayout.findViewById(R.id.nom_prenom);
            nomPrenom.setTypeface(font);
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
