package com.student.smartETailor.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.OrderAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Order;
import com.student.smartETailor.models.OrderModel;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {
    private final String TAG = OrdersFragment.class.getSimpleName();

    RecyclerView RV;
    LinearLayoutManager layoutManager;
    ArrayList<OrderModel> list;
    OrderAdapter adapter;
    LinearLayout LL_NA;
    SpinKitView spinKitView;

    String orderType;

    public OrdersFragment(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        setting(view);
        resetAdapter();

        fetchUserAndDisplay();
        return view;
    }

    private void fetchUserAndDisplay() {
        Query query;
        if (UsersUtils.getInstance(getActivity()).fetchUser().getType().equals("customer")) {
            query = FirebaseDatabase.getInstance()
                    .getReference(Const.DB_ORDERS).orderByChild("customerId")
                    .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } else if (UsersUtils.getInstance(getActivity()).fetchUser().getType().equals("tailor")) {
            query = FirebaseDatabase.getInstance()
                    .getReference(Const.DB_ORDERS).orderByChild("tailorId")
                    .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } else {
            query = FirebaseDatabase.getInstance()
                    .getReference(Const.DB_ORDERS);
        }


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                spinKitView.setVisibility(View.GONE);
                for (DataSnapshot snap : snapshot.getChildren()) {
                    OrderModel orderModel = snap.getValue(OrderModel.class);
                    if (orderModel.getStatus().equalsIgnoreCase(orderType)) {
                        list.add(orderModel);
                    }

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
        adapter = new OrderAdapter(getActivity(), list);
        RV.setAdapter(adapter);
    }

    private void setting(View v) {
        RV = v.findViewById(R.id.RV);
        layoutManager = new LinearLayoutManager(getActivity());
        RV.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        LL_NA = v.findViewById(R.id.LL_NA);
        spinKitView = v.findViewById(R.id.spin_kit);
    }
}