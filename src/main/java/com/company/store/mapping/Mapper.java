package com.company.store.mapping;

import com.company.store.dto.CompanyDto;
import com.company.store.dto.OrderDto;
import com.company.store.dto.OrderProductInfoDto;
import com.company.store.dto.ProductDto;
import com.company.store.model.*;

import java.util.Optional;

public class Mapper {

    public static CompanyDto toCompanyDto(Company company) {
        if (company == null) {
            return null;
        }
        return CompanyDto.builder()
                .companyId(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .build();
    }

    public static ProductDto toProductDto(Product product) {
        if (product == null) {
            return null;
        }
        CompanyDto company = Optional.ofNullable(product.getCompany())
                .map(Mapper::toCompanyDto)
                .orElse(null);
        return ProductDto.builder()
                .productId(product.getId())
                .company(company)
                .name(product.getName())
                .article(product.getArticle())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }

    public static OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        PaymentType paymentType = Optional.ofNullable(order.getPayment())
                .map(Payment::getType)
                .orElse(null);
        return OrderDto.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .paymentType(paymentType)
                .products(order.getProducts().stream()
                        .map(Mapper::toOrderProductInfoDto)
                        .toList())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .errorMessage(order.getErrorMessage())
                .build();
    }

    public static OrderProductInfoDto toOrderProductInfoDto(ProductHistory productInfo) {
        if (productInfo == null) {
            return null;
        }
        return OrderProductInfoDto.builder()
                .orderProductInfoId(productInfo.getId())
                .productId(productInfo.getProductId())
                .name(productInfo.getName())
                .article(productInfo.getArticle())
                .description(productInfo.getDescription())
                .quantity(productInfo.getQuantity())
                .price(productInfo.getPrice())
                .totalPrice(productInfo.getTotalPrice())
                .build();
    }
}
