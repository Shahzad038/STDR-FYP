package com.student.smartETailor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.UsersAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.User;

public class ViewUsersActivity extends AppCompatActivity {

    private UsersAdapter adapter;
    private RecyclerView rvUsers;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        rvUsers = findViewById(R.id.rv_users);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
        }
        fetchUsers(type);

    }

    private void fetchUsers(String type) {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("type")
                                .startAt(type).endAt(type + "\uf8ff"), User.class)
                        .build();

        adapter = new UsersAdapter(options, this);
        rvUsers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvUsers.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}