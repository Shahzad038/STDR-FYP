package com.student.smartETailor.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.models.Measurement;

import java.util.ArrayList;

public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MyViewHolder> {
    ArrayList<Measurement> measurements;
    Activity context;
    Boolean clickable;

    ArrayList<Measurement> selectedMeasurements;

    public MeasurementsAdapter(Activity activity, ArrayList<Measurement> measurements, Boolean clickable) {
        this.context = activity;
        this.clickable = clickable;
        this.measurements = measurements;
        this.selectedMeasurements = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        Measurement measurement = measurements.get(position);
        if (measurement.getPayment().isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_measurement_selected, parent, false));
        } else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_measurement, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int itemPosition = position;
        Measurement measurement = measurements.get(itemPosition);
        holder.tvNeck.setText("Neck: \t\t\t" + measurement.getNeck());
        holder.tvShoulders.setText("Shoulders: \t" + measurement.getShoulders());
        holder.tvSleeves.setText("Sleeves: \t\t" + measurement.getSleeves());
        holder.tvChest.setText("Chest: \t\t\t" + measurement.getChest());
        holder.tvWaist.setText("Waist: \t\t" + measurement.getWaist());
        holder.tvHips.setText("Hips: \t\t" + measurement.getHips());
        holder.tvInseam.setText("Inseam: \t" + measurement.getNeck());
        holder.tvThigh.setText("Thigh: \t\t" + measurement.getThigh());


    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvNeck, tvShoulders, tvSleeves, tvChest, tvWaist, tvHips, tvInseam, tvThigh;

        public MyViewHolder(@NonNull View v) {
            super(v);
            card = v.findViewById(R.id.card_measurement);
            tvNeck = v.findViewById(R.id.tv_measurement_neck);
            tvShoulders = v.findViewById(R.id.tv_measurement_shoulders);
            tvSleeves = v.findViewById(R.id.tv_measurement_sleeves);
            tvChest = v.findViewById(R.id.tv_measurement_chest);
            tvWaist = v.findViewById(R.id.tv_measurement_waist);
            tvHips = v.findViewById(R.id.tv_measurement_hips);
            tvInseam = v.findViewById(R.id.tv_measurement_inseam);
            tvThigh = v.findViewById(R.id.tv_measurement_thigh);


        }
    }
}