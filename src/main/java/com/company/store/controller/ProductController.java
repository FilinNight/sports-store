package com.company.store.controller;

import com.company.store.dto.ProductDto;
import com.company.store.dto.Response;
import com.company.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product controller")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public Response<List<ProductDto>> getAllProducts() {
        try {
            return Response.ok(productService.getAllProducts());
        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
