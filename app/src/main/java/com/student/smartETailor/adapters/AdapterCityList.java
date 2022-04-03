package com.student.smartETailor.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.student.smartETailor.R;
import com.student.smartETailor.ui.SignupActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterCityList extends RecyclerView.Adapter<AdapterCityList.ViewHolder> implements Filterable {


    private Context context;
    private List<String> citiesList;
    private Dialog mDialog;

    private List<String> exampleListFull;


    // RecyclerView recyclerView;
    public AdapterCityList(Context context, List<String> citiesList, Dialog mDialog) {
        this.context = context;
        this.citiesList = citiesList;
        this.mDialog = mDialog;
        this.exampleListFull = new ArrayList<>(citiesList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_list_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cityListTxt.setText(citiesList.get(position));


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (citiesList.get(position)) {
                    case "Pakistan\t(+92)":
                        SignupActivity.tvSelectCountry.setText("+92");
                        break;
                    case "India\t(+91)":
                        SignupActivity.tvSelectCountry.setText("+91");
                        break;
                    case "US \t(+1)":
                        SignupActivity.tvSelectCountry.setText("+1");
                        break;
                }

                citiesList.clear();
                citiesList.addAll(new ArrayList<>(exampleListFull));
                mDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter = new Filter() {
        // Runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<String> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(exampleListFull);
            } else {
                for (String city : exampleListFull) {
                    if (city.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(city);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        // Runs on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            citiesList.clear();
            citiesList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityListTxt;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityListTxt = (TextView) itemView.findViewById(R.id.txt_city);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.city_list_rel);
        }
    }
}
