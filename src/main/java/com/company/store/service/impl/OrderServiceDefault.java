package com.company.store.service.impl;

import com.company.store.dto.OrderData;
import com.company.store.dto.OrderDto;
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
    public OrderDto getOrderById(Long id) {
        return Mapper.toOrderDto(findUnsafe(id));
    }

    @Override
    public List<Long> getOrdersIdsByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(BaseEntity::getId)
                .toList();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(Mapper::toOrderDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderData orderDate, Long userId) {
        Order order = initOrder(orderDate, userId);
        List<ProductHistory> productHistoryList = productService.saveProductsHistory(orderDate, order);
        BigDecimal totalPrice = productHistoryList.stream()
                .map(ProductHistory::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        order.getProducts().addAll(productHistoryList);
        return Mapper.toOrderDto(orderRepository.save(order));
    }

    private Order initOrder(OrderData orderDate, Long userId) {
        Order order = new Order();
        Customer customer = customerService.getCustomerByUserId(userId);
        Payment payment = paymentService.getPaymentByType(orderDate.getType());
        order.setCustomer(customer);
        order.setPayment(payment);
        order.setStatus(OrderStatus.CREATE);
        order.setTotalPrice(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void payOrder(Long id) {
        Order order = findUnsafe(id);
        if (order.getStatus() != OrderStatus.CREATE) {
            throw new IllegalArgumentException("Order " + order.getId() + " Status is not equal CREATE");
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrder(Mapper.toOrderDto(order));
        paymentRequest.setAdditionalInfo("-");

        MethodPaymentService methodPaymentService = paymentService.getPaymentService(order.getPayment().getType());
        PaymentResponse response = methodPaymentService.pay(paymentRequest);

        switch (response.getStatus()) {
            case PAID -> {
                order.setStatus(OrderStatus.COMPLETED);
            }
            case ERROR -> {
                order.setStatus(OrderStatus.ERROR);
                order.setErrorMessage(response.getErrorMessage());
                productService.returnQuantity(order);
            }
        }
    }

}
