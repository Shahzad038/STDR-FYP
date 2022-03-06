package com.student.smartETailor.ui.Customer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
/*import com.student.smartETailor.adapters.TailorDesignAdapter;*/
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.User;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {
    private final String TAG = CustomerHomeFragment.class.getSimpleName();
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        user = UsersUtils.getInstance(this.getContext()).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this.requireActivity());
            return view;
        }
        setting(view);
        fetchDesigns();
        return view;
    }

    private void fetchDesigns() {

    }

    private void setting(View view) {

    }
}