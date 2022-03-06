package com.student.smartETailor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationView;
import com.student.smartETailor.R;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.Customer.CustomerChatFragment;
import com.student.smartETailor.ui.Customer.CustomerHomeFragment;
import com.student.smartETailor.ui.Customer.CustomerMeasurementFragment;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class CustomerFragment extends Fragment {
    User user;

    SmoothBottomBar smoothBottomBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
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
        openFragment(new CustomerHomeFragment());
//        smoothBottomBar.setOnItemSelectedListener(i -> {
//            switch (i) {
//                case 0: {
//                    openFragment(new CustomerHomeFragment());
//                    return true;
//                }
//                case 1: {
//                    openFragment(new CustomerChatFragment());
//                    return true;
//                }
//                case 2: {
//                    openFragment(new CustomerMeasurementFragment());
//                    return true;
//                }
//                default: {
//                    openFragment(new CustomerHomeFragment());
//                    return false;
//                }
//            }
//        });
    }


    private void setting(View view) {
        smoothBottomBar = view.findViewById(R.id.bottomBar);
//        bnv = view.findViewById(R.id.bn_customer);
//        bnv.setOnItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) item -> {
//            switch (item.getItemId()) {
//                case R.id.menu_home:
//                    openFragment(new CustomerHomeFragment());
//                    return true;
//                case R.id.menu_chat:
//                    openFragment(new CustomerChatFragment());
//                    return true;
//                case R.id.menu_measurements:
//                    openFragment(new CustomerMeasurementFragment());
//                    return true;
//                default:
//                    openFragment(new CustomerHomeFragment());
//                    return false;
//            }
//        });
    }

    private void openFragment(Fragment fragment) {
        this.requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_customer, fragment)
                .addToBackStack(null)
                .commit();
    }
}