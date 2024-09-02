package com.leo.springboot_mall.dao;

import com.leo.springboot_mall.dto.ProductQueryParams;
import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;

import java.util.List;

public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void updateStock(Integer productId, Integer stock);

    void deleteProductById(Integer producId);
}
