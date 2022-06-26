package com.student.smartETailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.adapters.TailorDesignAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.Customer.CustomerHomeFragment;

import java.util.ArrayList;

public class AllDesignsActivity extends AppCompatActivity {
    RecyclerView rvDesigns;
    ArrayList<Design> designs;
    TailorDesignAdapter adapter;
    private static final String TAG = "AllDesignsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_designs);
        setting();
        fetchDesigns();

    }

    private void fetchDesigns() {
        FirebaseDatabase.getInstance().getReference(Const.DB_DESIGNS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            for (DataSnapshot usersDesigns : snapshot.getChildren()) {
                                if (usersDesigns.exists()) {
                                    for (DataSnapshot snapDesigns : usersDesigns.getChildren()) {
                                        Design design = snapDesigns.getValue(Design.class);
                                        designs.add(design);
                                    }
                                }
                            }

                            if (designs.size() > 0) {
                                adapter.notifyItemRangeChanged(0,designs.size());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());

                    }
                });
    }

    private void setting() {

        rvDesigns = findViewById(R.id.RV_designs);
        designs = new ArrayList<>();
        adapter = new TailorDesignAdapter(this, designs);
        rvDesigns.setAdapter(adapter);
    }
}