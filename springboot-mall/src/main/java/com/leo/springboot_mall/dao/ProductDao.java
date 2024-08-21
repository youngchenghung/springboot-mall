package com.leo.springboot_mall.dao;

import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
