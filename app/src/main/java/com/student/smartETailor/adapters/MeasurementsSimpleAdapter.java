package com.student.smartETailor.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.models.OrderModel;
import com.student.smartETailor.ui.PlaceOrderActivity;
import com.student.smartETailor.utils.UsersUtils;

import java.util.ArrayList;

public class MeasurementsSimpleAdapter extends RecyclerView.Adapter<MeasurementsSimpleAdapter.MyViewHolder> {
    ArrayList<Measurement> measurements;
    Activity context;
    Design design;


    public MeasurementsSimpleAdapter(Activity activity, ArrayList<Measurement> measurements, Design design) {
        this.context = activity;
        this.measurements = measurements;
        this.design = design;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_measurement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int itemPosition = position;
        Measurement measurement = measurements.get(itemPosition);
        holder.tvNeck.setText("Neck: \t\t\t" + measurement.getNeck() + " inches");
        holder.tvShoulders.setText("Shoulders: \t" + measurement.getShoulders()+ " inches");
        holder.tvSleeves.setText("Sleeves: \t\t" + measurement.getSleeves()+ " inches");
        holder.tvChest.setText("Chest: \t\t\t" + measurement.getChest()+ " inches");
        holder.tvWaist.setText("Waist: \t\t" + measurement.getWaist()+ " inches");
        holder.tvHips.setText("Hips: \t\t" + measurement.getHips()+ " inches");
        holder.tvInseam.setText("Inseam: \t" + measurement.getNeck()+ " inches");
        holder.tvThigh.setText("Thigh: \t\t" + measurement.getThigh()+ " inches");


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UsersUtils.getInstance(context).fetchUser().getType().equals("customer")) {
                    context.startActivity(new Intent(context, PlaceOrderActivity.class)
                            .putExtra("Measurement",measurement)
                            .putExtra("Design",design));

                    OrderModel.getInstance().reset();
                    OrderModel.getInstance().setDesign(design);
                    OrderModel.getInstance().setMeasurement(measurement);
                }
            }
        });
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
