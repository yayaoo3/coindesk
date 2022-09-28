package com.oswin.config;

import com.oswin.repository.CoinRepository;
import com.oswin.service.ExchangeRatesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;

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
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.valueOf(proxyPort)));
            requestFactory.setProxy(proxy);
            return new RestTemplate(requestFactory);
        }
        return new RestTemplate(requestFactory);
    }
}

