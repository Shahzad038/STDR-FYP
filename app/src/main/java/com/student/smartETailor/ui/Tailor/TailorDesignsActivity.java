package com.student.smartETailor.ui.Tailor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.TailorDesignAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.utils.UsersUtils;

import java.util.ArrayList;

public class TailorDesignsActivity extends AppCompatActivity {
    private final String TAG = TailorDesignsActivity.class.getSimpleName();

    TextView tvAddDesign;
    RecyclerView rvDesigns;

    ArrayList<Design> designs;

    TailorDesignAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_designs);
        setting();
        setListener();
        fetchDesignsIDs();
    }

    private void fetchDesignsIDs() {
        FirebaseDatabase.getInstance().getReference(Const.DB_DESIGNS)
                .child(UsersUtils.getInstance(this).fetchUser().getUID())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        designs.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                designs.add(snap.getValue(Design.class));
                            }
                        }
                        adapter.notifyItemRangeChanged(0,designs.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    private void setListener() {
        tvAddDesign.setOnClickListener(view -> {
            startActivity(new Intent(TailorDesignsActivity.this, AddDesignActivity.class));
        });
    }

    private void setting() {
        tvAddDesign = findViewById(R.id.tv_add_design);
        rvDesigns = findViewById(R.id.RV_designs);
        designs = new ArrayList<>();
        adapter = new TailorDesignAdapter(this, designs);
        rvDesigns.setAdapter(adapter);
    }
}