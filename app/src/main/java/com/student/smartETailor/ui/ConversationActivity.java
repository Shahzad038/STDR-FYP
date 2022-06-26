package com.student.smartETailor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.student.smartETailor.R;
import com.student.smartETailor.adapters.ConversationAdapter;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

public class ConversationActivity extends AppCompatActivity {
    User user;

    RecyclerView rv_conversations;
    ConversationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        user = UsersUtils.getInstance(this).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this);
        }

        rv_conversations = findViewById(R.id.rv_conversations);
        adapter = new ConversationAdapter(this);
        rv_conversations.setAdapter(adapter);
    }
}