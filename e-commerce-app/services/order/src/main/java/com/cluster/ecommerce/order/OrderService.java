package com.cluster.ecommerce.order;

import com.cluster.ecommerce.customer.CustomerClient;
import com.cluster.ecommerce.exception.BusinessException;
import com.cluster.ecommerce.orderline.OrderLineRequest;
import com.cluster.ecommerce.orderline.OrderLineService;
import com.cluster.ecommerce.product.ProductClient;
import com.cluster.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest request) {
        //check the customer --> OpenFeign

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create order:: No Customer Exists with id " + request.customerId()));
        //purchase the products --> product-ms (RestTemplate)

        this.productClient.purchaseProducts(request.products());
        //persist the order

        var order = this.orderRepository.save(mapper.toOrder(request));
        //persist the order lines

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        //Todo the payment process

        //send the order confirmation--> notification-ms (Kafka)
        return null;
    }
}
