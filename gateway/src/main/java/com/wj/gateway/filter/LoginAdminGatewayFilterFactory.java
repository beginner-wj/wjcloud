package com.wj.gateway.filter;


import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginAdminGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    LoginAdminGatewayFilter loginAdminGatewayFilter;
    @Override
    public GatewayFilter apply(Object config) {
        return loginAdminGatewayFilter;
    }
}
