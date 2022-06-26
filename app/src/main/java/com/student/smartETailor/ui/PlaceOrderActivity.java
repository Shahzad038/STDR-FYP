package com.student.smartETailor.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.student.smartETailor.R;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.ui.fragments.PaymentModeFragment;

public class PlaceOrderActivity extends AppCompatActivity {
    ShippingFragment shippingFragment;
    Measurement measurement;
    Design design;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Bundle bundle = getIntent().getExtras();


        shippingFragment = new ShippingFragment();
        shippingFragment.setArguments(bundle);
        loadFragment(shippingFragment);

    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }


}