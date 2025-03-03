package com.company.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<ProductHistory> products = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private String errorMessage;
}
