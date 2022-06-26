package com.student.smartETailor.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.student.smartETailor.Common;
import com.student.smartETailor.R;
import com.student.smartETailor.models.Order;
import com.student.smartETailor.models.OrderModel;
import com.student.smartETailor.ui.fragments.PaymentModeFragment;

public class ShippingFragment extends Fragment {
    private EditText etName, etPhone, etEmail;
    private KProgressHUD progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping, container, false);

        initViews(view);
        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        Bundle bundle = getArguments();
        view.findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderModel.getInstance().setCustomerName(etName.getText().toString());
                OrderModel.getInstance().setCustomerEmail(etEmail.getText().toString());
                OrderModel.getInstance().setCustomerPhone(etPhone.getText().toString());
                OrderModel.getInstance().setCustomerId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                PaymentModeFragment paymentModeFragment = new PaymentModeFragment();
                paymentModeFragment.setArguments(bundle);
                loadFragment(paymentModeFragment);
            }
        });


        fetchDetails();

        return view;
    }

    private void fetchDetails() {
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        etName.setText(snapshot.child("name").getValue().toString());
                        etEmail.setText(snapshot.child("email").getValue().toString());
                        etPhone.setText(snapshot.child("contact").getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void initViews(View view) {
        progressDialog = Common.getIndeterminateProgress(getActivity());
        etEmail = view.findViewById(R.id.et_email);
        etPhone = view.findViewById(R.id.et_phone);
        etName = view.findViewById(R.id.et_name);
    }




}