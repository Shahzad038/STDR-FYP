package com.student.smartETailor.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.student.smartETailor.R;
import com.student.smartETailor.models.User;

public class UsersAdapter extends FirebaseRecyclerAdapter<User, UsersAdapter.UserViewHolder> {
    private Context context;

    public UsersAdapter(@NonNull FirebaseRecyclerOptions<User> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
        holder.tvCompanyName.setText(model.getName());
        holder.tvUserName.setText(model.getType());
        holder.tvUserEmail.setText(model.getEmail());
        holder.tvUserPhone.setText(model.getContact());
        Glide.with(context).load(model.getImageURL()).into(holder.ivUserIcon);
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailsDialog(model);
            }
        });
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_user, null);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName, tvUserEmail, tvUserPhone, tvCompanyName;
        private Button  btnViewDetails;
        private LinearLayout llSecond;
        private CardView cardView;
        private ImageView ivPhoneIcon, ivEmailIcon;
        private ImageView ivUserIcon;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            ivUserIcon = itemView.findViewById(R.id.iv_category_icon);
            tvCompanyName = itemView.findViewById(R.id.tv_company_name);
            cardView = itemView.findViewById(R.id.card_sales_manager);
            llSecond = itemView.findViewById(R.id.ll_second);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserEmail = itemView.findViewById(R.id.tv_email);
            tvUserPhone = itemView.findViewById(R.id.tv_phone);
            btnViewDetails = itemView.findViewById(R.id.btn_view_details);
            ivPhoneIcon = itemView.findViewById(R.id.iv_phone);
            ivEmailIcon = itemView.findViewById(R.id.iv_email);
        }
    }


    private void showDetailsDialog(User user) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_view_details);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView tvCompanyName = dialog.findViewById(R.id.et_company_name);
        TextView tvCustomerName = dialog.findViewById(R.id.et_customer_full_name);
        TextView tvEmail = dialog.findViewById(R.id.et_email);
        TextView tvEmailHeading = dialog.findViewById(R.id.et_email_text);
        TextView tvPhone = dialog.findViewById(R.id.llMobile);
        TextView tvAddress = dialog.findViewById(R.id.et_address);
        TextView tvAddressHeading = dialog.findViewById(R.id.et_address_text);
        TextView tvGst = dialog.findViewById(R.id.et_gst_no);
        TextView tvGstHeading = dialog.findViewById(R.id.et_gst_no_txt);




        tvCompanyName.setText(user.getName());
        tvCustomerName.setText(user.getType());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getContact());
        tvAddress.setText(user.getAddress());
        tvGst.setText(user.getUID());

        dialog.show();

    }

}
