package com.student.smartETailor.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.OrderActivity;
import com.student.smartETailor.ui.Tailor.TailorMeasurementsActivity;
import com.student.smartETailor.utils.AlerterUtils;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class TailorFragment extends Fragment {
    private final String TAG = TailorFragment.class.getSimpleName();
    TextView tvName;
    LinearLayout llChat, llMeasurements, llDesgins, llOrders;
    CircleImageView civPic;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tailor, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        setting(view);
        updateUI();
        setListeners();
        return view;
    }


    private void updateUI() {
        tvName.setText("" + user.getName());
        Glide.with(this).load(user.getImageURL()).centerCrop().into(civPic);
    }

    private void setListeners() {
        llOrders.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), OrderActivity.class));
        });

        llChat.setOnClickListener(view -> {
            AlerterUtils.getInstance().notImplemented(requireActivity());
        });



        llMeasurements.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), TailorMeasurementsActivity.class));
        });
    }

    private void setting(View view) {
        civPic = view.findViewById(R.id.civ_tailor_pic);
        tvName = view.findViewById(R.id.tv_name);
        llChat = view.findViewById(R.id.ll_chat);
        llMeasurements = view.findViewById(R.id.ll_measurements);
        llDesgins = view.findViewById(R.id.ll_designs);
        llOrders = view.findViewById(R.id.ll_orders);
    }
}