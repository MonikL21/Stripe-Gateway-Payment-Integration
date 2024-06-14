package com.example.payments.service;

import com.stripe.exception.StripeException;

public interface PaymentService {
    String makePayment(String orderId,Long amount) throws StripeException;

}
