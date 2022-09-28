package com.oswin.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oswin.controller.CoinDeskController;
import com.oswin.model.Coin;
import com.oswin.repository.CoinRepository;
import com.oswin.repository.Demo2Repository;
import com.oswin.service.ExchangeRatesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Set;

@Configuration
public class AppConfig {

    private static final Logger LOGGER = LogManager.getLogger(AppConfig.class);

    @Value("${spring.isUseProxy}")
    private String isUseProxy;

    @Value("${spring.proxyHost}")
    private String proxyHost;

    @Value("${spring.proxyPort}")
    private String proxyPort;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Resource
    private CoinRepository coinRepository;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        if (StringUtils.equals(isUseProxy, "true")) {
            //Local Proxy
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.valueOf(proxyPort)));
            //LAB Proxy
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
            requestFactory.setProxy(proxy);
            return new RestTemplate(requestFactory);
        }
        return new RestTemplate(requestFactory);
    }

    @PostConstruct
    public void initCoinDesk(){
        Coin c = new Coin();
        c.setId("1");
        c.setChartName("1");

        //coinRepository.save(c);
        //coinRepository.saveCoin("2","3","3");
    }
}

