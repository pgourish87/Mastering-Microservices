package com.cluster.ecommerce.order;

import com.cluster.ecommerce.customer.CustomerClient;
import com.cluster.ecommerce.exception.BusinessException;
import com.cluster.ecommerce.kafka.OrderConfirmation;
import com.cluster.ecommerce.kafka.OrderProducer;
import com.cluster.ecommerce.orderline.OrderLineRequest;
import com.cluster.ecommerce.orderline.OrderLineService;
import com.cluster.ecommerce.product.ProductClient;
import com.cluster.ecommerce.product.PurchaseRequest;
import com.cluster.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        //check the customer --> OpenFeign

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create order:: No Customer Exists with id " + request.customerId()));
        //purchase the products --> product-ms (RestTemplate)

        var purchasedProducts = this.productClient.purchaseProducts(request.products());
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
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with provided ID: %d" + orderId)));
    }
}
