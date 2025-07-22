package com.digitzh.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// /resource/v2/weather/query?token=<your_token>&city=上海
@FeignClient(value = "weather-client", url = "https://api.istero.com")
public interface WeatherFeignClient {
    @GetMapping("/resource/v2/weather/query")
    String getWeather(@RequestParam("token") String token,
                    @RequestParam("city") String city);
}
