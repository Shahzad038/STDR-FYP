package com.student.smartETailor.ui.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.student.smartETailor.R;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

public class CustomerChatFragment extends Fragment {

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_measurements, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        setting(view);
        updateUI();
        return view;
    }

    private void updateUI() {

    }


    private void setting(View view) {

    }
}