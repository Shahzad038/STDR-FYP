package com.student.smartETailor.utils;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.student.smartETailor.R;
import com.student.smartETailor.interfaces.AddPaymentInterface;
import com.student.smartETailor.models.Measurement;

public class DialogsUtils {
    private final String TAG = DialogsUtils.class.getSimpleName();

    private static DialogsUtils _INSTANCE;

    private DialogsUtils() {
    }

    public static DialogsUtils getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new DialogsUtils();
        }
        return _INSTANCE;
    }

    public void showDialogForMeasurementPrice(Activity activity, AddPaymentInterface paymentInterface) {
        Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add_payment_measurement);
        TextView tvClose = dialog.findViewById(R.id.tv_dialog_close);
        Button btnAdd = dialog.findViewById(R.id.btn_dialog_add);
        EditText etPayment = dialog.findViewById(R.id.et_dialog);
        tvClose.setOnClickListener(view -> {
            paymentInterface.onCancelled();
            dialog.dismiss();
        });

        btnAdd.setOnClickListener(view -> {
            String payment = etPayment.getText().toString().trim();
            if (payment.isEmpty()) {
                etPayment.setError("Please enter payment for this measurement");
                return;
            }
            paymentInterface.paymentConfirmed(payment);
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
