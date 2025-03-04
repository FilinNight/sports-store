package com.company.store.service.impl;

import com.company.store.dto.OrderData;
import com.company.store.dto.OrderProductData;
import com.company.store.model.Order;
import com.company.store.model.Product;
import com.company.store.model.ProductHistory;
import com.company.store.repository.ProductHistoryRepository;
import com.company.store.repository.ProductRepository;
import com.company.store.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceDefault implements ProductService {

    private final ProductRepository productRepository;

    private final ProductHistoryRepository productHistoryRepository;

    ProductServiceDefault(ProductRepository productRepository,
                          ProductHistoryRepository productHistoryRepository) {
        this.productRepository = productRepository;
        this.productHistoryRepository = productHistoryRepository;
    }

    private Product findUnsafe(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public List<ProductHistory> saveProductsHistory(OrderData orderDate, Order order) {
        return orderDate.getProducts().stream()
                .map(productData -> saveProductHistory(productData, order))
                .toList();
    }

    private ProductHistory saveProductHistory(OrderProductData productData, Order order) {
        Product product = findUnsafe(productData.getProductId());

        ProductHistory productHistory = new ProductHistory();
        productHistory.setOrder(order);
        productHistory.setProductId(product.getId());
        productHistory.setName(product.getName());
        productHistory.setArticle(product.getArticle());
        productHistory.setDescription(product.getDescription());

        BigDecimal totalPrice = productData.getPrice().multiply(BigDecimal.valueOf(productData.getQuantity()));
        productHistory.setQuantity(productData.getQuantity());
        productHistory.setPrice(productData.getPrice());
        productHistory.setTotalPrice(totalPrice);

        return productHistoryRepository.save(productHistory);
    }

    @Override
    @Transactional
    public void returnQuantity(Order order) {
        for (ProductHistory productInfo : order.getProducts()) {
            Product product = findUnsafe(productInfo.getProductId());
            product.setQuantity(product.getQuantity() + productInfo.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    @Transactional
    public void subtractQuantity(Order order) {
        for (ProductHistory productInfo : order.getProducts()) {
            Product product = findUnsafe(productInfo.getProductId());
            if (product.getQuantity() < productInfo.getQuantity()) {
                throw new IllegalArgumentException("The quantity available for sale is less " + productInfo.getQuantity());
            }
            product.setQuantity(product.getQuantity() - productInfo.getQuantity());
            productRepository.save(product);
        }
    }

}
