package com.cluster.ecommerce.payment;

import com.cluster.ecommerce.customer.CustomerResponse;
import com.cluster.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
