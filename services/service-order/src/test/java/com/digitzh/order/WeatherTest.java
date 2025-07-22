package com.digitzh.order;

import com.digitzh.order.feign.WeatherFeignClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTest {
    @Autowired
    WeatherFeignClient weatherFeignClient;
    @Test
    public void test01(){
        String weather = weatherFeignClient.getWeather("your_token", "上海");
        System.out.println("weather = " + weather);
    }
}
