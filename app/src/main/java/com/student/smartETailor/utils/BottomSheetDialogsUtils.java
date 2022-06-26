package com.student.smartETailor.utils;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Conversation;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.OrderModel;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.SMSActivity;
import com.student.smartETailor.ui.SelectOneMeasurementsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.student.smartETailor.ui.DesignDetailsActivity.EXTRA_DESIGN;

public class BottomSheetDialogsUtils {
    private final String TAG = BottomSheetDialogsUtils.class.getSimpleName();

    private static BottomSheetDialogsUtils _INSTANCE;

    private BottomSheetDialogsUtils() {
    }

    public static BottomSheetDialogsUtils getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new BottomSheetDialogsUtils();
        }
        return _INSTANCE;
    }

    public void showBSDForTailorDetails(Activity activity, String tailorID) {
        BottomSheetDialog bsd = new BottomSheetDialog(activity);
        bsd.setContentView(R.layout.bsd_tailor_details);
        TextView tvName = bsd.findViewById(R.id.tv_bsd_name);
        TextView tvAddress = bsd.findViewById(R.id.tv_bsd_address);
        TextView tvContact = bsd.findViewById(R.id.tv_bsd_contact);
        CircleImageView civPic = bsd.findViewById(R.id.civ_bsd);
        TextView tvContactButton = bsd.findViewById(R.id.tv_bsd_tailor_contact);
        SpinKitView spinKitView = bsd.findViewById(R.id.spin_kit);
        FirebaseDatabase.getInstance().getReference(Const.DB_USERS)
                .child(tailorID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            try {
                                User user = snapshot.getValue(User.class);
                                if (user != null) {
                                    spinKitView.setVisibility(View.GONE);
                                    tvName.setText(user.getName());
                                    tvAddress.setText(user.getAddress());
                                    tvContact.setText(user.getContact());
                                    Glide.with(activity).load(user.getImageURL()).centerCrop().into(civPic);
                                    tvContactButton.setOnClickListener(view -> {
                                        String customerID = UsersUtils.getInstance(activity).fetchUser().getUID();
                                        Conversation conv = new Conversation();
                                        conv.setUcid(customerID + "" + tailorID);
                                        conv.setName(user.getName());
                                        conv.setUid(tailorID);
                                        conv.setRead(0);
                                        conv.setLastSms("");
                                        Intent intent = new Intent(activity, SMSActivity.class);
                                        intent.putExtra(SMSActivity.EXTRA_CONVERSATION, conv);
                                        activity.startActivity(intent);
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(activity, "Something wrong", Toast.LENGTH_SHORT).show();
                                bsd.dismiss();
                            }
                        } else {
                            Toast.makeText(activity, "Something wrong", Toast.LENGTH_SHORT).show();
                            bsd.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(activity, "Something wrong", Toast.LENGTH_SHORT).show();
                        bsd.dismiss();
                    }
                });
        bsd.show();
    }


    public void showBSDForOrderPlacing(Activity activity, Design design) {
        BottomSheetDialog bsd = new BottomSheetDialog(activity);
        bsd.setContentView(R.layout.bsd_order_placing);
        TextView tvName = bsd.findViewById(R.id.tv_design_name);
        TextView tvDescription = bsd.findViewById(R.id.tv_design_description);
        ImageView imgDesign = bsd.findViewById(R.id.img_design_top);
        TextView tvProceed = bsd.findViewById(R.id.tv_place_order_proceed);

        tvName.setText(design.getName());
        tvDescription.setText(design.getDescription());
        Glide.with(activity).load(design.getDesigns().get(0)).centerCrop().into(imgDesign);

        tvProceed.setOnClickListener(view -> {
            Intent intent = new Intent(activity, SelectOneMeasurementsActivity.class);
            intent.putExtra(EXTRA_DESIGN,design);

            activity.startActivity(intent);
        });

        bsd.show();
    }

}
