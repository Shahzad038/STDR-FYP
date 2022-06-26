package com.student.smartETailor.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.SMSAdapter2;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Conversation;
import com.student.smartETailor.models.MySMS;
import com.student.smartETailor.utils.UsersUtils;

import java.util.ArrayList;
import java.util.List;

public class SMSActivity extends AppCompatActivity {
    private final String TAG = SMSActivity.class.getSimpleName();
    public static final String EXTRA_CONVERSATION = "extraConversation";
    private String DB_CONVERSATION_ID;
    private Conversation conversation;
    RecyclerView RV;
    List<MySMS> mySMSList;
    SMSAdapter2 adapter;
    String UID;

    EditText etSms;
    ImageView imgSmsSend;

    private int existConvInMe = 0, existConvOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        UID = FirebaseAuth.getInstance().getUid();
        if (UID == null || UID.equals("")) {
            Toast.makeText(this, "Account problem. Login again", Toast.LENGTH_SHORT).show();
            finish();
        }

        Intent intent = getIntent();
        Object cnvSr = intent.getSerializableExtra(EXTRA_CONVERSATION);
        if (cnvSr == null) {
            finish();
        } else {
            conversation = (Conversation) cnvSr;
        }

        DB_CONVERSATION_ID = conversation.getUcid();
        if (DB_CONVERSATION_ID.equals("")) {
            Log.e(TAG, "Conversation ID empty");
            finish();
        }
        getSupportActionBar().setTitle(conversation.getName());

        settingForSendingSMS();
        RV = findViewById(R.id.RV_sms);
        mySMSList = new ArrayList<>();
        resetAdapter();
        fetchSMS();
        checkForConversationInMe();
        checkForConversationInOther();
    }

    private void resetAdapter() {
        adapter = new SMSAdapter2(this, mySMSList, UID);
        RV.setAdapter(adapter);
    }

    private void settingForSendingSMS() {
        etSms = findViewById(R.id.et_sms);
        imgSmsSend = findViewById(R.id.img_sms_send);
        imgSmsSend.setOnClickListener(view -> {
            if (etSms.getText().toString().equals("")) {
                etSms.setError("Empty sms can't be send");
                return;
            }
            String smsBody = etSms.getText().toString();
            if (existConvInMe == 0 || existConvOther == 0) {
                Toast.makeText(this, "Wait and then send SMS", Toast.LENGTH_SHORT).show();
            } else if (existConvOther == -1 || existConvInMe == -1) {
                addConversationInUser();
                Toast.makeText(this, "Wait and then try to send", Toast.LENGTH_SHORT).show();
            } else {
                sendSMS(smsBody, Const.MESSAGE_TYPE_TEXT);
                etSms.setText("");
            }
        });
    }

    private void fetchSMS() {
        Log.i(TAG, "DB UCID:" + DB_CONVERSATION_ID);
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_CONVERSATIONS)
                .child(DB_CONVERSATION_ID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mySMSList.clear();
                        for (DataSnapshot snapMSG : snapshot.getChildren()) {
                            MySMS mySMS = snapMSG.getValue(MySMS.class);
                            mySMSList.add(mySMS);
                        }
                        resetAdapter();

                        if (mySMSList.size() <= 0) {
                            Toast.makeText(SMSActivity.this, "No messages found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                        Toast.makeText(SMSActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addConversationInUser() {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(UsersUtils.getInstance(this).fetchUser().getUID())
                .child(Const.DB_CONVERSATIONS)
                .child(conversation.getUcid())
                .setValue(conversation);

        Conversation convForOther = new Conversation();
        convForOther.setLastSms("");
        convForOther.setRead(0);
        convForOther.setName(UsersUtils.getInstance(this).fetchUser().getName());
        convForOther.setUcid(conversation.getUcid());
        convForOther.setUid(UsersUtils.getInstance(this).fetchUser().getUID());
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(conversation.getUid())
                .child(Const.DB_CONVERSATIONS)
                .child(conversation.getUcid())
                .setValue(convForOther);
    }

    private void sendSMS(String msgBody, String type) {
        String time = String.valueOf(System.currentTimeMillis());
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_CONVERSATIONS)
                .child(DB_CONVERSATION_ID)
                .child(time)
                .setValue(new MySMS(msgBody, time, UID, conversation.getUid(), type))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                    } else {
                        Toast.makeText(SMSActivity.this, "Error in sending sms", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkForConversationInMe() {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(UsersUtils.getInstance(this).fetchUser().getUID())
                .child(Const.DB_CONVERSATIONS)
                .child(conversation.getUcid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            existConvInMe = 1;
                        } else {
                            existConvInMe = -1;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }


    private void checkForConversationInOther() {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(conversation.getUid())
                .child(Const.DB_CONVERSATIONS)
                .child(conversation.getUcid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            existConvOther = 1;
                        } else {
                            existConvOther = -1;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }
}