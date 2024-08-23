package com.leo.springboot_mall.service;

import com.leo.springboot_mall.dto.ProductQueryParams;
import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
