package com.company.store.service;

import com.company.store.model.Customer;

public interface CustomerService {

    Customer getCustomerByUsername(String username);

    Customer getCustomerByUserId(Long userId);
}
