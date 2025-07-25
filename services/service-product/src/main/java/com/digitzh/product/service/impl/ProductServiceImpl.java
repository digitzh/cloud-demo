package com.digitzh.product.service.impl;

import com.digitzh.product.bean.Product;
import com.digitzh.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(0L);
        product.setPrice(new BigDecimal("99"));
        product.setProductName("苹果"+productId);
        product.setNum(2);

        return product;
    }

}
