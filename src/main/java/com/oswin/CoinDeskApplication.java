package com.oswin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoinDeskApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(CoinDeskApplication.class);


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CoinDeskApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CoinDeskApplication.class, args);
        LOGGER.info("CoinDeskApplication start");

    }

}
