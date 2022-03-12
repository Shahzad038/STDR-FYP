package com.student.smartETailor.ui.Tailor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.MeasurementsAdapter;
import com.student.smartETailor.adapters.TailorAddDesignsListAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.utils.FBUtils;
import com.student.smartETailor.utils.UsersUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;

public class AddDesignActivity extends AppCompatActivity {
    private final String TAG = AddDesignActivity.class.getSimpleName();

    RecyclerView RVImages, RVMeasurements;
    TailorAddDesignsListAdapter imagesAdapter;
    MeasurementsAdapter measurementsAdapter;

    EditText etName, etDescription;
    TextView tvAddDesign;
    CardView cardMeasurementsNA;
    CheckBox checkCustomMeasurements;

    ProgressDialog progressDialog;

    String designCustomizePrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_design);

        settings();
        fetchMeasurements();

        tvAddDesign.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String desc = etDescription.getText().toString();
            if (name.isEmpty()) {
                etName.setError("Please enter the design name");
                return;
            } else if (desc.isEmpty()) {
                etDescription.setError("Please enter the design description");
                return;
            } else if (0 <= 1) {
                Toast.makeText(this, "Please select some design images", Toast.LENGTH_SHORT).show();
                return;
            } else if (measurementsAdapter.getSelectedMeasurements().size() <= 0) {
                Toast.makeText(this, "Please select some measurements", Toast.LENGTH_SHORT).show();
                return;
            }

            String UID = UsersUtils.getInstance(AddDesignActivity.this).fetchUser().getUID();
            Design design = new Design();
            design.setName(name);
            design.setDescription(desc);
            design.setTailorID(UID);
            design.setDesignID(UID + System.currentTimeMillis());
            design.setMeasurements(measurementsAdapter.getSelectedMeasurements());

            design.setCustomization(checkCustomMeasurements.isChecked());
            design.setPrice(designCustomizePrice);

            });

        });
    }

    private void fetchMeasurements() {
        FirebaseDatabase.getInstance().getReference(Const.DB_USERS)
                .child(UsersUtils.getInstance(this).fetchUser().getUID())
                .child(Const.DB_USERS_MEASUREMENTS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ArrayList<Measurement> measurements = new ArrayList<>();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                Measurement m = snap.getValue(Measurement.class);
                                measurements.add(m);
                            }
                            if (measurements.size() > 0) {
                                cardMeasurementsNA.setVisibility(View.GONE);
                                measurementsAdapter.updateMeasurements(measurements);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    private void settings() {
        etName = findViewById(R.id.et_design_name);
        etDescription = findViewById(R.id.et_design_description);
        tvAddDesign = findViewById(R.id.tv_add_design);
        cardMeasurementsNA = findViewById(R.id.card_measurement_NA);
        RVImages = findViewById(R.id.rv_design_images);
        RVMeasurements = findViewById(R.id.rv_design_measurements);
        checkCustomMeasurements = findViewById(R.id.check_custom_measurements);
        measurementsAdapter = new MeasurementsAdapter(this, true);
        RVMeasurements.setAdapter(measurementsAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please wait. Your design is uploading...");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        }
    }
}