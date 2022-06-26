package com.student.smartETailor.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.interfaces.AddPaymentInterface;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.utils.UsersUtils;

public class AddMeasurementsActivity extends AppCompatActivity {
    private final String TAG = AddMeasurementsActivity.class.getSimpleName();

    EditText etNeck, etShoulders, etSleeves, etChest, etWaist, etHips, etInseam, etThigh;
    String neck, shoulder, sleeve, chest, waist, hips, inseam, thigh;

    RelativeLayout RLCreateMeasurement;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurements);
        setting();

        RLCreateMeasurement.setOnClickListener(view -> {
            getUserInputData();
            if (validateUserInputData()) {
                uploadMeasurement();
            }
        });
    }

    private void uploadMeasurement() {
        progressDialog.show();
        String UID = UsersUtils.getInstance(this).fetchUser().getUID();
        String ct = String.valueOf(System.currentTimeMillis());
        Measurement m = new Measurement(ct, neck, shoulder, sleeve, chest, waist, hips, inseam, thigh);
        FirebaseDatabase.getInstance().getReference(Const.DB_USERS)
                .child(UID)
                .child(Const.DB_USERS_MEASUREMENTS)
                .child(ct)
                .setValue(m)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this.getApplicationContext(), "Measurement added", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Something wrong in adding measurement", Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void setting() {
        etNeck = findViewById(R.id.et_neck);
        etShoulders = findViewById(R.id.et_shoulders);
        etSleeves = findViewById(R.id.et_sleeves);
        etChest = findViewById(R.id.et_chest);
        etWaist = findViewById(R.id.et_waist);
        etHips = findViewById(R.id.et_hips);
        etInseam = findViewById(R.id.et_inseam);
        etThigh = findViewById(R.id.et_thigh);
        RLCreateMeasurement = findViewById(R.id.RLCreateMeasurement);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating...");
        progressDialog.setMessage("Create measurement so please wait");
        progressDialog.setCancelable(false);
    }

    private void getUserInputData() {
        neck = etNeck.getText().toString();
        shoulder = etShoulders.getText().toString();
        sleeve = etSleeves.getText().toString();
        chest = etChest.getText().toString();
        waist = etWaist.getText().toString();
        hips = etHips.getText().toString();
        inseam = etInseam.getText().toString();
        thigh = etThigh.getText().toString();
    }

    private boolean validateUserInputData() {
        boolean isValid = true;
        try {
            if (neck.isEmpty()) {
                etNeck.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(neck) <= 0 || neck.trim().equals(".")) {
                etNeck.setError("neck must be greater than 0");
                isValid = false;
            } else if (shoulder.isEmpty()) {
                etShoulders.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(shoulder) <= 0 || shoulder.trim().equals(".")) {
                etShoulders.setError("shoulder must be greater than 0");
                isValid = false;
            } else if (sleeve.isEmpty()) {
                etSleeves.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(sleeve) <= 0 || sleeve.trim().equals(".")) {
                etSleeves.setError("sleeve must be greater than 0");
                isValid = false;
            } else if (chest.isEmpty()) {
                etChest.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(chest) <= 0 || chest.trim().equals(".")) {
                etChest.setError("chest must be greater than 0");
                isValid = false;
            } else if (waist.isEmpty()) {
                etWaist.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(waist) <= 0 || waist.trim().equals(".")) {
                etWaist.setError( "waist must be greater than 0");
                isValid = false;
            } else if (hips.isEmpty()) {
                etHips.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(hips) <= 0 || hips.trim().equals(".")) {
                etHips.setError("hips must be greater than 0");
                isValid = false;
            } else if (inseam.isEmpty()) {
                etInseam.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(inseam) <= 0 || inseam.trim().equals(".")) {
                etInseam.setError( "inseam must be greater than 0");
                isValid = false;
            } else if (thigh.isEmpty()) {
                etThigh.setError("Please enter this measurement");
                isValid = false;
            } else if (Double.parseDouble(thigh) <= 0 || thigh.trim().equals(".")) {
                etThigh.setError( "thigh must be greater than 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            isValid = false;
            Toast.makeText(this, "please enter a valid number", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }
}