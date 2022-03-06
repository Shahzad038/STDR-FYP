package com.student.smartETailor.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.utils.AlerterUtils;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.R;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();

    TextView tvSignup;
    RelativeLayout RL_signin;
    EditText etEmail, etPassword;
    String email, pass;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setting();
        Utils.getInstance().requestAllPermissions(this);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        RL_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.getInstance().isNetworkAvailable(LoginActivity.this)) {
                    AlerterUtils.getInstance().showAlertForConnectivity(LoginActivity.this);
                    return;
                }
                email = etEmail.getText().toString().trim();
                pass = etPassword.getText().toString().trim();
                if (isValid()) {
                    LoginUser();
                }
            }
        });


    }


    private void checkForAlreadyLoggedIn() {
        User user = UsersUtils.getInstance(this).fetchUser();
        String uid = user.getUID();
        if (!uid.equals("")) {
            uid = FirebaseAuth.getInstance().getUid();
            if (uid != null) {
                if (!user.getName().equals("")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        }
    }

    private void LoginUser() {
        progressDialog.show();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();
                            fetchUserData(uid);
                        } else {
                            progressDialog.dismiss();
                            AlerterUtils.getInstance().showAlertForError(LoginActivity.this);
                        }
                    }
                });

    }

    private void fetchUserData(String uid) {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        if (snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            if (user == null || user.getStatus().equals(Const.STATUS_BLOCKED)) {
                                Toast.makeText(LoginActivity.this, "You are blocked", Toast.LENGTH_SHORT).show();
                            } else {
                                UsersUtils.getInstance(LoginActivity.this).save(user);
                                finishAffinity();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Something wrong with this account", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Log.e(TAG, error.toString());
                    }
                });
    }


    private boolean isValid() {
        if (email.equals("")) {
            etEmail.setError("Enter your email");
            return false;
        } else if (pass.equals("")) {
            etPassword.setError("Enter your password");
            return false;
        } else if (pass.length() < 6) {
            etPassword.setError("Password is incorrect");
            return false;
        }
        return true;
    }

    private void setting() {
        tvSignup = findViewById(R.id.tv_signup);
        RL_signin = findViewById(R.id.RL_signin);
        etEmail = findViewById(R.id.et_signin_email);
        etPassword = findViewById(R.id.et_signin_password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please wait...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForAlreadyLoggedIn();
    }
}