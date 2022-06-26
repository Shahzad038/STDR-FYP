package com.student.smartETailor.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.OrderModel;


public class BookOrderFragment extends Fragment {

    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_order, container, false);
        progressBar = view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        OrderModel.getInstance().setTailorId(
                OrderModel.getInstance().getDesign().getTailorID()
        );
        OrderModel.getInstance().setStatus(Const.ORDER_STATUS_PENDING);
        OrderModel.getInstance().setOrderId(OrderModel.getInstance().getTailorId() + System.currentTimeMillis());
        FirebaseDatabase.getInstance().getReference("Orders")
                .child(OrderModel.getInstance().getOrderId())
                .setValue(OrderModel.getInstance())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Order Placed", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }
}