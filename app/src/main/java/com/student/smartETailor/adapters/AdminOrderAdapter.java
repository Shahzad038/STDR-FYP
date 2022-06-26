package com.student.smartETailor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.models.OrderModel;
import com.student.smartETailor.ui.DesignDetailsActivity;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.MyViewHolder> {
    ArrayList<OrderModel> orders;
    Context context;

    public AdminOrderAdapter(Context context, ArrayList<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_white, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderModel order = orders.get(position);
        holder.tvName.setText("From: " + order.getCustomerName());
        holder.tvPrice.setText("Price: " + order.getPrice() + "$");
        holder.tvEmail.setText("Email: " + order.getCustomerEmail());
        holder.tvPhone.setText("Phone: " + order.getCustomerPhone());
        holder.tvMode.setText("Payment Mode: " + order.getPaymentMode());

        Measurement measurement = order.getMeasurement();
        holder.tvNeck.setText("Neck: \t\t\t" + measurement.getNeck() + "inches");
        holder.tvShoulders.setText("Shoulders: \t" + measurement.getShoulders() + "inches");
        holder.tvSleeves.setText("Sleeves: \t\t" + measurement.getSleeves() + "inches");
        holder.tvChest.setText("Chest: \t\t\t" + measurement.getChest() + "inches");
        holder.tvWaist.setText("Waist: \t\t" + measurement.getWaist() + "inches");
        holder.tvHips.setText("Hips: \t\t" + measurement.getHips() + "inches");
        holder.tvInseam.setText("Inseam: \t" + measurement.getNeck() + "inches");
        holder.tvThigh.setText("Thigh: \t\t" + measurement.getThigh() + "inches");


        holder.btnComplete.setVisibility(View.GONE);

        holder.tvStatus.setText("Status: " + order.getStatus());

        holder.rlDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DesignDetailsActivity.class);
                intent.putExtra(DesignDetailsActivity.EXTRA_DESIGN, order.getDesign());
                intent.putExtra("from", "order");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvEmail, tvPhone, tvMode;
        TextView tvNeck, tvShoulders, tvSleeves, tvChest, tvWaist, tvHips, tvInseam, tvThigh,
        tvStatus;
        private Button btnComplete;
        private RelativeLayout rlDesign;
        private ProgressBar progressBar;

        public MyViewHolder(@NonNull View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
            tvName = v.findViewById(R.id.tv_customer_name);
            tvStatus = v.findViewById(R.id.tv_status);
            tvPrice = v.findViewById(R.id.tv_order_price);
            tvEmail = v.findViewById(R.id.tv_customer_email);
            tvPhone = v.findViewById(R.id.tv_customer_phone);
            tvMode = v.findViewById(R.id.tv_payment_mode);
            tvNeck = v.findViewById(R.id.tv_measurement_neck);
            tvShoulders = v.findViewById(R.id.tv_measurement_shoulders);
            tvSleeves = v.findViewById(R.id.tv_measurement_sleeves);
            tvChest = v.findViewById(R.id.tv_measurement_chest);
            tvWaist = v.findViewById(R.id.tv_measurement_waist);
            tvHips = v.findViewById(R.id.tv_measurement_hips);
            tvInseam = v.findViewById(R.id.tv_measurement_inseam);
            tvThigh = v.findViewById(R.id.tv_measurement_thigh);
            btnComplete = v.findViewById(R.id.btn_complete);
            rlDesign = v.findViewById(R.id.rl_design);
        }
    }
}
