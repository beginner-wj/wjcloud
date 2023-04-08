package com.wj.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
@MapperScan("com.wj.server.mapper")
@ComponentScan("com.wj")//加入公共模块时，要加入扫描包，否则会识别不到server注解
public class BusinessApplication {
    private  static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        LOG.info("(♥◠‿◠)ﾉﾞ  mycloud. business 启动成功   ლ(´ڡ`ლ)ﾞ ");
        LOG.info("端口【"+ env.getProperty("server.port")+"】");
    }
}