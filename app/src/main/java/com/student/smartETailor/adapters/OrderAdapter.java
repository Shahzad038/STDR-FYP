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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    ArrayList<OrderModel> orders;
    Context context;

    public OrderAdapter(Context context, ArrayList<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderModel order = orders.get(position);
        holder.tvName.setText("From: "  + order.getCustomerName());
        holder.tvPrice.setText("Price: " + order.getPrice() + "$");
        holder.tvEmail.setText("Email: "  + order.getCustomerEmail());
        holder.tvPhone.setText("Phone: "  + order.getCustomerPhone());
        holder.tvMode.setText("Payment Mode: "  + order.getPaymentMode());

        Measurement measurement = order.getMeasurement();
        holder.tvNeck.setText("Neck: \t\t\t" + measurement.getNeck()+"inches");
        holder.tvShoulders.setText("Shoulders: \t" + measurement.getShoulders()+"inches");
        holder.tvSleeves.setText("Sleeves: \t\t" + measurement.getSleeves()+"inches");
        holder.tvChest.setText("Chest: \t\t\t" + measurement.getChest()+"inches");
        holder.tvWaist.setText("Waist: \t\t" + measurement.getWaist()+"inches");
        holder.tvHips.setText("Hips: \t\t" + measurement.getHips()+"inches");
        holder.tvInseam.setText("Inseam: \t" + measurement.getNeck()+"inches");
        holder.tvThigh.setText("Thigh: \t\t" + measurement.getThigh()+"inches");

        if (order.getStatus().equalsIgnoreCase(Const.ORDER_STATUS_COMPLETED)){
            holder.btnComplete.setVisibility(View.GONE);
        }

        holder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
                FirebaseDatabase.getInstance().getReference("Orders")
                        .child(order.getOrderId())
                        .child("status")
                        .setValue(Const.ORDER_STATUS_COMPLETED)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Order marked as complete", Toast.LENGTH_SHORT).show();
                        holder.btnComplete.setVisibility(View.GONE);
                    }
                });

            }
        });



        holder.rlDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DesignDetailsActivity.class);
                intent.putExtra(DesignDetailsActivity.EXTRA_DESIGN, order.getDesign());
                intent.putExtra("from","order");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice,tvEmail, tvPhone, tvMode;
        TextView tvNeck, tvShoulders, tvSleeves, tvChest, tvWaist, tvHips, tvInseam, tvThigh;
        private Button btnComplete;
        private RelativeLayout rlDesign;
        private ProgressBar progressBar;

        public MyViewHolder(@NonNull View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
            tvName = v.findViewById(R.id.tv_customer_name);
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
