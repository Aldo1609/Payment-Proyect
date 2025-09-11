package com.aldob.payment.consumer.service;

import com.stripe.exception.StripeException;

public interface PaymentProcessService {

    void paymentProcess(String uuidPago) throws StripeException;

}
