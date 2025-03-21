package com.company.store.service.impl;

import com.company.store.model.Customer;
import com.company.store.repository.CustomerRepository;
import com.company.store.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceDefault implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findCustomerByUser_Username(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer with username " + username + " not found"));
    }

    @Override
    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with userId " + userId + " not found"));
    }
}
