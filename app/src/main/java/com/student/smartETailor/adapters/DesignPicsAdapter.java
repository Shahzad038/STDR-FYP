package com.student.smartETailor.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class DesignPicsAdapter extends RecyclerView.Adapter<DesignPicsAdapter.MyViewHolder> {
    ArrayList<String> images;
    Activity context;

    public DesignPicsAdapter(Activity activity, ArrayList<String> images) {
        this.context = activity;
        this.images = images;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public DesignPicsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_add_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String image = images.get(position);
        try {
            Glide.with(context).load(image).centerCrop().into(holder.designImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView designImage;

        public MyViewHolder(@NonNull View v) {
            super(v);
            designImage = v.findViewById(R.id.img_add_design_image);
        }
    }
}
