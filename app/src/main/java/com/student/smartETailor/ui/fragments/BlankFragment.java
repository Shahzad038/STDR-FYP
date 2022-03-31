package com.student.smartETailor.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.student.smartETailor.R;
import com.student.smartETailor.utils.UsersUtils;


public class BlankFragment extends Fragment {
    TextView tv2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        setting(view);
        tv2.setText("Welcome "+ UsersUtils.getInstance(this.getContext()).fetchUser().getName().toString());
        return view;
    }
    private void setting(View view) {
        tv2 = view.findViewById(R.id.textView2);
    }
}