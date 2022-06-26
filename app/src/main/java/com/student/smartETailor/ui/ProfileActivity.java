package com.student.smartETailor.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.student.smartETailor.R;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private final String TAG = ProfileActivity.class.getSimpleName();
    TextView tvUsername, tvAbout, tvType, tvPhone, tvEmail,tvEmailBottom;
    CircleImageView civProfilePic;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = UsersUtils.getInstance(this).fetchUser();
        if (user == null) {
            Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show();
            finish();
        } else if (user.getUID().equals("")) {
            Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show();
            finish();
        }
        setting();
        setUserData(user);
    }

    private void setting() {
        civProfilePic = findViewById(R.id.civ_profile_pic);
        tvUsername = findViewById(R.id.tv_profile_name);
        tvAbout = findViewById(R.id.tv_profile_about);
        tvType = findViewById(R.id.tv_profile_type);
        tvEmail = findViewById(R.id.tv_profile_email);
        tvEmailBottom = findViewById(R.id.tv_email_bottom);
        tvPhone = findViewById(R.id.tv_profile_phone);
    }

    @SuppressLint("SetTextI18n")
    private void setUserData(User user) {
        if (!user.getUID().equals("")) {
            Glide.with(this).load(user.getImageURL()).centerCrop()
                    .placeholder(ContextCompat.getDrawable(this,R.drawable.profile_round)).into(civProfilePic);
            Log.i(TAG, "imageURL:" + user.getImageURL());
            tvUsername.setText(user.getName());
            tvAbout.setText(user.getAddress());
            tvType.setText("(" + user.getType() + ")");
            tvPhone.setText(user.getContact());
            tvEmail.setText(user.getEmail());
            tvEmailBottom.setText(user.getEmail());
        }
    }
}