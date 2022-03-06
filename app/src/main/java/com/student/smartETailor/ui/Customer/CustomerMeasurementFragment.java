package com.student.smartETailor.ui.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.MeasurementsAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.AddMeasurementsActivity;
import com.student.smartETailor.ui.Tailor.TailorMeasurementsActivity;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import java.util.ArrayList;

public class CustomerMeasurementFragment extends Fragment {
    private final String TAG = TailorMeasurementsActivity.class.getSimpleName();
    User user;
    TextView tvAddMeasurement;
    RecyclerView rvMeasurements;
    ArrayList<Measurement> measurements;
    MeasurementsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_measurements, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setting(view);
        setListener();
        fetchMeasurements();
    }

    private void fetchMeasurements() {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(user.getUID())
                .child(Const.DB_USERS_MEASUREMENTS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        measurements.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                measurements.add(snap.getValue(Measurement.class));
                            }
                        }
                        adapter.notifyItemRangeChanged(0, measurements.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    private void setListener() {
        tvAddMeasurement.setOnClickListener(view -> {
            startActivity(new Intent(this.requireActivity(), AddMeasurementsActivity.class));
        });
    }


    private void setting(View view) {
        tvAddMeasurement = view.findViewById(R.id.tv_add_measurements);
        rvMeasurements = view.findViewById(R.id.rv_measurements);
        measurements = new ArrayList<>();
        adapter = new MeasurementsAdapter(this.requireActivity(), measurements, false);
        rvMeasurements.setAdapter(adapter);
    }
}