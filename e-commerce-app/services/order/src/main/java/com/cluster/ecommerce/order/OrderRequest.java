package com.cluster.ecommerce.order;

import com.cluster.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment Method is required")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer Id is required")
        @NotEmpty(message = "Customer Id is required")
        @NotBlank(message = "Customer Id is required")
        String customerId,
        @NotEmpty(message = "You should at least purchase one Product")
        List<PurchaseRequest> products
) {
}
