package com.student.smartETailor;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Common {
    public static KProgressHUD getIndeterminateProgress(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f);
    }

}
