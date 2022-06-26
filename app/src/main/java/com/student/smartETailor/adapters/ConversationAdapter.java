package com.student.smartETailor.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.interfaces.ConversationsInterface;
import com.student.smartETailor.models.Conversation;
import com.student.smartETailor.ui.SMSActivity;
import com.student.smartETailor.utils.FBUtils;
import com.student.smartETailor.utils.UsersUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {
    private final String TAG = ConversationAdapter.class.getSimpleName();
    Context context;
    List<Conversation> list;
    String UID;
    ConversationsInterface conversationsInterface = new ConversationsInterface() {
        @Override
        public void onConversationsReceived(List<Conversation> conversationList) {
            list = conversationList;
            Log.i(TAG, "Conversations found:" + conversationList.size());
            if (list.size() > 0)
                ConversationAdapter.this.notifyItemRangeChanged(0, list.size());
        }

        @Override
        public void onError(String error) {
            Log.e(TAG, error);
        }
    };

    public ConversationAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.UID = UsersUtils.getInstance(context).fetchUser().getUID();
        FBUtils.getInstance().fetchConversations(UID, conversationsInterface);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.template_conversation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Conversation myConversations = list.get(position);
        holder.tvConversationName.setText(myConversations.getName());
        holder.cardConversation.setOnClickListener(view -> {
            Intent intent = new Intent(context, SMSActivity.class);
            intent.putExtra(SMSActivity.EXTRA_CONVERSATION, myConversations);
            context.startActivity(intent);
        });
        holder.tvConversationLastMessage.setText(myConversations.getLastSms());
        if (myConversations.getRead() > 0) {
            holder.tvConversationCounter.setText(myConversations.getRead() + "");
            holder.tvConversationCounter.setVisibility(View.VISIBLE);
        } else {
            holder.tvConversationCounter.setVisibility(View.INVISIBLE);
        }
        String uid = myConversations.getUid();
        getAndSetProfilePic(uid, holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardConversation;
        TextView tvConversationName, tvConversationLastMessage, tvConversationCounter;
        CircleImageView imgProfilePic;

        public MyViewHolder(@NonNull View v) {
            super(v);
            cardConversation = v.findViewById(R.id.card_temp_conversation);
            tvConversationName = v.findViewById(R.id.tv_temp_conversation_name);
            tvConversationLastMessage = v.findViewById(R.id.tv_temp_conversation_last_msg);
            tvConversationCounter = v.findViewById(R.id.tv_temp_conversation_counter);
            imgProfilePic = v.findViewById(R.id.img_temp_conversation_profilePic);
        }
    }

    private void getAndSetProfilePic(String uid, MyViewHolder holder) {
        FirebaseDatabase.getInstance()
                .getReference(Const.DB_USERS)
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String imgURL = snapshot.child("imageURL").getValue().toString();
                        try {
                            Glide.with(context).load(imgURL).centerCrop().into(holder.imgProfilePic);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                        Toast.makeText(context, "Something wrong in profile pics", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
