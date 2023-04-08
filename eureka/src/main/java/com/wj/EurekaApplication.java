package com.wj;


import freemarker.core.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    private  static final Logger LOG = LoggerFactory.getLogger(EurekaApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EurekaApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        LOG.info("(♥◠‿◠)ﾉﾞ  mycloud.eureka 启动成功   ლ(´ڡ`ლ)ﾞ ");
        LOG.info("端口【"+ env.getProperty("server.port")+"】");
    }
}