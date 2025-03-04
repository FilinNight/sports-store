package com.company.store.service.impl;

import com.company.store.dto.OrderData;
import com.company.store.dto.PaymentRequest;
import com.company.store.dto.PaymentResponse;
import com.company.store.mapping.Mapper;
import com.company.store.model.*;
import com.company.store.repository.OrderRepository;
import com.company.store.service.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Service
public class OrderServiceDefault implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final PaymentService paymentService;

    OrderServiceDefault(OrderRepository orderRepository,
                        CustomerService customerService,
                        ProductService productService,
                        PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    private Order findUnsafe(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + id + " not found"));
    }

    @Override
    public Order getOrderById(Long id) {
        return findUnsafe(id);
    }

    @Override
    public List<Long> getOrdersIdsByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(BaseEntity::getId)
                .toList();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order createOrder(OrderData orderDate, Long userId) {
        Order order = initOrder(orderDate, userId);
        List<ProductHistory> productHistoryList = productService.saveProductsHistory(orderDate, order);
        BigDecimal totalPrice = productHistoryList.stream()
                .map(ProductHistory::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        order.getProducts().addAll(productHistoryList);
        return orderRepository.save(order);
    }

    private Order initOrder(OrderData orderDate, Long userId) {
        Order order = new Order();
        Customer customer = customerService.getCustomerByUserId(userId);
        Payment payment = paymentService.getPaymentByType(orderDate.getType());
        order.setCustomer(customer);
        order.setPayment(payment);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(Long orderId, Long userId) {
        Order order = findUnsafe(orderId);
        verificationUser(order, userId);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Order " + order.getId() + " cannot be confirm.");
        }
        order.setStatus(OrderStatus.CONFIRMED);
        productService.subtractQuantity(order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order payOrder(Long orderId, Long userId) {
        Order order = findUnsafe(orderId);
        verificationUser(order, userId);
        if (order.getStatus() != OrderStatus.PAYMENT_PENDING) {
            throw new IllegalArgumentException("Order " + order.getId() + " cannot be pay.");
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrder(Mapper.toOrderDto(order));
        paymentRequest.setAdditionalInfo("-");

        MethodPaymentService methodPaymentService = paymentService.getPaymentService(order.getPayment().getType());
        PaymentResponse response = methodPaymentService.pay(paymentRequest);

        switch (response.getStatus()) {
            case PAID -> {
                order.setStatus(OrderStatus.PAID);
            }
            case ERROR -> {
                order.setStatus(OrderStatus.PAYMENT_FAILED);
                order.setErrorMessage(response.getErrorMessage());
                productService.returnQuantity(order);
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId, Long userId) {
        Order order = findUnsafe(orderId);
        verificationUser(order, userId);
        Set<OrderStatus> statuses = Set.of(OrderStatus.CREATED, OrderStatus.CONFIRMED);
        if (statuses.stream().anyMatch(orderStatus -> orderStatus.equals(order.getStatus()))) {
            throw new IllegalArgumentException("Order " + order.getId() + " cannot be cancel");
        }
        order.setStatus(OrderStatus.CANCELLED);
        productService.returnQuantity(order);
        return orderRepository.save(order);
    }

    private void verificationUser(Order order, Long userId) {
        Customer customer = customerService.getCustomerByUserId(userId);
        if (order.getCustomer().getId() != customer.getId()) {
            throw new IllegalArgumentException("Incorrect customer for Order " + order.getId());
        }
    }
}
