package com.student.smartETailor.ui.Customer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.student.smartETailor.R;
import com.student.smartETailor.adapters.TailorDesignAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.fragments.CustomerFragment;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerHomeFragment extends Fragment {
    private final String TAG = CustomerHomeFragment.class.getSimpleName();
    User user;

    RecyclerView rvTrending, rvFeatured, rvRecommended;
    TailorDesignAdapter adapterTrending, adapterFeatured, adapterRecommended;

    SpinKitView spinKitView;

    LinearLayout LL_NA;

    private FusedLocationProviderClient fusedLocationProviderClient;
    public static String country_name;


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
        getLocation();

//        RetrofitClient.getGeoApiService().getLocation().enqueue(new Callback<GeoResponse>() {
//            @Override
//            public void onResponse(Call<GeoResponse> call, Response<GeoResponse> response) {
//                Log.d(TAG, "onResponse: " + response.body().getCountry());
//            }
//
//            @Override
//            public void onFailure(Call<GeoResponse> call, Throwable t) {
//                t.getMessage();
//            }
//        });
        fetchDesigns();
    }

    private void fetchDesigns() {
        ArrayList<Design> list = new ArrayList<Design>();
        ArrayList<Design> list2= new ArrayList<Design>();
        FirebaseDatabase.getInstance().getReference(Const.DB_DESIGNS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            for (DataSnapshot usersDesigns : snapshot.getChildren()) {
                                if (usersDesigns.exists()) {
                                    for (DataSnapshot snapDesigns : usersDesigns.getChildren()) {
                                        Design design = snapDesigns.getValue(Design.class);

                                        /** Comparison **/
                                        if (design.getCulture() != null){
                                            if (country_name.equalsIgnoreCase("Pakistan")){
                                                if (design.getCulture().equalsIgnoreCase("Pakistan")){
                                                    list.add(design);
                                                }
                                            }

                                            if (country_name.equalsIgnoreCase("China")){
                                                if (design.getCulture().equalsIgnoreCase("Chinese")){
                                                    list.add(design);
                                                }
                                            }

                                            if (country_name.equalsIgnoreCase("Europe")){
                                                if (design.getCulture().equalsIgnoreCase("Europe")){
                                                    list.add(design);
                                                }
                                            }

                                            if (country_name.equalsIgnoreCase("India")){
                                                if (design.getCulture().equalsIgnoreCase("Indian")){
                                                    list.add(design);
                                                }
                                            }


                                        }

                                        list2.add(design);
                                    }
                                }
                            }
                            spinKitView.setVisibility(View.GONE);
                            if (list.size() > 0) {
                                LL_NA.setVisibility(View.VISIBLE);
                            } else {
                                LL_NA.setVisibility(View.GONE);
                            }
                            adapterFeatured.updateDesign(list2);
                            adapterRecommended.updateDesign(list);
                            adapterTrending.updateDesign(list2);
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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

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

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            country_name = String.valueOf(addresses.get(0).getCountryName());
                            Log.d("YOMO", "onComplete: " + country_name);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }


}