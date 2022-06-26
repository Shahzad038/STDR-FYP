package com.student.smartETailor.ui.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.student.smartETailor.R;
import com.student.smartETailor.models.OrderModel;


public class PaymentModeFragment extends Fragment {
    private CardView cardJazz, cardCod;
    String paymentMode = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_mode, container, false);
        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

        cardJazz = view.findViewById(R.id.card1);
        cardCod = view.findViewById(R.id.card2);
        cardJazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMode = "jazzcash";
                cardJazz.setBackgroundColor(ContextCompat.getColor(getActivity(),
                        R.color.primary_dark));

                cardCod.setBackgroundColor(ContextCompat.getColor(getActivity(),
                        R.color.white));
            }
        });

        cardCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMode = "cod";
                cardCod.setBackgroundColor(ContextCompat.getColor(getActivity(),
                        R.color.primary_dark));

                cardJazz.setBackgroundColor(ContextCompat.getColor(getActivity(),
                        R.color.white));
            }
        });

        view.findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentMode.equals("jazzcash")) {
                    OrderModel.getInstance().setPaymentMode("jazzcash");
                    OrderModel.getInstance().setPrice(OrderModel.getInstance().getDesign().getPrice());
                    loadFragment(new JazzCashFragment());
                } else if (paymentMode.equals("cod")) {
                    System.out.println("Test");
                    OrderModel.getInstance().setPaymentMode("cod");
                    OrderModel.getInstance().setPrice(OrderModel.getInstance().getDesign().getPrice());
                    loadFragment(new BookOrderFragment());
                } else {
                    Toast.makeText(getActivity(), "Select Method", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
               .addToBackStack(null)
                .commit();

    }
}