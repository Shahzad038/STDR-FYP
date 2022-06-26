package com.student.smartETailor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.DesignPicsAdapter;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.utils.BottomSheetDialogsUtils;
import com.student.smartETailor.utils.UsersUtils;

public class DesignDetailsActivity extends AppCompatActivity {
    private final String TAG = DesignDetailsActivity.class.getSimpleName();
    public static String EXTRA_DESIGN = "design";
    Design design;

    TextView tvName, tvDescription, tvPrice, tvCustomization;
    TextView tvPlaceOrder;
    RecyclerView rvDesigns;
    DesignPicsAdapter imagesAdapter;
    ImageView imgDesignTop;
    View tailorDetails, measurementsList;
    private String from;


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_details);
        design = (Design) getIntent().getSerializableExtra(EXTRA_DESIGN);
        Bundle bundle = getIntent().getExtras();

        if (design == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            finish();
        }


        settings();

        if (UsersUtils.getInstance(this).fetchUser().getType().equals("tailor")) {
            tvPlaceOrder.setVisibility(View.GONE);
            tailorDetails.setVisibility(View.GONE);
        }


        if (UsersUtils.getInstance(this).fetchUser().getType().equals("admin")) {
            tvPlaceOrder.setVisibility(View.GONE);
        }

        if (bundle != null) {
            from = bundle.getString("from");
        }

        updateUI();
        setListener();
    }

    private void setListener() {
        tvPlaceOrder.setOnClickListener(view -> {
            BottomSheetDialogsUtils.getInstance().showBSDForOrderPlacing(this, design);
        });

        tailorDetails.setOnClickListener(view -> {
            BottomSheetDialogsUtils.getInstance().showBSDForTailorDetails(this, design.getTailorID());
        });

        measurementsList.setOnClickListener(view -> {
            Intent intent = new Intent(DesignDetailsActivity.this, MeasurementsActivity.class);
            intent.putExtra(EXTRA_DESIGN, design);
            startActivity(intent);
        });
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

        imagesAdapter = new DesignPicsAdapter(this, design.getDesigns());
        rvDesigns.setAdapter(imagesAdapter);


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
        imgDesignTop = findViewById(R.id.img_design_top);
        tvDescription = findViewById(R.id.tv_design_description);
        rvDesigns = findViewById(R.id.rv_design_images);
        tailorDetails = findViewById(R.id.inc_dd_tailor_details);
        measurementsList = findViewById(R.id.inc_dd_measurements);
        tvPlaceOrder = findViewById(R.id.tv_place_order);
    }
}