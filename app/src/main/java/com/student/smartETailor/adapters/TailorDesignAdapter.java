
package com.student.smartETailor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;

import java.util.ArrayList;

public class TailorDesignAdapter extends RecyclerView.Adapter<TailorDesignAdapter.MyViewHolder> {
 ArrayList<Design> designs;
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_design, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View v) {
            super(v);


        }
    }
}

