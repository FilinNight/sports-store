package com.company.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
