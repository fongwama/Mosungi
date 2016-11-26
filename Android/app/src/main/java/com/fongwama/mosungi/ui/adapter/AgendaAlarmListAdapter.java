package com.fongwama.mosungi.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.dialogfragment.SendMessageToPatient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 27/07/2016.
 */
public class AgendaAlarmListAdapter extends RecyclerView.Adapter<AgendaAlarmListAdapter.ViewHolder>{

    public List<AgendaALarm> listAlarms = new ArrayList<>();

    public AgendaAlarmListAdapter(List<AgendaALarm> listAlarms) {
        this.listAlarms = listAlarms;
    }

    public void setList(List<AgendaALarm> list){
        listAlarms = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_alarm, null);
        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.nomPrenom.setText(listAlarms.get(position).getNom()+" "+listAlarms.get(position).getPrenom());
        //holder.profilImageTxt.setText(listAlarms.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return listAlarms != null ? listAlarms.size() : 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvDate;
        public TextView tvTimeHour;
        public TextView tvTimeMin;
        public TextView tvTitre;
        public TextView tvDescription;

        public ViewHolder(View itemLayout)
        {
            super(itemLayout);
            tvDate = (TextView) itemLayout.findViewById(R.id.date);
            tvTimeHour= (TextView) itemLayout.findViewById(R.id.timeHour);
            tvTimeMin = (TextView) itemLayout.findViewById(R.id.timeMin);
            tvTitre   = (TextView) itemLayout.findViewById(R.id.titre);
            tvDescription    = (TextView) itemLayout.findViewById(R.id.description);

            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context c = v.getContext();
            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();

            AgendaALarm alarm = listAlarms.get(getPosition());

            //ACtion
        }
    }
}
