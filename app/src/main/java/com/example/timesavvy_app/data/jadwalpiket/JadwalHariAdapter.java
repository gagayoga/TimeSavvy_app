package com.example.timesavvy_app.data.jadwalpiket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.data.datasiswa.DataSiswaPage;

import java.util.List;

public class JadwalHariAdapter extends RecyclerView.Adapter<JadwalHariAdapter.DayViewHolder> {

    private List<JadwalPiketResponse.Jadwal> dayList;
    private OnItemClickListener listener;
    private Context context;

    public JadwalHariAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDayList(List<JadwalPiketResponse.Jadwal> dayList) {
        this.dayList = dayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_jadwal_hari, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        JadwalPiketResponse.Jadwal day = dayList.get(position);
        holder.txtHari.setText("Jadwal " + day.getHari());
        holder.txtIdHari.setText(String.valueOf(day.getId()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DataSiswaPage.class);
            intent.putExtra("id_hari", day.getId());
            intent.putExtra("nama_hari", day.getHari());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dayList != null ? dayList.size() : 0;
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView txtHari, txtIdHari;
        RelativeLayout cardJadwal;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHari = itemView.findViewById(R.id.txtHari);
            txtIdHari = itemView.findViewById(R.id.txtIdHari);
            cardJadwal = itemView.findViewById(R.id.cardJadwal);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
