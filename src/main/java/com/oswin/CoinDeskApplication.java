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

    //    private static final Logger LOGGER = LogManager.getLogger(SpringSsoGoogleApplication.class);
    //private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SpringSsoGoogleApplication.class);
    private static final Logger LOGGER = LogManager.getLogger(CoinDeskApplication.class);

    static {
        System.setProperty("example","test");
        //System.setProperty("https.proxyHost","10.160.3.88");
        //System.setProperty("https.proxyPort","8080");
        //System.setProperty("https.proxyHost","127.0.0.1");
        //System.setProperty("https.proxyPort","3128");
    }





    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CoinDeskApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CoinDeskApplication.class, args);

        Logger log4j2Demo = LogManager.getLogger(CoinDeskApplication.class.getName());
        LOGGER.info("CoinDeskApplication start");
        //System.out.println(args[0] + " " + args[1]);
    }

}
