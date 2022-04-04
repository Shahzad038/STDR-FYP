package com.student.smartETailor.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;
public class DesignDetailsActivity extends AppCompatActivity {
    public String EXTRA_DESIGN = "design";
    Design design;

    TextView tvName, tvDescription, tvPrice, tvCustomization;
    TextView tvPlaceOrder;
    RecyclerView rvDesigns;
    ImageView imgDesignTop;
    View tailorDetails, measurementsList;


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_details);
        design = (Design) getIntent().getSerializableExtra(EXTRA_DESIGN);
        if (design == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            finish();
        }

        settings();
        updateUI();
    }
    private void updateUI() {
        tvName.setText(design.getName());

        if (design.isCustomization()) {
            tvPrice.setText(design.getPrice() + "$");
            tvCustomization.setVisibility(View.VISIBLE);
        } else {
            tvPrice.setText(design.getMeasurements().get(0).getPayment() + "$");
            tvCustomization.setVisibility(View.GONE);
        }
        tvDescription.setText(design.getDescription());
        Glide.with(this).load(design.getDesigns().get(0)).centerCrop().into(imgDesignTop);
        if (design.getMeasurements().size() > 0) {
            measurementsList.setVisibility(View.VISIBLE);
        } else {
            measurementsList.setVisibility(View.GONE);
        }
    }

    private void settings() {
        tvName = findViewById(R.id.tv_design_name);
        tvCustomization = findViewById(R.id.tv_design_customizable);
        tvPrice = findViewById(R.id.tv_design_price);
        tvPlaceOrder = findViewById(R.id.tv_place_order);
    }
}