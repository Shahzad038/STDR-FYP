package com.student.smartETailor.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.MySMS;

import java.util.List;

public class SMSAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = SMSAdapter2.class.getSimpleName();
    Activity context;
    List<MySMS> list;
    String UID;
    Dialog dialog;
    ProgressDialog progressDialog;

    public SMSAdapter2(Activity context, List<MySMS> list, String UID) {
        this.context = context;
        this.list = list;
        this.UID = UID;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Order placing");
        progressDialog.setMessage("Please wait your information is verifying...");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new MyViewHolderReceive(LayoutInflater.from(context).inflate(R.layout.template_sms_receive, parent, false));
            case 1:
                return new MyViewHolderSend(LayoutInflater.from(context).inflate(R.layout.template_sms_send, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MySMS mySMS = list.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                MyViewHolderReceive HR = (MyViewHolderReceive) holder;
                HR.tvInbox.setText(mySMS.getBody());
                break;
            case 1:
                MyViewHolderSend HS = (MyViewHolderSend) holder;
                HS.tvSend.setText(mySMS.getBody());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolderReceive extends RecyclerView.ViewHolder {
        CardView cardInbox;
        TextView tvInbox;

        public MyViewHolderReceive(@NonNull View v) {
            super(v);
            cardInbox = v.findViewById(R.id.card_temp_receiver);
            tvInbox = v.findViewById(R.id.tv_temp_receiver);
        }
    }

    class MyViewHolderSend extends RecyclerView.ViewHolder {
        CardView cardSend;
        TextView tvSend;

        public MyViewHolderSend(@NonNull View v) {
            super(v);
            cardSend = v.findViewById(R.id.card_temp_sender);
            tvSend = v.findViewById(R.id.tv_temp_sender);
        }
    }

    @Override
    public int getItemViewType(int position) {
        MySMS mySMS = list.get(position);
        if (mySMS.getType().equals(Const.MESSAGE_TYPE_TEXT)) {
            if (mySMS.getReceive().equals(UID)) {
                return 0;
            } else {
                return 1;
            }
        }
        return -1;
    }
}
