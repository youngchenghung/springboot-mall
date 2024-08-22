package com.leo.springboot_mall.service;

import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
