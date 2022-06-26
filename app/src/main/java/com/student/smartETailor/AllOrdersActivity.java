package com.student.smartETailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.adapters.AdminOrderAdapter;
import com.student.smartETailor.adapters.OrderAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.OrderModel;
import com.student.smartETailor.ui.fragments.OrdersFragment;

import java.util.ArrayList;

public class AllOrdersActivity extends AppCompatActivity {
    private final String TAG = OrdersFragment.class.getSimpleName();

    RecyclerView RV;
    LinearLayoutManager layoutManager;
    ArrayList<OrderModel> list;
    AdminOrderAdapter adapter;
    LinearLayout LL_NA;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        setting();
        showOrders();
    }

    private void showOrders() {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_ORDERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        spinKitView.setVisibility(View.GONE);
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            OrderModel orderModel = snap.getValue(OrderModel.class);
                            list.add(orderModel);


                        }

                        resetAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                        spinKitView.setVisibility(View.GONE);
                    }
                });
    }

    private void resetAdapter() {
        if (list.size() > 0) {
            LL_NA.setVisibility(View.GONE);
        } else {
            LL_NA.setVisibility(View.VISIBLE);
        }
        adapter = new AdminOrderAdapter(this, list);
        RV.setAdapter(adapter);
    }

    private void setting() {
        RV = findViewById(R.id.RV);
        layoutManager = new LinearLayoutManager(this);
        RV.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        LL_NA = findViewById(R.id.LL_NA);
        spinKitView = findViewById(R.id.spin_kit);
    }
}