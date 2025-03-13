package com.cluster.ecommerce.kafka;

import com.cluster.ecommerce.customer.CustomerResponse;
import com.cluster.ecommerce.order.PaymentMethod;
import com.cluster.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReferance,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
