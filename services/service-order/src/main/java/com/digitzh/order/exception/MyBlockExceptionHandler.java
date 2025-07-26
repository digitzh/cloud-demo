package com.digitzh.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.digitzh.common.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       String resourceName, BlockException e) throws Exception {
        httpServletResponse.setStatus(429); // 429 Too Many Requests
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        R error = R.error(500, resourceName + " 被限流了，原因：" + e.getClass());
        String json = objectMapper.writeValueAsString(error);
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
