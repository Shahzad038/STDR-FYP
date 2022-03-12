package com.student.smartETailor.adapters;

import android.app.Activity;
import android.content.Context;
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
import com.student.smartETailor.models.Measurement;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class TailorAddDesignsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Uri> images;
    Activity context;

    public void addNewImage(Uri uri) {
        images.add(uri);
        this.notifyItemRangeChanged(0, images.size());
    }

    public ArrayList<Uri> getImages() {
        return images;
    }

    public TailorAddDesignsListAdapter(Activity activity) {
        this.context = activity;
        this.images = new ArrayList<>();
        addNewImage(null);
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new MyViewHolderPlus(LayoutInflater.from(context).inflate(R.layout.template_add_design_plus, parent, false));
        } else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_add_design, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Uri image = images.get(position);

        if (holder.getItemViewType() == 1) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), image);
                ((MyViewHolder) holder).designImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (holder.getItemViewType() == 0) {
            ((MyViewHolderPlus) holder).addNewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.startPickImageActivity(context);
                }
            });
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

    class MyViewHolderPlus extends RecyclerView.ViewHolder {
        ImageView addNewImage;

        public MyViewHolderPlus(@NonNull View v) {
            super(v);
            addNewImage = v.findViewById(R.id.img_add_design_plus);
        }
    }
}
