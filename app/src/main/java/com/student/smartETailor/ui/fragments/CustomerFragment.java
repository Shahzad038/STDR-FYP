package com.student.smartETailor.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.Customer.CustomerChatFragment;
import com.student.smartETailor.ui.Customer.CustomerHomeFragment;
import com.student.smartETailor.ui.Customer.CustomerMeasurementFragment;
import com.student.smartETailor.ui.OrderActivity;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class CustomerFragment extends Fragment {
    User user;

    List<Fragment> listOfFragments;
    List<String> listOfTitles;

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        fillFragmentAndTitle();
        setting(view);

        return view;
    }

    private void fillFragmentAndTitle() {
        listOfFragments = new ArrayList<>();
        listOfTitles = new ArrayList<>();

        listOfTitles.add("Home");
        listOfTitles.add("Chat");
        listOfTitles.add("Measurements");

        listOfFragments.add(new CustomerHomeFragment());
        listOfFragments.add(new CustomerChatFragment());
        listOfFragments.add(new CustomerMeasurementFragment());
    }

    private void setting(View view) {
        viewPager = view.findViewById(R.id.view_pager_customer);
        tabs = view.findViewById(R.id.tabs_customer);
        sectionsPagerAdapter = new SectionsPagerAdapter(this.requireActivity(), this.requireActivity().getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabs.setupWithViewPager(viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this.requireActivity()).inflate(R.layout.custom_tabs, tabs, false);

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
//    private void openFragment(Fragment fragment) {
//        this.requireActivity()
//                .getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.frame_customer, fragment)
//                .addToBackStack(null)
//                .commit();
//    }
}