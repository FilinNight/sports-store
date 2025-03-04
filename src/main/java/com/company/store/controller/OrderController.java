package com.company.store.controller;

import com.company.store.config.CustomUserDetails;
import com.company.store.dto.OrderData;
import com.company.store.dto.OrderDto;
import com.company.store.dto.Response;
import com.company.store.mapping.Mapper;
import com.company.store.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order controller")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @PostMapping
    @Operation(summary = "Create order")
    public Response<OrderDto> createOrder(
            @RequestBody OrderData orderData,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            OrderDto order = Mapper.toOrderDto(orderService.createOrder(orderData, userDetails.getUserId()));
            return Response.ok(order);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    public Response<List<OrderDto>> getAllOrders() {
        try {
            List<OrderDto> orders = orderService.getAllOrders().stream()
                    .map(Mapper::toOrderDto)
                    .toList();
            return Response.ok(orders);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id")
    public Response<OrderDto> getOrderById(@PathVariable Long id) {
        try {
            OrderDto order = Mapper.toOrderDto(orderService.getOrderById(id));
            return Response.ok(order);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/confirm")
    @Operation(summary = "Confirm order")
    public Response<OrderDto> confirmOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            OrderDto order = Mapper.toOrderDto(orderService.confirmOrder(id, userDetails.getUserId()));
            return Response.ok(order);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/pay")
    @Operation(summary = "Pay order")
    public Response<OrderDto> payOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            OrderDto order = Mapper.toOrderDto(orderService.payOrder(id, userDetails.getUserId()));
            return Response.ok(order);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel order")
    public Response<OrderDto> cancelOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            OrderDto order = Mapper.toOrderDto(orderService.cancelOrder(id, userDetails.getUserId()));
            return Response.ok(order);
        } catch (Exception e) {
            return Response.error(e);
        }
    }
}
