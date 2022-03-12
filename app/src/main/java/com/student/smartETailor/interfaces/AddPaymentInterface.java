package com.student.smartETailor.interfaces;

public interface AddPaymentInterface {
    void paymentConfirmed(String payment);
    void onCancelled();
}
