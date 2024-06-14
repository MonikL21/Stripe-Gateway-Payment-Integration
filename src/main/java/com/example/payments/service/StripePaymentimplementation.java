package com.example.payments.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentimplementation implements PaymentService {
    @Override
    public String makePayment(String orderId, Long amount) throws StripeException {
        Stripe.apiKey = "sk_test_51PRBJBDBeMX1bHgscqteOgRjVi7qW5kA8kaKQ1uV8ufzJIeLX0kHjrRnt9SxChdEVqIMdbkmalM2znsOs4wCJpLG00f6f6KTAf";

        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(amount)
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName(orderId).build()
                        )
                        .build();

        Price price = Price.create(params);

        PaymentLinkCreateParams paymentParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://credwise.co.in")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();


        PaymentLink paymentLink = PaymentLink.create(paymentParams);
        return paymentLink.getUrl();
    }
}
