package com.student.smartETailor.ui.Tailor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.MeasurementsAdapter;
/*import com.student.smartETailor.adapters.TailorDesignAdapter;*/
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.ui.AddMeasurementsActivity;
import com.student.smartETailor.utils.UsersUtils;

import java.util.ArrayList;

public class TailorMeasurementsActivity extends AppCompatActivity {
    private final String TAG = TailorMeasurementsActivity.class.getSimpleName();

    TextView tvAddMeasurement;
    RecyclerView rvMeasurements;

    ArrayList<Measurement> measurements;

    MeasurementsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_measurements);
        setting();
        setListener();
        fetchMeasurements();
    }

    private void fetchMeasurements() {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(UsersUtils.getInstance(this).fetchUser().getUID())
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
            startActivity(new Intent(TailorMeasurementsActivity.this, AddMeasurementsActivity.class));
        });
    }

    private void setting() {
        tvAddMeasurement = findViewById(R.id.tv_add_measurements);
        rvMeasurements = findViewById(R.id.rv_measurements);
        measurements = new ArrayList<>();
        adapter = new MeasurementsAdapter(this, measurements, false);
        rvMeasurements.setAdapter(adapter);
    }
}