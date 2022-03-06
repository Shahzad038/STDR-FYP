package com.student.smartETailor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.models.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    ArrayList<Order> orders;
    Context context;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
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
        Order order = orders.get(position);
        holder.tvName.setText(order.getName());
        holder.tvPrice.setText("Price: " + order.getPrice());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;

        public MyViewHolder(@NonNull View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_order_name);
            tvPrice = v.findViewById(R.id.tv_order_price);

        }
    }
}
