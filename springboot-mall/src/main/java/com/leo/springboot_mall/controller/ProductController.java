package com.leo.springboot_mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;
import com.leo.springboot_mall.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 商品功能 - 取得商品資料
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 商品功能 - 新增商品
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    
    }
    
    // 商品功能 - 更新商品資料
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

            Product product = productService.getProductById(productId);

            // 檢查Product是否存在, 若不存在則回傳404
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // 若Product存在, 則更新Product
            productService.updateProduct(productId, productRequest);

            Product updateProduct = productService.getProductById(productId);

            return ResponseEntity.status(HttpStatus.OK).body(updateProduct);


    }
    
}
