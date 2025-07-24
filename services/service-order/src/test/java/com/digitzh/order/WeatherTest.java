package com.digitzh.order;

import com.digitzh.order.feign.WeatherFeignClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class WeatherTest {
    @Autowired
    private WeatherFeignClient weatherFeignClient;

    // TODO: FeignClient为null，原因不明
    @Test
    public void test01(){
        System.out.println("WeatherFeignClient 注入成功: " + (weatherFeignClient != null));

        String weather = weatherFeignClient.getWeather("your_token", "上海");
        System.out.println("weather = " + weather);
    }
}
