package com.company.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "archive_product_id")
    )
    private List<ArchiveProduct> products;
}
