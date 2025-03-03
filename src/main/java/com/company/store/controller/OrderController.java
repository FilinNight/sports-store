package com.company.store.controller;

import com.company.store.config.CustomUserDetails;
import com.company.store.dto.OrderData;
import com.company.store.dto.OrderDto;
import com.company.store.dto.Response;
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
    public Response<OrderDto> createOrder(@RequestBody OrderData request,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
//        try {
//            return Response.ok(orderService.createOrder(request, userDetails.getUserId()));
//        } catch (Exception e) {
//            return Response.error(e);
//        }
        return Response.ok(orderService.createOrder(request, userDetails.getUserId()));
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    public Response<List<OrderDto>> getAllOrders() {
        try {
            return Response.ok(orderService.getAllOrders());
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id")
    public Response<OrderDto> getOrderById(@PathVariable Long id) {
        try {
            return Response.ok(orderService.getOrderById(id));
        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
