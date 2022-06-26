package com.student.smartETailor.ui;

import static com.student.smartETailor.ui.DesignDetailsActivity.EXTRA_DESIGN;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.MeasurementsSimpleAdapter;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;

import java.util.ArrayList;
import java.util.List;

public class SelectOneMeasurementsActivity extends AppCompatActivity {
    private RecyclerView rvMyMeasurements, rvDesignMeasurements;
    private MeasurementsSimpleAdapter adapterMyMeasurements;
    private MeasurementsSimpleAdapter adapterDesignMeasurements;
    private Design design;
    private ArrayList<Measurement> myMeasurements;
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_one_measurement);
        design = (Design) getIntent().getSerializableExtra(EXTRA_DESIGN);
        myMeasurements = new ArrayList<>();
        if (design == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            setDesignMesureAdapter(design.getMeasurements());
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("Measurements")){
                                DataSnapshot measurementsSnapshot = snapshot.child("Measurements");
                                for (DataSnapshot dataSnapshot : measurementsSnapshot.getChildren()) {
                                    myMeasurements.add(dataSnapshot.getValue(Measurement.class));
                                }
                                setMyMesureAdapter(myMeasurements);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    private void setDesignMesureAdapter(ArrayList<Measurement> measurements) {
        rvDesignMeasurements = findViewById(R.id.rv_select_measurement_design);
        adapterDesignMeasurements = new MeasurementsSimpleAdapter(this, measurements,design);
        rvDesignMeasurements.setAdapter(adapterDesignMeasurements);
    }

    private void setMyMesureAdapter(ArrayList<Measurement> measurements) {
        rvMyMeasurements = findViewById(R.id.rv_select_measurement_my);
        adapterMyMeasurements = new MeasurementsSimpleAdapter(this, measurements,design);
        rvMyMeasurements.setAdapter(adapterMyMeasurements);
    }


}