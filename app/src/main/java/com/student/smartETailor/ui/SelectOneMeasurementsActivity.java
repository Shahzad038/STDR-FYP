package com.student.smartETailor.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;

import java.util.ArrayList;

public class SelectOneMeasurementsActivity extends AppCompatActivity {
    private RecyclerView rvMyMeasurements, rvDesignMeasurements;
    private Design design;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_one_measurements);
        design = (Design) getIntent().getSerializableExtra("Design");
        if (design == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            setAdapter(design.getMeasurements());
        }
    }

    private void setAdapter(ArrayList<Measurement> measurements) {

    }
}