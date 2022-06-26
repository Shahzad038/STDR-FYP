package com.student.smartETailor.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.ui.OrderActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CustomerOrdersFragment extends Fragment {

    private final String TAG = OrderActivity.class.getSimpleName();

    List<Fragment> listOfFragments;
    List<String> listOfTitles;

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         View view=  inflater.inflate(R.layout.fragment_customer_orders, container, false);

        fillFragmentAndTitle();
        setting(view);
         return view;
    }

    private void fillFragmentAndTitle() {
        listOfFragments = new ArrayList<>();
        listOfTitles = new ArrayList<>();

        listOfTitles.add(Const.ORDER_STATUS_PENDING);
        listOfTitles.add(Const.ORDER_STATUS_COMPLETED);

        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_PENDING));
        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_COMPLETED));
    }

    private void setting(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabs =  view.findViewById(R.id.tabs);

        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getActivity()).inflate(R.layout.custom_tabs, tabs, false);

            TextView tabTextView = relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
            //tab.select();
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return listOfFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return listOfTitles.get(position);
        }

        @Override
        public int getCount() {
            return listOfTitles.size();
        }
    }

}