package com.cluster.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String referance,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
