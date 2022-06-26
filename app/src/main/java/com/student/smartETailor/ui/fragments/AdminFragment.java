package com.student.smartETailor.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.student.smartETailor.AllDesignsActivity;
import com.student.smartETailor.AllOrdersActivity;
import com.student.smartETailor.R;
import com.student.smartETailor.ui.ViewUsersActivity;

public class AdminFragment extends Fragment {

    private CardView cardUsers, cardTailors, cardDesigns, cardOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        initViews(view);
        cardUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewUsersActivity.class)
                        .putExtra("type","customer"));
            }
        });

        cardTailors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewUsersActivity.class)
                        .putExtra("type","tailor"));
            }
        });

        cardOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AllOrdersActivity.class));

            }
        });

        cardDesigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AllDesignsActivity.class));

            }
        });
        return view;
    }


    private void initViews(View view) {
        cardUsers = view.findViewById(R.id.card_users);
        cardTailors = view.findViewById(R.id.card_tailors);
        cardDesigns = view.findViewById(R.id.card_designs);
        cardOrders = view.findViewById(R.id.card_orders);
    }


}