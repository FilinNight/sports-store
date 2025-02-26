package com.company.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;
}
