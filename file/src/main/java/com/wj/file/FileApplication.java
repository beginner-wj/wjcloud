package com.wj.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class FileApplication {
    private  static final Logger LOG = LoggerFactory.getLogger(FileApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FileApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        LOG.info("(♥◠‿◠)ﾉﾞ  mycloud. file 启动成功   ლ(´ڡ`ლ)ﾞ ");
        LOG.info("端口【"+ env.getProperty("server.port")+"】");
    }
}
