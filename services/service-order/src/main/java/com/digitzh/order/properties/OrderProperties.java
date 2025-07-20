package com.digitzh.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "order") // 配置批量绑定在Nacos下，无需@RefreshScope即可自动刷新
@Data
public class OrderProperties {
    String timeout;
    String autoConfirm;
    String dbUrl;
}
