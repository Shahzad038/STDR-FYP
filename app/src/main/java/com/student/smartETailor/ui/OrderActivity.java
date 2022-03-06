package com.student.smartETailor.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.ui.fragments.OrdersFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    private final String TAG = OrderActivity.class.getSimpleName();

    List<Fragment> listOfFragments;
    List<String> listOfTitles;

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        fillFragmentAndTitle();
        setting();
    }

    private void fillFragmentAndTitle() {
        listOfFragments = new ArrayList<>();
        listOfTitles = new ArrayList<>();

        listOfTitles.add(Const.ORDER_STATUS_PENDING);
        listOfTitles.add(Const.ORDER_STATUS_ACCEPTED);
        listOfTitles.add(Const.ORDER_STATUS_STARTED);
        listOfTitles.add(Const.ORDER_STATUS_SUIT_COMPLETED);
        listOfTitles.add(Const.ORDER_STATUS_SHIPPED);
        listOfTitles.add(Const.ORDER_STATUS_COMPLETED);

        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_PENDING));
        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_ACCEPTED));
        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_STARTED));
        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_SUIT_COMPLETED));
        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_SHIPPED));
        listOfFragments.add(new OrdersFragment(Const.ORDER_STATUS_COMPLETED));
    }

    private void setting() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.custom_tabs, tabs, false);

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

    @Override
    public void onBackPressed() {
        finish();
    }
}