package com.student.smartETailor.ui;

import static com.student.smartETailor.ui.DesignDetailsActivity.EXTRA_DESIGN;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.adapters.MeasurementsSimpleAdapter;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;

import java.util.ArrayList;

public class MeasurementsActivity extends AppCompatActivity {
    RecyclerView rvMeasurements;
    MeasurementsSimpleAdapter adapter;
    Design design;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);
        design = (Design) getIntent().getSerializableExtra(EXTRA_DESIGN);
        if (design == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            setAdapter(design.getMeasurements());
        }
    }

    private void setAdapter(ArrayList<Measurement> measurements) {
        rvMeasurements = findViewById(R.id.rv_measurements);
        adapter = new MeasurementsSimpleAdapter(this, measurements, design);
        rvMeasurements.setAdapter(adapter);
    }
}