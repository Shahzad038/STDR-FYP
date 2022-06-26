package com.student.smartETailor.ui.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.student.smartETailor.R;
import com.student.smartETailor.adapters.ConversationAdapter;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.AlerterUtils;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

public class CustomerChatFragment extends Fragment {
    User user;

    RecyclerView rv_conversations;
    ConversationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_chat, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_conversations = view.findViewById(R.id.rv_customer_conversations);
        adapter = new ConversationAdapter(this.requireActivity());
        rv_conversations.setAdapter(adapter);
    }


}