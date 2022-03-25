package com.student.smartETailor.ui;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.student.smartETailor.utils.AlerterUtils;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.R;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {
    private final String TAG = SignupActivity.class.getSimpleName();
    private final int PERMISSION_REQUEST_CODE = 11;

    EditText etEmail, etPass, etConfPass, etName, etContact, etAddress;
    Spinner spnrUserType;
    RelativeLayout RL_SignUp;
    CircleImageView civPic;
    RadioGroup radioGroupGender;

    String name, contact, address, gender, email, pass, confPass, userType;
    Uri URI_OF_SELECTED_IMAGE, URI_URL_OF_IMAGE;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setting();
        Utils.getInstance().requestAllPermissions(this);

        RL_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Before Registering user, Check his Internet Connection
                if(isOnline()){
                    fetchData();
                    if (isValid()) {
                        signupUser();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Please make sure you have an active internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        civPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picImageFromGallery();
            }
        });


    }

    private void signupUser() {
        progressDialog.show();
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();
                            uploadProfilePic(uid);
                        } else {
                            progressDialog.dismiss();
                            Log.e(TAG, task.getException().toString());
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void uploadProfilePic(String UID) {
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child("profilePics/" + (UID));
        reference.putFile(URI_OF_SELECTED_IMAGE)
                .continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                URI_URL_OF_IMAGE = task.getResult();
                createDatabaseRecord(UID);
            } else {
                progressDialog.dismiss();
                AlerterUtils.getInstance().showAlertForProfilePicNotUpload(SignupActivity.this);
            }
        });
    }

    private void createDatabaseRecord(String UID) {
        User user = new User(UID, name, contact, address, gender, URI_URL_OF_IMAGE.toString(), Const.STATUS_ACTIVE, userType, Const.USER_ACCESS_USER);
        user.setEmail(email);
        user.setPassword(pass);
        Log.v(TAG, user.toString());

        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(UID)
                .setValue(user)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this.getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        AlerterUtils.getInstance().showAlertForCreateAccountError(SignupActivity.this);
                    }
                });

    }

    private void setting() {
        etEmail = findViewById(R.id.et_signup_email);
        etPass = findViewById(R.id.et_signup_password);
        etConfPass = findViewById(R.id.et_signup_confirm_password);
        RL_SignUp = findViewById(R.id.RL_signup);

        etName = findViewById(R.id.et_signup_detail_name);
        etContact = findViewById(R.id.et_signup_detail_contact);
        etAddress = findViewById(R.id.et_signup_detail_address);

        civPic = findViewById(R.id.civ_signup_pic);
        radioGroupGender = findViewById(R.id.rgrp_gender);

        spnrUserType = findViewById(R.id.spnr_user_types);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Sign Up");
        progressDialog.setMessage("Please wait. Your account is being register");
    }

    private void fetchData() {
        name = etName.getText().toString().trim();
        contact = etContact.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        confPass = etConfPass.getText().toString().trim();

        userType = (spnrUserType.getSelectedItem()).toString().toLowerCase();

        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        gender = radioButton.getText().toString().trim();
    }

    private boolean isValid() {
        if (URI_OF_SELECTED_IMAGE == null) {
            Toast.makeText(this, "Please select your profile pic", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(name)) {
            etName.setError("Please enter your name");
            return false;
        } else if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter your email");
            return false;
        }else if (!isValidEmail(email)) {
            Toast.makeText(this, "Please provide a valid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(contact)) {
            etContact.setError("Contact detail missing");
            return false;
        }else if (!isPhoneNumberValid(contact,"+92")) {
            etContact.setError("Phone # should be in a proper format");
            return false;
        } else if (TextUtils.isEmpty(address)) {
            etAddress.setError("Enter your address");
            return false;
        } else if (gender.equals("")) {
            Toast.makeText(this, "Reselect your gender", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.equals("")) {
            etPass.setError("Enter your password");
            return false;
        }
        else if(!validatePassword()){
            return  false;
        }
        else if (confPass.equals("")) {
            etConfPass.setError("Reenter your password");
            return false;
        } else if (!confPass.equals(pass)) {
            etConfPass.setError("Password didn't match");
            return false;
        }
        return true;
    }

    private void picImageFromGallery() {
        CropImage.startPickImageActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            URI_OF_SELECTED_IMAGE = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, URI_OF_SELECTED_IMAGE)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                }
            } else {
                CropImage.activity(URI_OF_SELECTED_IMAGE)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMultiTouchEnabled(true)
                        .start(this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    if (result != null) {
                        URI_OF_SELECTED_IMAGE = result.getUri();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), URI_OF_SELECTED_IMAGE);
                        civPic.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    // Checks Network Connectivity
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    // Alphanumeric Password Validation
    private boolean validatePassword() {
        String passwordInput =  pass;

        if (!passwordInput.matches(".*[0-9].*")) {
            Toast.makeText(SignupActivity.this, "Password should contain at least 1 digit", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.matches(".*[a-z].*")) {
            Toast.makeText(SignupActivity.this, "Password should contain at least 1 lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.matches(".*[A-Z].*")) {
            Toast.makeText(SignupActivity.this, "Password should contain at least 1 upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.matches(".*[a-zA-Z].*")) {
            Toast.makeText(SignupActivity.this, "Password should contain a letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.matches( ".{8,}")) {
            Toast.makeText(SignupActivity.this, "Password should contain 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    // Email Pattern Validation
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isPhoneNumberValid(String phoneNumber, String countryCode) {
        // NOTE: This should probably be a member variable.
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }

}