package com.digitzh.product.controller;

import com.digitzh.product.bean.Product;
import com.digitzh.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 使用Gateway的"路径重写"，可省略该注解
//@RequestMapping("/api/product")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    // 查询商品
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId){
        System.out.println("called"); // 测试由哪个实例调用
        Product product = productService.getProductById(productId);
//        // 休眠1秒，模拟慢调用
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        // 模拟异常调用
//        int i = 1 / 0;

        return product;
    }
}
