package com.example.mommyhealthapp.Nurse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mommyhealthapp.Class.AppointmentDate;
import com.example.mommyhealthapp.Class.MommyDetail;
import com.example.mommyhealthapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AppointmentDateAdapter extends RecyclerView.Adapter<AppointmentDateAdapter.MyViewHolder> {
    private List<AppointmentDate> listMD;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_date_record, parent, false);
        return new MyViewHolder(resultView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppointmentDate ad = listMD.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        holder.txtViewAppointDate.setText(dateFormat.format(ad.getAppointmentDate()));
        holder.txtViewAppointTime.setText(timeFormat.format(ad.getAppointmentTime()));
    }

    @Override
    public int getItemCount() {
        return listMD.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtViewAppointDate, txtViewAppointTime;
        public MyViewHolder(View view)
        {
            super(view);
            txtViewAppointDate = (TextView)view.findViewById(R.id.txtViewAppointmentDate);
            txtViewAppointTime = (TextView)view.findViewById(R.id.txtViewAppointTime);
        }
    }

    public AppointmentDateAdapter(List<AppointmentDate> listmd){
        listMD = listmd;
    }
}
