package com.company.store.service;

import com.company.store.dto.OrderData;
import com.company.store.dto.ProductDto;
import com.company.store.model.Order;
import com.company.store.model.ProductHistory;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductHistory> saveProductsHistory(OrderData orderDate, Order order);

    void returnQuantity(Order order);

    void subtractQuantity(Order order);

}
