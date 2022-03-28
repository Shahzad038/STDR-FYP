package com.student.smartETailor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;
import java.util.ArrayList;

public class TailorDesignAdapter extends RecyclerView.Adapter<TailorDesignAdapter.MyViewHolder> {
    ArrayList<Design> designs;
    Context context;
    Boolean isHorizontal = false;


    public TailorDesignAdapter(Context context, ArrayList<Design> designs) {
        this.context = context;
        this.designs = designs;
    }


    public TailorDesignAdapter(Context context, ArrayList<Design> designs, Boolean isHorizontal) {
        this.context = context;
        this.designs = designs;
        this.isHorizontal = isHorizontal;
    }

    public TailorDesignAdapter(Context context, Boolean isHorizontal) {
        this.context = context;
        this.designs = new ArrayList<>();
        this.isHorizontal = isHorizontal;
    }

    public void updateDesign(ArrayList<Design> designs) {
        this.designs = designs;
        this.notifyItemRangeChanged(0, designs.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHorizontal) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_design_horizontal, parent, false));
        } else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_design, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Design design = designs.get(position);
        Glide.with(context).load(design.getDesigns().get(0)).centerCrop().into(holder.imgDesign);
        holder.tvName.setText(design.getName());
        if (design.isCustomization()) {
            holder.tvPrice.setText(design.getPrice() + "$");
        } else {
            holder.tvCustomizable.setVisibility(View.GONE);
            holder.tvPrice.setText(design.getMeasurements().get(0).getPayment()+"$");
        }

    }

    @Override
    public int getItemCount() {
        return designs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDesign;
        CardView cardDesign;
        TextView tvName, tvPrice, tvCustomizable;

        public MyViewHolder(@NonNull View v) {
            super(v);
            imgDesign = v.findViewById(R.id.img_design);
            cardDesign = v.findViewById(R.id.card_design);
            tvName = v.findViewById(R.id.tv_design_name);
            tvPrice = v.findViewById(R.id.tv_design_price);
            tvCustomizable = v.findViewById(R.id.tv_design_customizable);
        }
    }
}
