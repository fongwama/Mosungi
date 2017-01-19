package com.fongwama.mosungi.ui.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.data.MyDbHelper;
import com.fongwama.mosungi.functions.MyFunctions;
import com.fongwama.mosungi.model.AgendaALarm;
import com.fongwama.mosungi.model.Patient;
import com.fongwama.mosungi.ui.dialogfragment.AlarmManagementMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class AgendaAlarmListAdapter extends RecyclerView.Adapter<AgendaAlarmListAdapter.ViewHolder>{

    public List<AgendaALarm> listAlarms = new ArrayList<>();
    Context context;
    MyDbHelper helper;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    public static final String EXTRA_ALARM_DATABASE_ID = "mosungi.alarm.db.id";
    public static final int REQUEST_CODE_ALARM = 8;

    private AlarmOnClickCallback mFragmentCallback;

    public interface AlarmOnClickCallback{
        void onItemClick(AgendaALarm aLarm);
    }

    public AgendaAlarmListAdapter(List<AgendaALarm> listAlarms, Context context, MyDbHelper helper) {
        this.listAlarms = listAlarms;
        this.context = context;
        this.helper  = helper;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    public void setAgendaAlarmListAdapter(AlarmOnClickCallback mCallback){
        this.mFragmentCallback = mCallback;
    }

    public void setList(List<AgendaALarm> list){
        listAlarms = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_alarm, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Calendar calendar =  GregorianCalendar.getInstance(Locale.FRENCH);
        calendar.setTimeInMillis(listAlarms.get(position).getDateMillisWakeUp());

        holder.alarmState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //On demande les permisions si l'alarme actuel veut passer de OFF à ON
                if( ! listAlarms.get(position).isState()){
                    if(MyFunctions.checkSMSPermissions(context))
                        Log.i("Permission","granted");
                    else {
                        Log.e("Permission", "Denied");
                        return;
                    }
                }

                //Si --le temps programé n'est pas au futur ET --l'utilisateur veut activer l'alarm
                //TODO : lui faire comprendre que c'est impossible | ensuite le proposer de modifier la date/l'heure
                if( isChecked &&
                        System.currentTimeMillis() > listAlarms.get(position).getDateMillisWakeUp()
                        ){

                    //ceci évite que le Toast soit affiché lors du peuplement de la liste
                    // Mais, uniquement lors d'un changement d'état sur un item de la liste
                    if(holder.tvTitre.getText().toString().isEmpty())
                        return;

                    Toast.makeText(context, context.getResources().getString(R.string.error_planification_date), Toast.LENGTH_SHORT).show();

                    Log.e("adapter errre id: ", listAlarms.get(position).getId()+"");
                    Log.e("adapter errre id: ", listAlarms.get(position).isState()+"");

                    // 1 second before updating the view to unchecked
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AgendaAlarmListAdapter.this.notifyItemChanged(position);
                        }
                    }, 1000);

                }
                else if(isChecked){
                    Log.e("adapter errre id: ", "adapter succeeded on");
                    listAlarms.get(position).setState(true);
                    helper.toggleAlarmOn(listAlarms.get(position), context);
                }
                else {
                    Log.e("adapter errre id: ", "adapter succeeded off");
                    listAlarms.get(position).setState(false);
                    helper.toggleAlarmOff(listAlarms.get(position), context);
                }
            }

        });

        holder.tvDate.setText(MyFunctions.getReadableLongDate(calendar.getTimeInMillis()));
        holder.tvDescription.setText(listAlarms.get(position).getTitre());

        //si le temps d'activation est déjà écoulé
        if(System.currentTimeMillis() > listAlarms.get(position).getDateMillisWakeUp()){
            holder.alarmState.setChecked(false);
            helper.toggleAlarmOff(listAlarms.get(position), context);
        }
        //Si
        else if(listAlarms.get(position).isState())
            holder.alarmState.setChecked(true);


        final Patient p = helper.getPatientById(listAlarms.get(position).getIdPatient()+"");
        if(p!=null){
            String nom = p.getNom().toLowerCase();
            String prenom = p.getPrenom().toLowerCase();

            //On met la première lettre du Nom en capital et le reste en minuscule
            nom = (nom.charAt(0)+"").toUpperCase()+nom.substring(1, nom.length());
            prenom = (prenom.charAt(0)+"").toUpperCase()+prenom.substring(1, prenom.length());
            holder.tvTitre.setText(nom+" "+prenom);
        }

        holder.tvTimeHour.setText(MyFunctions.get24Hour(calendar.getTimeInMillis())+"H");
        holder.tvTimeMin.setText(MyFunctions.getFormatedMinutes(calendar.getTimeInMillis()));


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlarmManagementMenu.newInstance(listAlarms.get(position)).show(((FragmentActivity) v.getContext()).getSupportFragmentManager(),"tag_menu_alarm");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAlarms != null ? listAlarms.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvDate;
        private TextView tvTimeHour;
        private TextView tvTimeMin;
        private TextView tvTitre;
        private TextView tvDescription;
        private Switch alarmState;

        public ViewHolder(View itemLayout)
        {
            super(itemLayout);
            tvDate = (TextView) itemLayout.findViewById(R.id.date);
            tvTimeHour= (TextView) itemLayout.findViewById(R.id.timeHour);
            tvTimeMin = (TextView) itemLayout.findViewById(R.id.timeMin);
            tvTitre   = (TextView) itemLayout.findViewById(R.id.nomPatient);
            tvDescription    = (TextView) itemLayout.findViewById(R.id.description);
            alarmState  = (Switch) itemLayout.findViewById(R.id.switcher);

            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(mFragmentCallback!=null)
                mFragmentCallback.onItemClick(listAlarms.get(getAdapterPosition()));
            }
    }
}