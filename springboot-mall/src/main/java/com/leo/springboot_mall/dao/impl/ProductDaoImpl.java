package com.leo.springboot_mall.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.leo.springboot_mall.rowmapper.ProductRowMapper;

import com.leo.springboot_mall.dao.ProductDao;
import com.leo.springboot_mall.dto.ProductRequest;
import com.leo.springboot_mall.model.Product;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;


@Component
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description ,created_date,last_modified_date " + 
                    "From product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    
        if (productList.size() > 0) {
            return productList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES (:product_name, :category, :image_url, :price, :stock, :description, :created_date, :last_modified_date)";
        
        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyholder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyholder);

        int productId = keyholder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :product_name, category = :category, image_url = :image_url, price = :price, stock = :stock, description = :description, last_modified_date = :last_modified_date WHERE product_id = :product_id";

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);

        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("last_modified_date", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }
}
