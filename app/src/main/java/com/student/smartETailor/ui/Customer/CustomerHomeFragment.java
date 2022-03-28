package com.student.smartETailor.ui.Customer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.TailorDesignAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {
    private final String TAG = CustomerHomeFragment.class.getSimpleName();
    User user;

    RecyclerView rvTrending, rvFeatured, rvRecommended;
    TailorDesignAdapter adapterTrending, adapterFeatured, adapterRecommended;

    SpinKitView spinKitView;

    LinearLayout LL_NA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setting(view);
        fetchDesigns();
    }

    private void fetchDesigns() {
        ArrayList<Design> list = new ArrayList<Design>();
        FirebaseDatabase.getInstance().getReference(Const.DB_DESIGNS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            for (DataSnapshot usersDesigns : snapshot.getChildren()) {
                                if (usersDesigns.exists()) {
                                    for (DataSnapshot snapDesigns : usersDesigns.getChildren()) {
                                        Design design = snapDesigns.getValue(Design.class);
                                        list.add(design);
                                    }
                                }
                            }
                            spinKitView.setVisibility(View.GONE);
                            if (list.size() > 0) {
                                LL_NA.setVisibility(View.VISIBLE);
                            } else {
                                LL_NA.setVisibility(View.GONE);
                            }
                            adapterFeatured.updateDesign(list);
                            adapterRecommended.updateDesign(list);
                            adapterTrending.updateDesign(list);
                        } else {
                            LL_NA.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                        spinKitView.setVisibility(View.GONE);
                        if (list.size() > 0) {
                            LL_NA.setVisibility(View.VISIBLE);
                        } else {
                            LL_NA.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void setting(View view) {
        rvFeatured = view.findViewById(R.id.rv_designs_featured);
        rvTrending = view.findViewById(R.id.rv_designs_trending);
        rvRecommended = view.findViewById(R.id.rv_designs_recommended);
        spinKitView = view.findViewById(R.id.spin_kit);

        adapterFeatured = new TailorDesignAdapter(this.requireActivity(), true);
        adapterRecommended = new TailorDesignAdapter(this.requireActivity(), false);
        adapterTrending = new TailorDesignAdapter(this.requireActivity(), true);

        rvFeatured.setAdapter(adapterFeatured);
        rvRecommended.setAdapter(adapterRecommended);
        rvTrending.setAdapter(adapterTrending);

        LL_NA = view.findViewById(R.id.LL_NA);
    }
}