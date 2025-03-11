package com.cluster.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {

    Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId);
}
