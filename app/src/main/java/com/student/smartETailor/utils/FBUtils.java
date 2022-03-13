package com.student.smartETailor.utils;


import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.interfaces.DesignPictureUploadingInterface;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.SignupActivity;

import java.util.ArrayList;

public class FBUtils {
    private final String TAG = FBUtils.class.getSimpleName();

    private static FBUtils _INSTANCE;

    private FBUtils() {
    }

    public static FBUtils getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new FBUtils();
        }
        return _INSTANCE;
    }

    public void uploadDesign(Activity activity, Design design) {
        User user = UsersUtils.getInstance(activity).fetchUser();
        FirebaseDatabase.getInstance().getReference(Const.DB_DESIGNS)
                .child(user.getUID())
                .child(design.getDesignID())
                .setValue(design)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Design uploaded", Toast.LENGTH_SHORT).show();
                        activity.finish();
                    } else {
                        Toast.makeText(activity, "Something went wrong in uploading design!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void uploadDesigns(String UID, ArrayList<Uri> designs,
                              int uploadingItemNumber,
                              DesignPictureUploadingInterface designPictureUploadingInterface) {


    }

}
