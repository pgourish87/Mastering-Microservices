package com.cluster.ecommerce.order;

import com.cluster.ecommerce.customer.CustomerClient;
import com.cluster.ecommerce.exception.BusinessException;
import com.cluster.ecommerce.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public Integer createOrder(OrderRequest request) {
        //check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create order:: No Customer Exists with id " + request.customerId()));

        //purchase the products --> product-ms (RestTemplate)


        //persist the order

        //persist the order lines

        //start the payment process

        //send the order confirmation--> notification-ms (Kafka)
        return null;
    }
}
