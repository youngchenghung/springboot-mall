package com.leo.springboot_mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.leo.springboot_mall.constant.ProductCategory;
import com.leo.springboot_mall.dto.ProductQueryParams;
import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;
import com.leo.springboot_mall.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 商品功能 - 取得所有商品資料
    // 可以透過category和search進行篩選查詢             
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
        // 查詢參數filtering
        @RequestParam(required = false) ProductCategory category,
        @RequestParam(required = false) String search,

        // 排序參數sorting, 預設為created_date 資料的大到小排序
        @RequestParam(defaultValue = "created_date") String orderBy,
        @RequestParam(defaultValue = "desc") String sort,

        // 分頁參數paging, 預設為每頁5筆資料
        @RequestParam(defaultValue = "5") @Max(100) @Min(0) Integer limit,
        @RequestParam(defaultValue = "0") @Min(0) Integer offset
        ) {

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimitl(limit);
        productQueryParams.setOffset(offset);

        List<Product> productList = productService.getProducts(productQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

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
    
    // 商品功能 - 刪除商品
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
